package com.igomall.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JWTUtils {

    public static String create(String id, Map<String,Object> map){
        long now = System.currentTimeMillis();//当前时间
        long exp = now + 1000*60*60*24*365;//过期时间为1分钟
        JwtBuilder builder= Jwts.builder().setId(id)
                .setSubject("小白")
                .setIssuedAt(new Date())//用于设置签发时间
                .signWith(SignatureAlgorithm.HS256,"wangmh")
                .setExpiration(new Date(exp));//用于设置过期时间
        for (String key: map.keySet()) {
            builder.claim(key,map.get(key));
        }
        return builder.compact();
    }

    public static Claims parseToken(String token){
        Claims claims =
                Jwts.parser().setSigningKey("wangmh").parseClaimsJws(token).getBody();
        return claims;
    }

    public static void main(String[] args) {
        Claims claims = parseToken("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoi5bCP55m9IiwiaWF0IjoxNTg5NjQwNzgwLCJleHAiOjE1OTExMTIwMDgsImlkIjoxLCJ1c2VybmFtZSI6ImFkbWluMSJ9.rRchUdLxEzFBW9pGEoAQiV3TuklQsLF8_z-ZQHaR080");

        System.out.println(claims);
    }
}
