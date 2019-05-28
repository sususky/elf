package com.su.elf.auth.client.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author surongyao
 * @date 2019-01-02 16:13
 * @desc
 */
public class User implements UserDetails {

    private String username;
    private String password;
    private List<Authorities> authorities;  //权限
    private int roleId;
    private String roleName;
    private int isSuper;
    private int readOnly;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setAuthorities(List<Authorities> authorities) {
        this.authorities = authorities;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(int isSuper) {
        this.isSuper = isSuper;
    }

    public int getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(int readOnly) {
        this.readOnly = readOnly;
    }

    @Override
    public boolean isAccountNonExpired() {
        //账户是否未过期
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //账户是否未锁定
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //凭证(密码)是否未过期
        return true;
    }

    @Override
    public boolean isEnabled() {
        //用户是否启用
        return true;
    }

}
