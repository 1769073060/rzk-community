package com.rzk;

import org.apache.catalina.connector.Connector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
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
//@EnableRabbit
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

    //小程序涉及到需要添加https操作,发布需要解放这一代码,公众号不需要用到,需要注释

    /**
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createHTTPConnector());
        return tomcat;
    }
    private Connector createHTTPConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        //同时启用http（8080）、https（8443）两个端口
        connector.setScheme("http");
        connector.setSecure(false);
        connector.setPort(8080);
        connector.setRedirectPort(8099);
        return connector;
    }
    **/


}
