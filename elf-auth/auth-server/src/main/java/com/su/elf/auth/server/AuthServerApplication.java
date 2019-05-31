package com.su.elf.auth.server;

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
@SpringBootApplication
@MapperScan("com.su.*.mapper")
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

}
