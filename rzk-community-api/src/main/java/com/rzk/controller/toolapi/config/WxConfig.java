package com.rzk.controller.toolapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: 小熊
 * @date: 2021/6/15 11:05
 * @description:phone 17521111022
 */
@Component
public class WxConfig {
    public static String appId;
    public static String secret;
    public static String syappId;
    public static String sysecret;
    @Value("${wx.appid}")
    private void setAppId(String appId) {
        WxConfig.appId = appId;
    }

    @Value("${wx.secret}")
    private void setSecret(String secret) {
        WxConfig.secret = secret;
    }

    @Value("${wx.sysecret}")
    public static void setSyappId(String syappId) {
        WxConfig.syappId = syappId;
    }

    @Value("${wx.sysecret}")
    public static void setSysecret(String sysecret) {
        WxConfig.sysecret = sysecret;
    }
}
