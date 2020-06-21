package com.su.elf.auth.service;

import com.su.elf.auth.entity.AuthUser;
import com.su.elf.auth.mapper.UserMapper;
import com.su.elf.common.CodeEnum;
import com.su.elf.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author surongyao
 * @date 2019-01-11 10:23
 * @desc
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public AuthUser loadUserByUsername(String name) {
        AuthUser user = userMapper.getByName(name);
        if (user != null) {
            user.setRoles(userMapper.getRoles(user.getId()));
        } else {
            throw new ApiException(CodeEnum.NO_USER);
        }

//        String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode("123456");
//        sso.setPassword(finalSecret);
        return user;
    }

}
