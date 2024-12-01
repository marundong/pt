package com.mrd.pt.auth.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Map;

public class PtUserGrantAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {
    /**
     * Sub-class constructor.
     *
     * @param authorizationGrantType the authorization grant type
     * @param clientPrincipal        the authenticated client principal
     * @param additionalParameters   the additional parameters
     */
    public PtUserGrantAuthenticationToken(AuthorizationGrantType authorizationGrantType, Authentication clientPrincipal, Map<String, Object> additionalParameters) {
        super(authorizationGrantType, clientPrincipal, additionalParameters);
    }
}
