package com.rzk.controller.toolapi.controller;

import com.rzk.pojo.WxAppUser;
import com.rzk.controller.toolapi.model.support.BaseResponse;
import com.rzk.service.WxAppUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author: 小熊
 * @date: 2021/6/11 16:58
 * @description:phone 17521111022
 */
@RestController
@RequestMapping("/api/wx")
public class WxController {

    @Resource
    private WxAppUserService wxAppUserService;

    @PostMapping(value = "auth")
    public BaseResponse<String> wxAuth(@RequestParam(value = "js_code") String code) {
        String openId = wxAppUserService.getOpenId(code);
        return BaseResponse.ok("openId获取成功", openId);
    }

    /**
     * 登录、注册、获取用户信息
     * @param openId
     * @return
     */
    @PostMapping(value = "login")
    public BaseResponse<WxAppUser> login(@RequestParam(value = "openId") String openId, String name) {
        WxAppUser wxUser = wxAppUserService.getUserInfoByOpenId(openId);
        if (wxUser == null) {
            wxUser = new WxAppUser();
            wxUser.setName(name);
            wxUser.setOpenId(openId);
            wxUser.setVideoNumber(999);
            wxUser.setSignInSum(1);
            wxUser.setCreateTime(new Date());
            wxAppUserService.insert(wxUser);
        }
        return BaseResponse.ok(wxUser);
    }

    /***
     * 签到
     * @param openId
     * @return
     */
    @PostMapping(value = "signIn")
    public BaseResponse<WxAppUser> sign(@RequestParam(value = "openId") String openId) {
        WxAppUser wxUser = wxAppUserService.singIn(openId);
        return BaseResponse.ok(wxUser);
    }

}
