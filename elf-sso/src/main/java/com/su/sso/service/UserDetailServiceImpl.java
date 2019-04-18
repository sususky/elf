package com.su.sso.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.su.common.CodeEnum;
import com.su.common.exception.CommonException;
import com.su.common.service.RestService;
import com.su.sso.entity.Authorities;
import com.su.sso.entity.SsoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author surongyao
 * @date 2019-01-11 10:23
 * @desc
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private RestService restService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        SsoUser sso = new SsoUser();
        sso.setUsername(name);
        JSONObject jsonUser = restService.get("http://system/user?name=" + name);
        if(jsonUser!=null){
            JSONArray userList = jsonUser.getJSONArray("list");
            if(userList!=null && userList.size()>0){
                JSONObject user = userList.getJSONObject(0);
                sso.setPassword(user.getString("password"));
                Integer roleId = user.getInteger("roleId");
                sso.setRoleId(user.getInteger("roleId"));
                sso.setIsSuper(user.getInteger("isSuper"));
                sso.setReadOnly(user.getInteger("readonly"));
                sso.setRoleName(user.getString("roleName"));

                JSONObject jsonPrivilege = restService.get("http://system/privilege/role/" + roleId);
                JSONArray privilegeList = jsonPrivilege.getJSONArray("list");
                if(privilegeList!=null && privilegeList.size()>0){
                    List<Authorities> authorities = parseAuthorities(privilegeList);
                    sso.setAuthorities(authorities);
                }
            }
        }else{
            throw new CommonException(CodeEnum.NO_USER);
        }

//        String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode("123456");
//        sso.setPassword(finalSecret);
        return sso;
    }

    private List<Authorities> parseAuthorities(JSONArray privilegeList) {
        List<Authorities> list = new ArrayList<>();
        Authorities authority;
        for(int i=0;i<privilegeList.size();i++){
            authority = new Authorities();
            authority.setAuthority(privilegeList.getJSONObject(i).getString("link"));
            authority.setAuthorityName(privilegeList.getJSONObject(i).getString("privilegeName"));
            list.add(authority);
        }
        return list;
    }

}
