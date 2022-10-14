package com.rzk.config.login;

import org.springframework.stereotype.Component;

import com.rzk.pojo.*;
import com.rzk.service.*;

import java.util.List;

@Component
public class IsLogin {


    private Integer code;

    private Integer userId;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 用户登录判断
     *
     * @param User
     * @param openid
     * @param UserService
     * @return
     */

    public IsLogin isTrue(User User, String openid, UserService UserService) {
        User User1 = new User();
        User1.setUserOpenid(openid);

        IsLogin isLogin = new IsLogin();
        isLogin.setCode(500);
        List<User> userMessageByOtherMessage = UserService.getUserMessageByOtherMessage(User1);

        try {
            if (userMessageByOtherMessage.size() == 1) {
                UserService.updateUserMessage(User);
                isLogin.setCode(200);//老用户
                isLogin.setUserId(userMessageByOtherMessage.get(0).getUserId());
            } else {
                UserService.insertUserMessage(User);
                isLogin.setUserId(User.getUserId());
                isLogin.setCode(300);//新用户
            }
        } catch (Exception e) {
            e.printStackTrace();
            isLogin.setCode(500);//出现错误
        }
        return isLogin;
    }


}
