package com.rzk;


import com.google.common.collect.Lists;
import com.rzk.handlerInterceptor.MyInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


/**
 * 拦截器配置
 */
@Configuration
public class FilterConfig implements WebMvcConfigurer {

    @Bean
    public MyInterceptor getMyInterceptor() {
        return new MyInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截器如果有注入其他则使用这种,都可以
//        registry.addInterceptor(getMyInterceptor())
//                .addPathPatterns("/wx/**")
//                .excludePathPatterns("/wx/getMessage/getAllNoticeMessage");
                        //这里配置不拦截上方的url
                        //"/download/**");
        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/swagger-ui/**")
                .excludePathPatterns("/Login")
                .excludePathPatterns("/api/wx/auth")
                .excludePathPatterns("/ws")
                .excludePathPatterns("/pc/video/upLoadBizhi")
                .excludePathPatterns("/userVideo/showAllVideos")
                .excludePathPatterns("/getMessage/getAllMessageDetail/**")
                .excludePathPatterns("/getMessage/getAllMessageDetailClassification/**")
                .excludePathPatterns("/getMessage/getLostMessage")
                .excludePathPatterns("/getMessage/getAllShop")
                .excludePathPatterns("/getMessage/getAllSwiperMessage")
                .excludePathPatterns("/getMessage/getAllCategoryMessage")
                .excludePathPatterns("/getMessage/getCategoryMenuI")
                .excludePathPatterns("/getMessage/getCategoryMenuII/**")
                .excludePathPatterns("/categorymenu/**")
                .excludePathPatterns("/wx/**")
                .excludePathPatterns("/wxsy/**")
                .excludePathPatterns("/**/api/wx/login")
                .excludePathPatterns("/**/api/video/**")
                .excludePathPatterns("/**/api/video/getParsingInfo")
                .excludePathPatterns("/api/video/getVideoInfos")
                .excludePathPatterns("/**/api/video/getVideoInfos")
                .excludePathPatterns("/getMessage/getAllNoticeMessage");


      // registry.addInterceptor(getMyInterceptor()).addPathPatterns("/api/**").excludePathPatterns("/api/login");

    }


    /**
   @Bean
   public FilterRegistrationBean jwtFilter() {
       final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
       registrationBean.setFilter(new JwtFilter());
       //添加需要拦截的url
       List<String> urlPatterns = Lists.newArrayList();
       urlPatterns.add("/api/");

       registrationBean.addUrlPatterns(urlPatterns.toArray(new String[urlPatterns.size()]));
       return registrationBean;
   }
**/

}
