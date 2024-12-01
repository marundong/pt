package com.mrd.pt.auth.entity.oauth2.redis;

import lombok.Getter;

import java.security.Principal;
import java.util.Set;

@Getter
public class PtUserAuthorizationGrantAuthorization extends OAuth2AuthorizationGrantAuthorization {

    private final Principal principal;

    public PtUserAuthorizationGrantAuthorization(String id, String registeredClientId, String principalName, Set<String> authorizedScopes, AccessToken accessToken, RefreshToken refreshToken,Principal principal) {
        super(id, registeredClientId, principalName, authorizedScopes, accessToken, refreshToken);
        this.principal = principal;
    }
}
