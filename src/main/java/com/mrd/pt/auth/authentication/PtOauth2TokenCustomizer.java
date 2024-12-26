package com.mrd.pt.auth.authentication;

import com.mrd.pt.auth.entity.AuthPtUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author marundong
 */
@Component
public class PtOauth2TokenCustomizer implements OAuth2TokenCustomizer<OAuth2TokenClaimsContext> {
    /**
     * Customize the OAuth 2.0 Token attributes.
     *
     * @param context the context containing the OAuth 2.0 Token attributes
     */
    @Override
    public void customize(OAuth2TokenClaimsContext context) {
        if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
            Authentication principal = context.getPrincipal();
            Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();

            if(principal instanceof PtUserAuthenticationToken ptUserAuthenticationToken){
                context.getClaims().claims((claims) -> {
                    claims.put("userId", ptUserAuthenticationToken.getAuthPtUser().getId());
                    claims.put("userName", ptUserAuthenticationToken.getAuthPtUser().getUsername());
                });
            }

        }
    }
}
