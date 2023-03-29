package com.rzk.config;

/**
 * @PackageName : com.rzk.service.impl
 * @FileName : MyCallBack
 * @Description : RabbitMQ回调接口配置
 * @Author : rzk
 * @CreateTime : 2023年 03月 25日 上午11:46
 * @Version : 1.0.0
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 *回调接口
 */
@Slf4j
@Component
public class MyCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        //内部接口注入类中
        rabbitTemplate.setConfirmCallback(this);
        //rabbitTemplate.setReturnsCallback(this);
    }

    /**
     *交换机确定回调方法
     * 1.发消息 交换机接收到消息 回调
     *  1.1 correlationData 保存回调消息的ID及相关信息
     *  1.2 交换机收到消息 ack=true
     *  1.3 cause null
     * 2.发消息 交换机接受失败 回调
     *  2.1 correlationData 保存回调消息的ID及相关信息
     *  2.2 交换机收到消息 ack=false
     *  2.3 cause 失败原因
     */
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if(ack){
            log.info("交换机已经收到id为：{}的消息",id);
            log.info("交换机已经收到correlationData为：{}的消息",correlationData);
        }
        else {
            log.info("交换机未经收到id为：{}的消息，原因为：{}",id,cause);
        }

    }

    //在消息传递过程中不可达目的地时将消息返回给生产者
    //只有不可达目的地时才进行回退
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("消息被交换机:{}退回，路由key：{}，退回原因：{}",
                exchange, routingKey,replyText);
    }
}