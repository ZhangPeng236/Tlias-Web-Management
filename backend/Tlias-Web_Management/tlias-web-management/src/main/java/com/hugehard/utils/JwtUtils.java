package com.hugehard.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

    private static final String SECRET_KEY = "aHVnZWhhcmQ="; // 秘钥
    private static final long JWT_EXPIRE = 12 * 24 * 60 * 60 * 1000; // 12小时

    /**
     * 生成JWT令牌
     *
     * @param claims 令牌中包含的信息
     * @return 生成的JWT令牌字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 指定加密算法，秘钥
                .addClaims(claims) // 添加自定义信息
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRE)) // 设置令牌过期时间
                .compact(); //  compact()方法生成JWT令牌字符串并返回
    }

    /**
     * 解析JWT令牌
     *
     * @param token 要解析的JWT令牌字符串
     * @return 包含令牌信息的Claims对象
     * @throws Exception 如果令牌无效或者过期，则抛出异常
     */
    public static Claims parseToken(String token) throws Exception {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // 指定秘钥
                .parseClaimsJws(token)// 解析JWT令牌字符串
                .getBody(); // 获取令牌信息
    }
}
