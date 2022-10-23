package com.rzk.utils;

import com.rzk.pojo.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @PackageName : com.rzk.config.security
 * @FileName : JwtTokenUtil 工具类
 * @Description :
 * @Author : rzk
 * @CreateTime : 3/5/2021 下午4:00
 * @Version : 1.0.0
 */
@Component
public class JwtTokenUtil {
    //jwt 荷载用户名的key
    private static final String CLAIM_KEY_USERNAME="sub";
    //jwt 的创建时间
    private static final String CLAIM_KEY_CREATED="created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    public static void main(String[] args) {
        Claims claims = getClaimsFromTokens("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvbUN4UjQzbmpQWXBOZWFYcUw3cHRNc29fcW40IiwiY3JlYXRlZCI6MTY2NjUzMjA0NTk5MSwiZXhwIjoxNjY3MTM2ODQ2fQ.vsJql6cG1rzG4UMiCcLHnILxMqbWeTrt2gnS8alO-taTjDifyLL0IvivyOmTUhhAqWVNRXNzbU5QoDiE1Qq_yQ");
        System.out.println(claims.getExpiration());
        System.out.println(claims);
        System.out.println(claims.get("sub"));
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        //Claims claims = Jwts.parser().setSigningKey("itcast").parseClaimsJws(token).getBody();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println("当前时间:"+format.format(new Date()));
        System.out.println("过期时间:"+format.format(claims.getExpiration()));
        System.out.println("判断是否失效");
        boolean tokenExpireds = isTokenExpireds("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvbUN4UjQzbmpQWXBOZWFYcUw3cHRNc29fcW40IiwiY3JlYXRlZCI6MTY2NjUzMjA0NTk5MSwiZXhwIjoxNjY3MTM2ODQ2fQ.vsJql6cG1rzG4UMiCcLHnILxMqbWeTrt2gnS8alO-taTjDifyLL0IvivyOmTUhhAqWVNRXNzbU5QoDiE1Qq_yQ");
        System.out.println(tokenExpireds);
    }
    public static Claims getClaimsFromTokens(String token) {
        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .setSigningKey("rzk-secret")
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }
    public static String generateToken(String token){
        Map<String,Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateTokens(claims);
    }
    public static String generateTokens(Map<String,Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDates())
                .signWith(SignatureAlgorithm.HS512,"rzk-secret")
                .compact();
    }
    public static Date generateExpirationDates() {
        //当前时间+配置的失效时间
        return new Date(System.currentTimeMillis()+604800*1000);
    }

    /**
     * 判断token是否失效
     * @param token
     * @return
     */
    public static boolean isTokenExpireds(String token) {
        //获取失效时间
        Date expireDate = getExpiredDateFromTokens(token);
        return expireDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     * @param token
     * @return
     */
    public static Date getExpiredDateFromTokens(String token) {
        Claims claims = getClaimsFromTokens(token);
        return claims.getExpiration();
    }








    /**
     * 根据用户信息生成token
     * @param user
     * @return
     */
    public String generateToken(User user){
        Map<String,Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,user.getUserOpenid());
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

    /**
     * 从token获取登录用户名
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token){
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
            e.printStackTrace();
        }
        return username;
    }

    /**
     * 验证token是否有效
     * @param token
     * @param user
     * @return
     */
    public boolean validateToken(String token,User user){
       //获取用户名
        String username = getUserNameFromToken(token);
        return username.equals(user.getUserOpenid()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否可以被刷新
     * @param token
     * @return
     */
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String refreshToken(String token){
        //获取对应的荷载
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);

    }

    /**
     * 判断token是否失效
     * @param token
     * @return
     */
    public boolean isTokenExpired(String token) {
        //获取失效时间
        Date expireDate = getExpiredDateFromToken(token);
        return expireDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     * @param token
     * @return
     */
    public Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 从token中获取荷载
     * @param token
     * @return
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }


    /**
     * 根据荷载生成JWT Token
     * @param claims
     * @return
     */
    public String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    /**
     * 生成token失效时间
     * @return
     */
    public Date generateExpirationDate() {
        //当前时间+配置的失效时间
        return new Date(System.currentTimeMillis()+expiration*1000);
    }
}
