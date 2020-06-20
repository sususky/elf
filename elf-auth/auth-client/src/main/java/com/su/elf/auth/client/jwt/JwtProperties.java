package com.su.elf.auth.client.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author surongyao
 * @date 2020-05-28 17:32
 * @desc
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /** Request Headers ： Authorization */
    private String header;

    /** 必须使用最少88位的Base64对该令牌进行编码 */
    private String base64Secret;

    /** 令牌过期时间 此处单位/毫秒 */
    private Integer tokenValidityInSeconds;

    /** 在线用户 key，根据 key 查询 redis 中在线用户的数据 */
    private String onlineKey;

    /** 验证码 key */
    private String codeKey;
    private Integer captchaOpen;

    /** token 续期检查 */
    private Long detect;

    /** 续期时间 */
    private Long renew;

}
