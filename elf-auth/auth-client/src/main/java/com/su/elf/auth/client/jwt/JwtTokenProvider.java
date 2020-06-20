package com.su.elf.auth.client.jwt;

import com.su.elf.auth.client.entity.AuthUser;
import com.su.elf.common.redis.RedisDao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * @Desc
 * @date 2020/6/20 13:46
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String AUTH_TOKEN_HEADER_NAME = "token";

    private final JwtProperties properties;
    private final RedisDao redisDao;
    private Key key;


    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getBase64Secret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String verifyToken(String token, String url){
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

    /**
     * @param token 需要检查的token
     */
    public void checkRenewal(String token){
        // 判断是否续期token,计算token的过期时间
        long time = redisDao.getExpire(properties.getOnlineKey() + token);
        // 如果在续期检查的范围内，则续期
        if(time <= properties.getDetect()){
            redisDao.expire(properties.getOnlineKey() + token, properties.getRenew(), TimeUnit.MILLISECONDS);
        }
    }

    public String getToken(HttpServletRequest request){
        String token = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if(StringUtils.isBlank(token)){
            token = request.getParameter(AUTH_TOKEN_HEADER_NAME);
        }

        return token;
    }

}
