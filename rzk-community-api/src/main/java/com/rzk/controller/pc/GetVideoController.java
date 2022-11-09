package com.rzk.controller.pc;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.rzk.pojo.Comment;
import com.rzk.pojo.ImageResource;
import com.rzk.pojo.User;
import com.rzk.pojo.Videos;
import com.rzk.pojo.vo.PublisherVideos;
import com.rzk.pojo.vo.UsersVo;
import com.rzk.service.ImageResourceService;
import com.rzk.service.UserService;
import com.rzk.service.VideosService;
import com.rzk.utils.*;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@Slf4j
@RestController
@Api(value = "pc操作video有关业务的接口", tags = {"pc操作video业务controller"})
@RequestMapping("pc/video")
public class GetVideoController {
    private Logger logger = LoggerFactory.getLogger(GetVideoController.class);

    @Autowired
    private VideosService videosService;
    @Autowired
    private UserService usersService;
    @Autowired
    private ImageResourceService imageResourceService;

    @Value("${rzk.url}")
    private String rzkUrl;
    @Value(value = "${minio.endpoint}")
    private String endpoint;

    @Value(value = "${minio.accessKey}")
    private String accessKey;

    @Value(value = "${minio.secretKey}")
    private String secretKey;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Autowired
    private MinioUtils minioUtils;
    //每页分页记录数
    public static final Integer PAGE_SIZE = 5;

    @PostMapping("/upload")
    public Object upload(MultipartFile file) {

        List<String> upload = minioUtils.upload(new MultipartFile[]{file});

        return endpoint + "/" + bucketName + "/" + upload.get(0);
    }

    @PostMapping("/uploads")
    public Object uploads(String address) throws Exception {
        //获取视频的第一帧图片输出流
        InputStream first = MinioUtils.randomGrabberFFmpegImage(address);
        //获取文件名
        String fileName = address.substring(address.lastIndexOf("/"), address.lastIndexOf(".")).concat(".jpg");
        //将流转化为multipartFile
        MultipartFile multipartFile = new MockMultipartFile("file", fileName, "image/jpg", first);

        String upload = minioUtils.upload(multipartFile);

        return endpoint + "/" + bucketName + "/" + upload;
    }

