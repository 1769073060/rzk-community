package com.rzk.controller.app;

import com.rzk.config.login.IsLogin;
import com.rzk.utils.JsonUtils;
import com.rzk.utils.common.HttpClientUtil;
import com.rzk.utils.status.MsgConsts;
import com.rzk.utils.status.ResponseData;
import com.rzk.utils.status.ResponseResult;
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

@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @Resource
    private WXMessage wxMessage;
    @Resource
    private Audience audience;

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
        //      url: 'https://api.weixin.qq.com/sns/jscode2session?appid='+appid+'&secret='+secret+'&js_code='+code+'&grant_type=authorization_code',
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+wxMessage.getWxId()+"&secret="+wxMessage.getWxSecret()+"&js_code="+code+"&grant_type=authorization_code";

        String wxResult = HttpClientUtil.doGet(url);
        System.out.println("登陆后显示："+wxResult);
        WXSessionModel wxSessionModel = JsonUtils.jsonToPojo(wxResult, WXSessionModel.class);

        String openid = wxSessionModel.getOpenid();
        user.setUserOpenid(openid);


        List<User> userMessageByOtherMessage = userService.getUserMessageByOtherMessage(user);
        System.out.println("登录userMessageByOtherMessage"+userMessageByOtherMessage.size());
        try {
            if (userMessageByOtherMessage.size() == 1) {
                userService.updateUserMessage(user);
                //老用户
                return  new ResponseResult(MsgConsts.SUCCESS_CODE, "老用户",userMessageByOtherMessage.get(0).getUserId());

            } else {
                userService.save(user);
                return ResponseResult.success(MsgConsts.NEW_USER,"新用户",user.getUserId());//新用户

            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseData.error(INTERNAL_SERVER_ERROR);//出现错误
        }
        return ResponseResult.success(MsgConsts.SUCCESS_CODE,"成功",userMessageByOtherMessage.get(0).getUserId());//新用户


    }

    @PostMapping("/checkAdmin")
    public List<User> checkAdmin(Integer id) {
        User user = new User();
        user.setUserId(id);
        return userService.getUserMessageByOtherMessage(user);
    }
}
