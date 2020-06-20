package com.elf.gateway.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * @author Administrator
 * @Desc
 * @date 2020/6/20 21:23
 */
public class JwtTokenUtil {

    private static final String AUTHORITIES_KEY = "auth";

    public String verifyToken(String token, String request){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey("key")
                .build()
                .parseClaimsJws(token)
                .getBody();

        // fix bug: 当前用户如果没有任何权限时，在输入用户名后，刷新验证码会抛IllegalArgumentException
        Object authoritiesStr = claims.get(AUTHORITIES_KEY);

        return "1";
    }

}