    /**
     * 文件创建
     * 如果文件是视频类型的，那么将文件截图，并将截图和视频上传到MinIO存储
     * 如果文件是图片类型的，那么将图片上传到MinIo存储
     */
    @ApiOperation(value = "Minio文件上传", notes = "Minio上传视频的接口")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "type", value = "文件类型", required = true, dataType = "String", paramType = "form"))
    @PostMapping(value = "/minioFileUpload", headers = "content-type=multipart/form-data")
    public HashMap minioFileUpload(MultipartFile file, String type) throws Exception {
        HashMap map = new HashMap();

        String date = DateUtil.formatDate(new Date());
        String filename = DateUtil.currentSeconds() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        if ("0".equals(type)) {
            String imgName = "img" + "/" + date + "/" + filename;
            minIoClientUpload(file.getInputStream(), imgName);
            String videoUrl = endpoint + "/" + bucketName + "/" + imgName;
            //获取视频的第一帧图片输出流
            InputStream first = MinioUtils.randomGrabberFFmpegImage(videoUrl);
            //获取文件名
            String fileName = videoUrl.substring(videoUrl.lastIndexOf("/"), videoUrl.lastIndexOf(".")).concat(".jpg");
            //将流转化为multipartFile
            MultipartFile multipartFile = new MockMultipartFile("file", fileName, "image/jpg", first);

            String pictureName = minioUtils.upload(multipartFile);
            String pictureUrl = endpoint + "/" + bucketName + "/" + pictureName;

            map.put("videoUrl", videoUrl);
            map.put("pictureUrl", pictureUrl);
            return map;
        }

        String videoType = file.getContentType();
        // 文件存储的目录结构

        String videoName = videoType + "/" + date + "/" + filename;

        String imgPath = this.ffmpegGetScreenshot(file);
        String imgPathName = DateUtil.currentSeconds() + imgPath.substring(imgPath.lastIndexOf("."));
        BufferedInputStream imgInputStream = FileUtil.getInputStream(imgPath);

        String imgName = "video" + "/" + date + "/" + imgPathName;

        minIoClientUpload(imgInputStream, imgName);
        minIoClientUpload(file.getInputStream(), videoName);
        logger.info("图片文件上传成功!");
        // 存储文件
        logger.info("视频文件上传成功!");
        String videoPath = endpoint + "/" + bucketName + "/" + videoName;
        String imgPathOne = endpoint + "/" + bucketName + "/" + imgName;

        map.put("videoPath", videoPath);
        map.put("imgPath", imgPathOne);
        //   logger.info("上传发生错误: {}！", e.getMessage());

        return map;
    }

    public String ffmpegGetScreenshot(MultipartFile file) throws IOException {
        File tofile = new File(file.getOriginalFilename());
        FileUtils.copyInputStreamToFile(file.getInputStream(), tofile);
        String absolutePath = tofile.getAbsoluteFile().getAbsolutePath();
        Map<String, Object> screenshot = ScreenShotUtils.getScreenshot(absolutePath);
        String imgPath = (String) screenshot.get("imgPath");
        return imgPath;
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

        //图片保存路径
        //String fileUploadPath ="/"+userId+"/image";
        String uploadFile=null;
        if (image != null && !image.isEmpty()) {

            String imageName = image.getOriginalFilename();
            if (StringUtils.isNotBlank(imageName)) {

                String date = DateUtil.formatDate(new Date());
                String filename = DateUtil.currentSeconds() + image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
                String imgName = userId + "/" + "img" + "/" + date + "/" + filename;
                logger.info("imgName"+imgName);
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
                com.rzk.utils.FileUtil fileUtil = new com.rzk.utils.FileUtil();

            }
        } else {
            return JSONResult.errorMsg("上传功能出错");
        }

//        User users = new Users();
//        users.setId(userId);
//        users.setFaceImage(uploadFile);
//        usersService.updateUsersInfo(users);
        ImageResource imageResource = new ImageResource();
        imageResource.setImageUrl(uploadFile);
        imageResource.setType(1);
        imageResource.setStatus(1);
        imageResource.setCreateTime(new Date());
        imageResource.setUpdateTime(new Date());
        return JSONResult.ok(uploadFile);
    }



    @ApiOperation(value = "用户上传视频", notes = "用户上传视频的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户的id", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoSeconds", value = "视频时长", required = true, dataType = "double", paramType = "form"),
            @ApiImplicitParam(name = "videoWidth", value = "视频宽度", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "videoHeight", value = "视频高度", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "desc", value = "视频描述", required = false, dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/upLoadVideos", headers = "content-type=multipart/form-data")
    public JSONResult upLoadVideos(String userId, double videoSeconds, int videoWidth, int videoHeight, String desc, @ApiParam(value = "视频", required = true) MultipartFile video) throws Exception {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorTokenMsg("用户id不能为空");
        }

        //截取视频名
        String originalFilename = video.getOriginalFilename();
        int indexOf = originalFilename.lastIndexOf(".mp4");
        String originalFileName = originalFilename.substring(0, indexOf);


        String videosid = null;
        String uploadFile = null;
        String videoUrl = null;
        String date = null;
        try {
            if (video != null) {

                String videoName = UUID.randomUUID().toString() + ".mp4";
                if (StringUtils.isNotBlank(videoName)) {
                    date = DateUtil.formatDate(new Date());
                    String filename = DateUtil.currentSeconds() + video.getOriginalFilename().substring(video.getOriginalFilename().lastIndexOf("."));
                    String imgName =  "video" + "/" + date + "/" + originalFileName + "/" + filename;
                    minIoClientUpload(video.getInputStream(), imgName);
                    videoUrl = "/" + bucketName + "/" + imgName;
                    logger.info("视频地址{}" + videoUrl);

                }
            } else {
                //后续可做处理如果视频上传成功后, 后续操作失败就把上传的视频删除
                return JSONResult.errorMsg("上传功能出错");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取视频的第一帧图片输出流
        InputStream first = MinioUtils.randomGrabberFFmpegImage(endpoint + videoUrl);
        //获取文件名
        String fileJpgName = videoUrl.substring(videoUrl.lastIndexOf("/"), videoUrl.lastIndexOf(".")).concat(".jpg");
        //根据用户名  日期 文件名 生成截图地址
        String fileName = userId + "/" + "video" + "/" + date + "/" + originalFileName + fileJpgName;
        //将流转化为multipartFile
        MultipartFile multipartFile = new MockMultipartFile("file", fileName, "image/jpg", first);
        //上传截图
        String pictureName = minioUtils.upload(multipartFile);
        String pictureUrl = "/" + bucketName + "/" + pictureName;
        //视频截图工具

        //保存视频信息到mysql
        Videos videos = new Videos();
        videos.setUserId(userId);
        videos.setVideoSeconds((float) videoSeconds);
        videos.setVideoHeight(videoHeight);
        videos.setVideoWidth(videoWidth);
        videos.setVideoDesc(desc);
        videos.setVideoPath(videoUrl);
        videos.setCoverPath(pictureUrl);
        videos.setStatus(UserVideoStatusEnum.BAN.value);
        videos.setCreateTime(new Date());
        videosid = videosService.insertVideos(videos);


        return JSONResult.ok(videosid);
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


//    @ApiOperation(value = "用户上传封面", notes = "用户上传封面的接口")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "videoId", value = "视频的id", required = true, dataType = "String", paramType = "form"),
//            @ApiImplicitParam(name = "userId", value = "用户的id", required = true, dataType = "String", paramType = "form"),
//    })
//    @PostMapping(value = "/upLoadCover", headers = "content-type=multipart/form-data")
//    public JSONResult upLoadCover(String videoId, String userId, @ApiParam(value = "视频封面", required = true) MultipartFile cover) throws Exception {
//        if (StringUtils.isBlank(videoId) || StringUtils.isBlank(userId)) {
//            return JSONResult.errorMsg("视频的id和用户id不能为空");
//        }
//        //视频上传路径
//        //String fileDownloadPath = "E:\\lnsf_mod_dev";
//        //视频保存路径
//        String fileCoverPath = "/" + userId + "/video";
//        FileOutputStream fileOutputStream = null;
//        InputStream inputStream = null;
//        String coverFinalLocal = "";
//        String videosid = null;
//        try {
//            if (cover != null) {
//
//                String videoName = cover.getOriginalFilename();
//                if (StringUtils.isNotBlank(videoName)) {
//                    //视频上传最终路径
//                    coverFinalLocal = FILE_DOWNLOAD_PATH + fileCoverPath + "/" + videoName;
//                    //视频片最终	保存路径
//                    fileCoverPath += ("/" + videoName);
//                    File outImage = new File(coverFinalLocal);
//                    if (outImage.getParentFile() != null || !outImage.getParentFile().isDirectory()) {
//                        //创建父文件
//                        outImage.getParentFile().mkdirs();
//                    }
//                    fileOutputStream = new FileOutputStream(outImage);
//                    inputStream = cover.getInputStream();
//                    IOUtils.copy(inputStream, fileOutputStream);
//                }
//            } else {
//                return JSONResult.errorMsg("上传功能出错");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fileOutputStream != null) {
//                fileOutputStream.flush();
//                fileOutputStream.close();
//            }
//        }
//
//
//        videosService.updateVideos(videoId, coverFinalLocal);
//        return JSONResult.ok();
//    }

    @PostMapping(value = "/showAllVideos")
    public JSONResult showAllVideos(@RequestBody Videos video, Integer isSaveRecord, Integer pages, Integer pageSize) {
        if (pages == null) {
            pages = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedResult pagedResult = videosService.getAllVideosAndUsers(video, isSaveRecord, pages, pageSize);
        return JSONResult.ok(pagedResult);

    }

    @PostMapping(value = "/showLikeVideos")
    public JSONResult showLikeVideos(String userId, Integer pages, Integer pageSize) {
        if (pages == null) {
            pages = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedResult pagedResult = videosService.selectMyLikeVideos(userId, pages, pageSize);
        return JSONResult.ok(pagedResult);

    }

    @PostMapping(value = "/showFollowVideos")
    public JSONResult showFollowVideos(String userId, Integer pages, Integer pageSize) {
        if (pages == null) {
            pages = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedResult pagedResult = videosService.selectFollowVideos(userId, pages, pageSize);
        return JSONResult.ok(pagedResult);

    }


    @PostMapping(value = "/hotselect")
    public JSONResult hotselect() {

        return JSONResult.ok(videosService.getHotselect());

    }

    @PostMapping(value = "/userLike")
    public JSONResult userLike(String userId, String videoId, String videoUserId) {
        videosService.userLikeVideos(userId, videoId, videoUserId);
        return JSONResult.ok();

    }

    @PostMapping(value = "/userDisLike")
    public JSONResult userDisLike(String userId, String videoId, String videoUserId) {
        videosService.userDislikeVideos(userId, videoId, videoUserId);
        return JSONResult.ok();

    }

    @PostMapping("/saveComment")
    public JSONResult saveComment(@RequestBody Comment comment,
                                  String fatherCommentId, String toUserId) throws Exception {

//        comment.setFatherCommentId(fatherCommentId);
//        comment.setToUserId(toUserId);
//        videosService.saveComment(comment);
        return JSONResult.ok();
    }

    @PostMapping("/getVideoComments")
    public JSONResult getVideoComments(String videoId, Integer page, Integer pageSize) throws Exception {

        if (StringUtils.isBlank(videoId)) {
            return JSONResult.ok();
        }

        // 分页查询视频列表，时间顺序倒序排序
        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        PagedResult list = videosService.getAllComments(videoId, page, pageSize);

        return JSONResult.ok(list);
    }


    @PostMapping("/hiddenVideos")
    public JSONResult hiddenVideos(String userId, Boolean isChecked) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("程序运行失败");
        }
//        usersService.updateUserHidden(userId, isChecked);


        return JSONResult.ok();
    }

    @PostMapping("/queryPublisher")
    public JSONResult queryPublisher(String loginUserId, String videoId, String publishUserId) {
        if (StringUtils.isBlank(publishUserId)) {
            return JSONResult.errorMsg("");
        }
        User users = usersService.queryUsersInfo(publishUserId);
        //查询视频发布者信息
        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(users, usersVo);
        //查询当前登录者的点赞关系
        boolean userLikeVideo = usersService.isUserLikeVideo(loginUserId, videoId);
        PublisherVideos bean = new PublisherVideos();
        bean.setPublisher(usersVo);
        bean.setUserLikeVideo(userLikeVideo);
        return JSONResult.ok(bean);
    }
    
    
    //链接url下载图片
    public static void download(String urlString, String filename,String savePath) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为5s
        con.setConnectTimeout(5*1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf=new File(savePath);
        if(!sf.exists()){
            sf.mkdirs();
        }
        log.info(sf.getPath()+filename);
        OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }


    @ApiImplicitParam(name = "userId", value = "用户的id", required = true, dataType = "String", paramType = "query")
    @PostMapping("/upLoadBizhi")
    public void upLoadBizhi(@RequestParam("file") MultipartFile image) throws Exception {
        log.info("image.getOriginalFilename()"+image.getOriginalFilename());
        try {
            // 将D:/test.txt文件读取到输入流中
            //InputStreamReader input = new InputStreamReader(new FileInputStream(image.getOriginalFilename()),"UTF-8");
            //InputStreamReader inputTwo = new InputStreamReader(new FileInputStream(image.getOriginalFilename()),"UTF-8");
            // 创建BufferedReader，以gb2312的编码方式读取文件
            InputStreamReader inputStreamReader1 = new InputStreamReader(image.getInputStream());
            InputStreamReader inputStreamReader2 = new InputStreamReader(image.getInputStream());
            BufferedReader reader = new BufferedReader(inputStreamReader1);
            BufferedReader readerTwo = new BufferedReader(inputStreamReader2);
            List<fileUrlTest> fileUrl = new ArrayList<fileUrlTest>();//图片路径List
            List<fileUrlTwoTest> fileUrlTwo = new ArrayList<fileUrlTwoTest>();//图片名称List
            String line = null;
            String lineTwo = null;
            // 按行读取文本，直到末尾（一般都这么写）
            while ((line = reader.readLine()) != null) {
                log.info("按行读取文本---------------->"+line);
                // 打印当前行字符串
                fileUrlTest ff = new fileUrlTest();
                ff.setPathUrl(line);
                fileUrl.add(ff);
            }
            while ((lineTwo = readerTwo.readLine()) != null) {
                // 打印当前行字符串
                lineTwo = lineTwo.substring(lineTwo.lastIndexOf("/")+1,lineTwo.length());
                fileUrlTwoTest fg = new fileUrlTwoTest();
                fg.setFileName(lineTwo);
                fileUrlTwo.add(fg);
            }


            for (int i = 0; i < fileUrl.size(); i++) {
                logger.info("fileUrl{}"+fileUrl.get(i).getPathUrl());
                MultipartFile fileItem = new com.rzk.utils.FileUtil().createFileItem(fileUrl.get(i).getPathUrl(), fileUrlTwo.get(i).getFileName());
                logger.info("fileItem{}"+fileItem);
                String filename = System.currentTimeMillis() + fileItem.getOriginalFilename().substring(fileItem.getOriginalFilename().lastIndexOf("."));

                //截取文件夹，每个文件下都有多个图片
                int index = fileUrl.get(i).getPathUrl().lastIndexOf("/")-7;
                String subFileUrl = fileUrl.get(i).getPathUrl().substring(index);
                log.info(subFileUrl);

                String subFileName = subFileUrl.substring(0,subFileUrl.indexOf("/"));


                String imgName = "img" + "/" + subFileName + "/" + filename;
                minIoClientUpload(fileItem.getInputStream(), imgName);
                String upload = "/" + bucketName + "/" + imgName;
                logger.info("上传地址为====>"+upload);
                ImageResource imageResource = new ImageResource();
                imageResource.setImageUrl(upload);
                imageResource.setType(1);
                imageResource.setStatus(1);
                imageResource.setGroupName(subFileName);
                imageResource.setCreateTime(new Date());
                imageResource.setUpdateTime(new Date());
                boolean save = imageResourceService.save(imageResource);
                if (save==true){
                    logger.info("保存图片地址------->"+save);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @ApiImplicitParam(name = "userId", value = "用户的id", required = true, dataType = "String", paramType = "query")
    @PostMapping("/upLoadShiping")
    public void upLoadShiping(@RequestParam("file") MultipartFile image) throws Exception {
        log.info("image.getOriginalFilename()"+image.getOriginalFilename());
        try {
            // 将D:/test.txt文件读取到输入流中
            //InputStreamReader input = new InputStreamReader(new FileInputStream(image.getOriginalFilename()),"UTF-8");
            //InputStreamReader inputTwo = new InputStreamReader(new FileInputStream(image.getOriginalFilename()),"UTF-8");
            // 创建BufferedReader，以gb2312的编码方式读取文件
            InputStreamReader inputStreamReader1 = new InputStreamReader(image.getInputStream());
            InputStreamReader inputStreamReader2 = new InputStreamReader(image.getInputStream());
            BufferedReader reader = new BufferedReader(inputStreamReader1);
            BufferedReader readerTwo = new BufferedReader(inputStreamReader2);
            List<fileUrlTest> fileUrl = new ArrayList<fileUrlTest>();//图片路径List
            List<fileUrlTwoTest> fileUrlTwo = new ArrayList<fileUrlTwoTest>();//图片名称List
            String line = null;
            String lineTwo = null;
            // 按行读取文本，直到末尾（一般都这么写）
            while ((line = reader.readLine()) != null) {
                log.info("按行读取文本---------------->"+line);
                // 打印当前行字符串
                fileUrlTest ff = new fileUrlTest();
                ff.setPathUrl(line);
                fileUrl.add(ff);
            }
            while ((lineTwo = readerTwo.readLine()) != null) {
                // 打印当前行字符串
                lineTwo = lineTwo.substring(lineTwo.lastIndexOf("/")+1,lineTwo.length());
                fileUrlTwoTest fg = new fileUrlTwoTest();
                fg.setFileName(lineTwo);
                fileUrlTwo.add(fg);
            }
            String videoUrl = null;
            String date = null;
            for (int i = 0; i < fileUrl.size(); i++) {
                logger.info("fileUrl{}"+fileUrl.get(i).getPathUrl());
                MultipartFile fileItem = new com.rzk.utils.FileUtil().createFileItem(fileUrl.get(i).getPathUrl(), fileUrlTwo.get(i).getFileName());
                logger.info("fileItem{}"+fileItem);
                String filename = System.currentTimeMillis() + fileItem.getOriginalFilename().substring(fileItem.getOriginalFilename().lastIndexOf("."));

                //截取文件夹，每个文件下都有多个图片
                int index = fileUrl.get(i).getPathUrl().lastIndexOf("/");
                String subFileUrl = fileUrl.get(i).getPathUrl().substring(index);
                log.info(subFileUrl);

                String subFileName = subFileUrl.substring(0,subFileUrl.indexOf("/"));

                date = DateUtil.formatDate(new Date());
                String imgName = "video" + "/" + date + "/" + filename;
                minIoClientUpload(fileItem.getInputStream(), imgName);
                String upload = "/" + bucketName + "/" + imgName;
                logger.info("上传地址为====>"+upload);


                //获取视频的第一帧图片输出流
                InputStream first = MinioUtils.randomGrabberFFmpegImage(upload);
                //获取文件名
                String fileJpgName = upload.substring(upload.lastIndexOf("/"), upload.lastIndexOf(".")).concat(".jpg");
                //根据用户名  日期 文件名 生成截图地址
                String fileName =  "video" + "/" + System.currentTimeMillis() + "/" + subFileName + fileJpgName;
                //将流转化为multipartFile
                MultipartFile multipartFile = new MockMultipartFile("file", fileName, "image/jpg", first);
                //上传截图
                String pictureName = minioUtils.upload(multipartFile);
                String pictureUrl = "/" + bucketName + "/" + pictureName;
                //视频截图工具

//                //保存视频信息到mysql
//                Videos videos = new Videos();
//                videos.setUserId("1");
//                videos.setVideoSeconds((float) videoSeconds);
//                videos.setVideoHeight(videoHeight);
//                videos.setVideoWidth(videoWidth);
//                videos.setVideoDesc(desc);
//                videos.setVideoPath(videoUrl);
//                videos.setCoverPath(pictureUrl);
//                videos.setStatus(UserVideoStatusEnum.BAN.value);
//                videos.setCreateTime(new Date());
//                videosid = videosService.insertVideos(videos);
//                if (save==true){
//                    logger.info("保存图片地址------->"+save);
//                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String url = "http://elf-deco.img.maibaapp.com/content/ugc/wallpaper/5119863/11bec812c78f028e28b840dea53b184d.jpg";
        int i = url.lastIndexOf("/")-7;
        String substring = url.substring(i);
        log.info(substring);

        String substring1 = substring.substring(0,substring.indexOf("/"));
        log.info(substring1);
    }

}

class fileUrlTest{
    String pathUrl;

    public String getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }
}

class fileUrlTwoTest{
    String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}