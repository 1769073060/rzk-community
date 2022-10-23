package com.rzk.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.rzk.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @PackageName : com.rzk.utils
 * @FileName : TokenUtil
 * @Description : token
 * @Author : rzk
 * @CreateTime : 2022年 10月 22日 下午12:01
 * @Version : 1.0.0
 */
public class TokenUtil {
    public static String getToken() {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60 * 60 * 1000;//一小时有效时间
        Date end = new Date(currentTime);
        String token = "";

        token = JWT.create().withAudience("omCxR43njPYpNeaXqL7ptMso_qn4".toString()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256("24".toString()));
        return token;
    }

    public static void main(String[] args) {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJvbUN4UjQzbmpQWXBOZWFYcUw3cHRNc29fcW40IiwiZXhwIjoxNjY2NTMzODA0LCJpYXQiOjE2NjY1MzAyMDR9.z11EbnOWQNkf-uy9uWl1l1bXzYUhq8kbYn6EBUrWotU";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Claims claims = Jwts.parser().setSigningKey("itcast").parseClaimsJws(token).getBody();

        System.out.println("签发时间:"+sdf.format(claims.getIssuedAt()));
        System.out.println("过期时间:"+sdf.format(claims.getExpiration()));
        System.out.println("当前时间:"+sdf.format(new Date()) );

        System.out.println("生成{}"+token);
        String userId = JWT.decode(token).getAudience().get(0);
        System.out.println("解析token{}"+userId);
        String userId1 = JWT.decode(token).getAudience().get(1);
        System.out.println("解析token{}"+userId1);
    }


    //生成token
    public static String getToken(User user) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60 * 60 * 1000;//一小时有效时间
        Date end = new Date(currentTime);
        String token = "";

        token = JWT.create().withAudience(user.getUserOpenid().toString()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getUserId().toString()));
        return token;
    }


    public static String getTokenUserId() {
        String token = getRequest().getHeader("authorization");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        return userId;
    }

    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }

}
