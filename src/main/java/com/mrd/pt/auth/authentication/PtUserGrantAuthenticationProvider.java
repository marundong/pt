package com.mrd.pt.auth.authentication;

import com.mrd.pt.auth.code.AuthErrorResultCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClaimAccessor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.security.Principal;

@Slf4j
@AllArgsConstructor
public class PtUserGrantAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final OAuth2AuthorizationService authorizationService;

    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PtUserGrantAuthenticationToken ptUserGrantAuthenticationToken = (PtUserGrantAuthenticationToken) authentication;
        AuthorizationGrantType grantType = ptUserGrantAuthenticationToken.getGrantType();
        String username = ptUserGrantAuthenticationToken.getUsername();
        String password = ptUserGrantAuthenticationToken.getPassword();
        OAuth2ClientAuthenticationToken clientAuthenticationToken = PtOAuth2AuthenticationProviderUtils.getAuthenticatedClientElseThrowInvalidClient(ptUserGrantAuthenticationToken);
        RegisteredClient registeredClient = clientAuthenticationToken.getRegisteredClient();
        if (registeredClient == null || registeredClient.getAuthorizationGrantTypes() == null || !registeredClient.getAuthorizationGrantTypes().contains(grantType)) {
            throw new OAuth2AuthenticationException(AuthErrorResultCode.AUTH_FAILED_UNAUTHORIZED_CLIENT.getOauth2ErrorCode());
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new OAuth2AuthenticationException(AuthErrorResultCode.AUTH_FAILED_INVALID_USERNAME_PASSWORD.getOauth2ErrorCode());
        }
        UsernamePasswordAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(userDetails, clientAuthenticationToken, userDetails.getAuthorities());
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(authenticated)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizedScopes(registeredClient.getScopes())
                .authorizationGrantType(grantType)
                .authorizationGrant(ptUserGrantAuthenticationToken);
        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient);
        authorizationBuilder.principalName(authenticated.getName())
                .authorizationGrantType(grantType)
                .attribute(Principal.class.getName(), authenticated)
                .authorizationGrantType(grantType)
        ;
        // ----- Access token -----
        OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
        if (generatedAccessToken == null) {
            log.error("token generation failed");
            throw new OAuth2AuthenticationException("the access token could not be generated");
        }

        if (log.isTraceEnabled()) {
            log.trace("Generated ptUser access token");
        }

        OAuth2AccessToken accessToken = PtOAuth2AuthenticationProviderUtils.accessToken(authorizationBuilder,
                generatedAccessToken, tokenContext);
        if (generatedAccessToken instanceof ClaimAccessor) {
            authorizationBuilder
                    .token(accessToken,
                            (metadata) -> metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME,
                                    ((ClaimAccessor) generatedAccessToken).getClaims()))
                    // 0.4.0 新增的方法
                    .authorizedScopes(registeredClient.getScopes())
                    .attribute(Principal.class.getName(), authenticated);
        }
        // ----- Refresh token -----
        OAuth2RefreshToken refreshToken = null;
        // Do not issue refresh token to public client
        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN)) {
            tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
            OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
            if (generatedRefreshToken != null) {
                if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                    log.error("refresh token generation failed");
                    throw new OAuth2AuthenticationException("The token generator failed to generate a valid refresh token.");
                }

                if (log.isTraceEnabled()) {
                    log.trace("Generated refresh token");
                }

                refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
                authorizationBuilder.refreshToken(refreshToken);
            }
        }

        OAuth2Authorization oAuth2Authorization = authorizationBuilder.build();

        this.authorizationService.save(oAuth2Authorization);
        return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientAuthenticationToken, accessToken, refreshToken, ptUserGrantAuthenticationToken.getAdditionalParameters());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PtUserGrantAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
