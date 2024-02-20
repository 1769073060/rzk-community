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
public interface IReplyMessageWCRService {



    public BaseMessage replyTextMessageWeChatRobot(Map<String, String> requestMap);

}
