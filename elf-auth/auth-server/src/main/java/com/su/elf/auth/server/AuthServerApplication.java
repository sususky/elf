package com.su.elf.auth.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author surongyao
 * @date 2019-01-08 11:17
 * @desc
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = { "com.su.elf" })   // 包含日志模块
@MapperScan("com.su.elf.**.mapper")
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

}
