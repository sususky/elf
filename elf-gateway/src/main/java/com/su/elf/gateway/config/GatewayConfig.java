package com.su.elf.gateway.config;

import com.su.elf.gateway.filter.JwtTokenFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 * @Desc
 * @date 2020/6/20 17:27
 */
@Configuration
public class GatewayConfig {

    @Bean
    public GlobalFilter jwtFilter() {
        return new JwtTokenFilter();
    }

}
