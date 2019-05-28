package com.su.elf.auth.client.entity;

import org.springframework.security.core.GrantedAuthority;

public class Authorities implements GrantedAuthority {

    private static final long serialVersionUID = -6058060376656180793L;
    private String authority;
    private String authorityName;

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
