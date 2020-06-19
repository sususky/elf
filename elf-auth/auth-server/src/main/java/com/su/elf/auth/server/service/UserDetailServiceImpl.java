package com.su.elf.auth.server.service;

import com.su.elf.auth.client.entity.User;
import com.su.elf.auth.server.mapper.AuthoritieMapper;
import com.su.elf.auth.server.mapper.UserMapper;
import com.su.elf.common.CodeEnum;
import com.su.elf.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * @author surongyao
 * @date 2019-01-11 10:23
 * @desc
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthoritieMapper authoritieMapper;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userMapper.getByName(name);
        if (user != null) {
            user.setAuthorities(authoritieMapper.getPrivilegeByRoleId(user.getRoleId()));
        } else {
            throw new ApiException(CodeEnum.NO_USER);
        }

//        String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode("123456");
//        sso.setPassword(finalSecret);
        return user;
    }

}
