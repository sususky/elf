package com.su.elf.auth.server.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author surongyao
 * @date 2020-06-01 17:29
 * @desc
 */
@Getter
@Setter
public class OnlineUser {

    /**
     * token
     */
    private String token;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * IP
     */
    private String ip;

    /**
     * 地址
     */
    private String os;

    /**
     * 登录时间
     */
    private Date loginTime;
}
