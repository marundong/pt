package com.mrd.pt.auth.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AuthPtUser extends PtUser implements UserDetails {

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
        return null;
    }
}