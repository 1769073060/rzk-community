package com.rzk.handlerInterceptor;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzk.pojo.Audience;

import com.rzk.pojo.User;
import com.rzk.service.UserService;
import com.rzk.utils.JWTUtil;
import com.rzk.utils.JwtTokenUtil;
import com.rzk.utils.status.MsgConsts;
import com.rzk.utils.status.ResponseResult;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class MyInterceptor implements HandlerInterceptor {


    @Resource
    private Audience audience;
    @Resource
    private UserService userService;
    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }

        log.info("拦截器{}ip=====>"+ipAddress);
        String token = request.getHeader("authorization");// 从 http 请求头中取出 token
        log.info("token{}"+token);
        // 执行认证
        if (token == null) {
            throw new RuntimeException("无token,请重新登录");
        }
        // 获取 token 中的 openId
        String openId;
        try {
            Claims claimsFromToken = jwtTokenUtil.getClaimsFromToken(token);
            openId = claimsFromToken.get("sub").toString();
            log.info(openId);
        } catch (JWTDecodeException j) {
            throw new RuntimeException("401");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_openid", openId);
        User user = userService.getOne(queryWrapper);
        if (user == null) {
            throw new RuntimeException("用户不存在，请重新登录");

        }
        // 验证 token
        /**
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUserId().toString())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("401");
        }

        boolean tokenExpired = jwtTokenUtil.isTokenExpired(token);
        if (!tokenExpired){
            throw new RuntimeException("401");
        }**/
        return true;
    }
        /**
         //等到请求头信息authorization信息
         final String authHeader = request.getHeader("authorization");

         if ("OPTIONS".equals(request.getMethod())) {
         //测试服务器支持方法
         response.setStatus(HttpServletResponse.SC_OK);
         return false;
         } else {
         final String token = authHeader;//获取 token
         try {
         if (audience == null) {//获取配置信息
         BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
         audience = (Audience) factory.getBean("audience");
         }
         final Claims claims = JWTUtil.parseJWT(token, audience.getBase64Secret());//解密token,获取token内容
         if (claims == null) {//解析失败,token有问题
         this.returnJson(response, JsonUtils.objectToJson(ResponseData.out(CodeEnum.SIGNATURE_NOT_ALLOW, "令牌出现错误")));
         return false;
         }
         WXSessionModel user = JsonUtils.jsonToPojo(claims.get("user").toString(), WXSessionModel.class);//解析储存的user信息

         request.getSession().setAttribute("user", user);

         } catch (final Exception e) {
         this.returnJson(response, JsonUtils.objectToJson(ResponseData.out(CodeEnum.SIGNATURE_NOT_ALLOW, "出现致命错误")));
         return false;
         }
         }
         return true;
         **/


    /**
     * 返回客户端数据
     */
    private void returnJson(HttpServletResponse response, String result) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(result);

        } catch (IOException e) {
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }


}
