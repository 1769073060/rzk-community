package com.rzk;


import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Data
@Configuration
@EnableSwagger2
@ConditionalOnClass(EnableSwagger2.class)
@ConfigurationProperties(prefix = "swagger")  //获取yml配置
public class Swagger2 {


    /**
     * swagger2的配置文件这里可以配置swagger2的基本内容，如扫描包等
     */
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rzk.controller"))
                .paths(PathSelectors.any())
                .build();

    }

    /**
     *
     * @Description
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                //设置页面标题
                .title("短视频文档")
                //设置联系人
                .contact(new Contact("rzk", "www.rzkai.com", "1769073060@qq.com"))
                //描述
                .description("欢迎查看短视频接口文档")
                //版本号
                .version("0.1").build();
            }

}
