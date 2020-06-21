package com.su.elf.auth.client.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author surongyao
 * @date 2019-01-02 16:13
 * @desc
 */
@Getter
@Setter
public class AuthUser {

    private int id;
    private String username;
    private String password;
    private String avatar;
    private Integer gender;
    private List<Integer> roles;

}
