package com.rzk.service;

import java.util.Map;

/**
 * @PackageName : com.rzk.service
 * @FileName : WxService
 * @Description : 用于处理所有的事件和消息的回复
 * @Author : rzk
 * @CreateTime : 19/1/2022 上午2:00
 * @Version : v1.0
 */
public interface IWxService {


    /**
     * 用于处理所有的事件和消息的回复
     * @param requestMap
     * @return
     */
    public String getResponse(Map<String, String> requestMap);


}