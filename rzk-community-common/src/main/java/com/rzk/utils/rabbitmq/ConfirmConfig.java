package com.rzk.utils.rabbitmq;

/**
 * @PackageName : com.rzk.config
 * @FileName : ConfirmConfig
 * @Description : RabbitMQ配置类
 * @Author : rzk
 * @CreateTime : 2023年 03月 25日 上午11:38
 * @Version : 1.0.0
 */

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类 发布确认
 */
@Configuration
public class ConfirmConfig {
    //普通交换机
    public static final String CONFIRM_EXCHANGE_NAME = "confirm_exchange";
    //普通队列
    public static final String CONFIRM_QUEUE_NAME = "confirm_queue";
    //RoutingKey
    public static final String CONFIRM_EXCHANGE_ROUTING_KEY = "key1";

    //声明普通交换机
    @Bean("confirmExchange")
    public DirectExchange confirmExchange() {
        return new DirectExchange(CONFIRM_EXCHANGE_NAME);
    }

    //声明普通队列
    @Bean("confirmQueue")
    public Queue confirmQueue() {
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }

    //绑定普通交换机和普通队列
    @Bean
    public Binding queueBindingExchange(@Qualifier("confirmQueue") Queue confirmQueue,
                                        @Qualifier("confirmExchange") DirectExchange confirmExchange) {
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_EXCHANGE_ROUTING_KEY);
    }
}