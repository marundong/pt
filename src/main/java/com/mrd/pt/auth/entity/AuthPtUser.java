package com.mrd.pt.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthPtUser extends PtUser implements UserDetails, OAuth2AuthenticatedPrincipal {

    private List<GrantedAuthority> authorities;
    private final Map<String, Object> attributes = new HashMap<>();
    private final PtUser ptUser;

    public AuthPtUser(PtUser ptUser) {
        this.ptUser = ptUser;
    }

    public PtUser getUser() {
        return ptUser;
    }

    @Override
    public Long getId() {
        return ptUser.getId();
    }

    @Override
    public String getEmail() {
        return ptUser.getEmail();
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return ptUser.getPassword();
    }

    @Override
    public String getUsername() {
        return ptUser.getUsername();
    }

    /**
     * Get the OAuth 2.0 token attributes
     *
     * @return the OAuth 2.0 token attributes
     */
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    /**
     * Returns the name of the authenticated <code>Principal</code>. Never
     * <code>null</code>.
     *
     * @return the name of the authenticated <code>Principal</code>
     */
    @Override
    public String getName() {
        return ptUser.getUsername();
    }
}