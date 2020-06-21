package com.su.elf.auth.client.jwt;


import com.su.elf.auth.client.config.JwtProperties;
import com.su.elf.auth.client.entity.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.UUID;

/**
 * @author Administrator
 * @Desc
 * @date 2020/6/20 13:46
 */
@Slf4j
@Component
public class JwtTokenUtil {

    private static final String AUTHORITIES_KEY = "auth";
    private Key key;

    @Autowired
    private JwtProperties jwtProperties;

    public String verifyToken(String token, String request){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // fix bug: 当前用户如果没有任何权限时，在输入用户名后，刷新验证码会抛IllegalArgumentException
        Object authoritiesStr = claims.get(AUTHORITIES_KEY);

        return "1";
    }

    public String createToken(AuthUser user) {
        String authorities = user.getRoles().toString();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                // 加入ID确保生成的 Token 都不一致
                .setId(UUID.randomUUID().toString())
                .compact();
    }


}
