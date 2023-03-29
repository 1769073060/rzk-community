package com.rzk.service;

import com.rzk.pojo.WxAppUser;

/**
 * @author: 小熊
 * @date: 2021/6/15 13:38
 * @description:phone 17521111022
 */
public interface WxAppUserService {
    WxAppUser getUserInfoByOpenId(String openId);

    int insert(WxAppUser wxUser);

    int updateById(WxAppUser wxUser);

    /**
     * 签到
     *
     * @param openId
     * @return 累计签到次数
     */
    WxAppUser singIn(String openId);

    /***
     * 获取微信用户openId
     * @param code
     * @return
     */
    String getOpenId(String code);
}
