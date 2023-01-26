package com.rzk.service;

import com.rzk.pojo.wxserver.BaseMessage;

import java.util.Map;

/**
 * @PackageName : com.rzk.service
 * @FileName : ReplyMessageService
 * @Description : 处理回复消息
 * @Author : rzk
 * @CreateTime : 24/1/2022 上午1:50
 * @Version : v1.0
 */
public interface IReplyMessageService {


    public BaseMessage replyTextMessage(Map<String, String> requestMap);

    public BaseMessage replyTextBlackFLagMessage(Map<String, String> requestMap);

    public String getVideoInfo();

    public BaseMessage replyEventMessage(Map<String, String> requestMap);
}
