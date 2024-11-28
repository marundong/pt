package com.mrd.pt.auth.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AuthPtUser extends PtUser implements UserDetails {

    private List<GrantedAuthority> authorities;

    private final PtUser ptUser;

    public AuthPtUser(PtUser ptUser) {
        this.ptUser = ptUser;
    }

    public PtUser getUser() {
        return ptUser;
    }

    @Override
    public String getPassword() {
        return ptUser.getPassword();
    }

    @Override
    public String getUsername() {
        return ptUser.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}