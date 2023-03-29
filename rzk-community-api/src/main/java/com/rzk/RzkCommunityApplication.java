package com.rzk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * @PackageName : com.rzk
 * @FileName : RzkCommunityApplication
 * @Description : 启动类
 * @Author : rzk
 * @CreateTime : 2022年 09月 10日 下午8:56
 * @Version : 1.0.0
 */
@EnableRabbit
@EnableScheduling
@SpringBootApplication
@MapperScan("com.rzk.mapper")
@SpringBootConfiguration
public class RzkCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(RzkCommunityApplication.class,args);
    }
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
