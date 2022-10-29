package com.rzk.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.rzk.pojo.vo.UsersVo;
import com.rzk.utils.JSONResult;
import com.rzk.utils.JsonUtils;
import com.rzk.utils.JwtTokenUtil;
import com.rzk.utils.common.HttpClientUtil;
import com.rzk.utils.status.MsgConsts;
import com.rzk.utils.status.ResponseData;
import com.rzk.utils.status.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.rzk.pojo.*;
import com.rzk.service.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rzk.utils.status.CodeEnum.*;

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
                UpdateWrapper updateWrapper = new UpdateWrapper();
                updateWrapper.eq("user_openid",user.getUserOpenid());
                userService.update(user,updateWrapper);

                //老用户
                map.put("userId",userMessageByOtherMessage.get(0).getUserId());
                map.put("token",token);
                System.out.println("Token{}"+token);
                return  new ResponseResult(MsgConsts.SUCCESS_CODE, "老用户",map);

            } else {
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
        BeanUtils.copyProperties(user, usersVo);
        if (StringUtils.isNotBlank(fanId))
            usersVo.setFollow(userService.queryIfFollow(userId, fanId));
        return JSONResult.ok(usersVo);
    }

}
