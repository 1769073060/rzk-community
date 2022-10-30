package com.rzk.controller.app;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.rzk.pojo.vo.UsersVo;
import com.rzk.utils.*;
import com.rzk.utils.common.HttpClientUtil;
import com.rzk.utils.status.MsgConsts;
import com.rzk.utils.status.ResponseData;
import com.rzk.utils.status.ResponseResult;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.util.validation.metadata.NamedObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.rzk.pojo.*;
import com.rzk.service.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rzk.utils.status.CodeEnum.*;
import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @Resource
    private WXMessage wxMessage;
    @Resource
    private Audience audience;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Value(value = "${minio.endpoint}")
    private String endpoint;

    @Value(value = "${minio.accessKey}")
    private String accessKey;

    @Value(value = "${minio.secretKey}")
    private String secretKey;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Value("${url.http}")
    private String utlHttp;

    @Autowired
    private MinioUtils minioUtils;
    /**
     * 登录功能
     *
     * @param code
     * @param user
     * @return
     */
    @ResponseBody
    @Transactional
    @PostMapping("/Login")
    public ResponseResult Login(String code, @RequestBody User user) {
        log.info("获取登录用户信息user{}"+user);
        //      url: 'https://api.weixin.qq.com/sns/jscode2session?appid='+appid+'&secret='+secret+'&js_code='+code+'&grant_type=authorization_code',
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+wxMessage.getWxId()+"&secret="+wxMessage.getWxSecret()+"&js_code="+code+"&grant_type=authorization_code";
        Map<String,Object> map = new HashMap<>();
        String token = null;
        String wxResult = HttpClientUtil.doGet(url);
        System.out.println("登陆后显示："+wxResult);
        WXSessionModel wxSessionModel = JsonUtils.jsonToPojo(wxResult, WXSessionModel.class);

        String openid = wxSessionModel.getOpenid();
        user.setUserOpenid(openid);



        List<User> userMessageByOtherMessage = userService.getUserMessageByOtherMessage(user);
        System.out.println("登录userMessageByOtherMessage"+userMessageByOtherMessage.size());
        try {
            if (userMessageByOtherMessage.size() == 1) {
                User userToken = new User();
                userToken.setUserId(userMessageByOtherMessage.get(0).getUserId());
                userToken.setUserOpenid(userMessageByOtherMessage.get(0).getUserOpenid());
                token = jwtTokenUtil.generateToken(userToken);
                /*UpdateWrapper updateWrapper = new UpdateWrapper();
                updateWrapper.eq("user_openid",user.getUserOpenid());
                userService.update(user,updateWrapper);*/

                //老用户
                map.put("userId",userMessageByOtherMessage.get(0).getUserId());
                map.put("token",token);
                System.out.println("Token{}"+token);
                return  new ResponseResult(MsgConsts.SUCCESS_CODE, "老用户",map);

            } else {
                user.setUserAvatar(AvatarHelper.BASE64_PREFIX +AvatarHelper.createBase64Avatar(Math.abs("springboot.io".hashCode())));
                userService.save(user);

                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("user_openid",openid);
                User userServiceOne = userService.getOne(queryWrapper);
                token = jwtTokenUtil.generateToken(userServiceOne);
                map.put("userId",userServiceOne.getUserId());
                map.put("token",token);
                System.out.println("Token{}"+token);
                return ResponseResult.success(MsgConsts.NEW_USER,"新用户",map);//新用户

            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseData.error(INTERNAL_SERVER_ERROR);//出现错误
        }
        map.put("userId",userMessageByOtherMessage.get(0).getUserId());
        map.put("token",token);
        return ResponseResult.success(MsgConsts.SUCCESS_CODE,"成功",map);//新用户


    }

    @ApiImplicitParam(name = "userId", value = "用户的id", required = true, dataType = "String", paramType = "query")
    @PostMapping("/upLoadImage")
    public JSONResult upLoadImage(String userId, @RequestParam("file") MultipartFile image) throws Exception {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空");
        }
        if (image.isEmpty()) {
            return JSONResult.errorMsg("不能上传空文件哦");
        }
        //图片上传路径
        //String fileDownloadPath = "C:\\lnsf_mod_dev";
        //String fileDownloadPath = "/opt/lnsf_mod_dev";
        //图片保存路径
        //String fileUploadPath ="/"+userId+"/image";
        String uploadFile=null;
        if (image != null && !image.isEmpty()) {

            String imageName = image.getOriginalFilename();
            if (StringUtils.isNotBlank(imageName)) {

                String date = DateUtil.formatDate(new Date());
                String filename = DateUtil.currentSeconds() + image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
                String imgName = userId + "/" + "img" + "/" + date + "/" + filename;
                minIoClientUpload(image.getInputStream(), imgName);
                uploadFile =  "/" + bucketName + "/" + imgName;
                String videoUrl = endpoint + "/" + bucketName + "/" + imgName;
                //获取视频的第一帧图片输出流
                InputStream first = MinioUtils.randomGrabberFFmpegImage(videoUrl);
                //获取文件名
                String fileName = videoUrl.substring(videoUrl.lastIndexOf("/"), videoUrl.lastIndexOf(".")).concat(".jpg");
                //将流转化为multipartFile
                MultipartFile multipartFile = new MockMultipartFile("file", fileName, "image/jpg", first);

                String pictureName = minioUtils.upload(multipartFile);
                String pictureUrl = endpoint + "/" + bucketName + "/" + pictureName;


                //图片上传最终路径
                //图片最终	保存路径

            }
        } else {
            return JSONResult.errorMsg("上传功能出错");
        }
        User user = new User();
        user.setUserAvatar(utlHttp+uploadFile);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("user_id", userId);
        userService.update(user,updateWrapper);
//        User users = new Users();
//        users.setId(userId);
//        users.setFaceImage(uploadFile);
//        usersService.updateUsersInfo(users);
        return JSONResult.ok(uploadFile);
    }


    /**
     * 上传
     * @param imgInputStream
     * @param objectName
     * @throws Exception
     */
    public void minIoClientUpload(InputStream imgInputStream, String objectName) throws Exception {
        //创建头部信息
        Map<String, String> headers = new HashMap<>(10);
        //添加自定义内容类型
        headers.put("Content-Type", "application/octet-stream");
        //添加存储类
        headers.put("X-Amz-Storage-Class", "REDUCED_REDUNDANCY");
        //添加自定义/用户元数据
        Map<String, String> userMetadata = new HashMap<>(10);
        userMetadata.put("My-Project", "Project One");


        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint(endpoint)
                        .credentials(accessKey, secretKey)
                        .build();

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(imgInputStream, imgInputStream.available(), -1)
                        .userMetadata(userMetadata)
                        .build());
        imgInputStream.close();

    }

    @PostMapping("/checkAdmin")
    public List<User> checkAdmin(Integer id) {
        User user = new User();
        user.setUserId(id);
        return userService.getUserMessageByOtherMessage(user);
    }

    @PostMapping("/queryUsers")
    public JSONResult queryUsers(String userId, String fanId) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空");
        }
        User user = userService.queryUsersInfo(userId);
        UsersVo usersVo = new UsersVo();
        log.info("获取我的信息{}"+usersVo.toString());
        BeanUtils.copyProperties(user, usersVo);
        if (StringUtils.isNotBlank(fanId))
            usersVo.setFollow(userService.queryIfFollow(userId, fanId));
        return JSONResult.ok(usersVo);
    }

}
