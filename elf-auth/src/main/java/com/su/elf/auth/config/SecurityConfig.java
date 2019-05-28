package com.su.elf.auth.config;


import com.su.elf.common.service.RestService;
import com.su.elf.common.service.impl.RestServiceImpl;
import com.su.elf.auth.handler.AuthLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.client.RestTemplate;

/**
 * @author sury
 * Date 2019-01-10
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestService restService(){
        RestServiceImpl restService = new RestServiceImpl();
        restService.setRestTemplate(restTemplate);
        return restService;
    }

    @Bean
    public TokenStore tokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }


    /**
     * springboot2.0 删除了原来的 plainTextPasswordEncoder
     * https://docs.spring.io/spring-security/site/docs/5.0.4.RELEASE/reference/htmlsingle/#10.3.2 DelegatingPasswordEncoder
     *
     */


    // password 方案一：明文存储，用于测试，不能用于生产
//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }

    // password 方案二：用 BCrypt 对密码编码
//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    // password 方案三：支持多种编码，通过密码的前缀区分编码方式,推荐
    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //
//    /**
//     * 这一步的配置是必不可少的，否则SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
//     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/oauth/*", "/").permitAll()
                .anyRequest().authenticated()
                .and().logout().addLogoutHandler(ssoLogoutHandler())
                .and().csrf().disable();
    }

    @Bean
    public AuthLogoutHandler ssoLogoutHandler() {
        return new AuthLogoutHandler();
    }

}
