package com.rzk.config;

import com.rzk.controller.wxserver.WxServerController;
import com.rzk.pojo.wxserver.BaseMessage;
import com.rzk.service.IWxService;
import com.rzk.utils.BeanToXml;
import com.rzk.utils.ByteUtils;
import com.rzk.utils.rabbitmq.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @PackageName : com.rzk.service.impl
 * @FileName : Consumer
 * @Description : 接收队列
 * @Author : rzk
 * @CreateTime : 2023年 03月 25日 下午2:16
 * @Version : 1.0.0
 */
@Slf4j
@Component
public class Consumer {
    @Autowired
    private IWxService iWxService;

    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE_NAME)
    public void receiveConfirMessage(Message message){
        String msg = new String(message.getBody());
        log.info("接收队列confirm.queue消息:{}",msg);

        Map<String,String> map = new HashMap<>();
        WxServerController wxServerController = new WxServerController();
        wxServerController.validate(msg);

        //iWxService.getResponses(map);
        //BeanToXml.objToXml(baseMessage);

    }


}
