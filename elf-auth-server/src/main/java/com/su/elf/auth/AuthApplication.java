package com.su.elf.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author surongyao
 * @date 2019-01-08 11:17
 * @desc
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.su.elf"})  // 包含鉴权日志模块
@MapperScan("com.su.elf.**.mapper")
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
