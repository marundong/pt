package com.mrd.pt.auth.config;

import com.mrd.pt.auth.authentication.PtAuthenticationEntryPoint;
import com.mrd.pt.auth.handler.PtAccessDeniedHandler;
import com.mrd.pt.auth.authentication.PtUserGrantAuthenticationConvert;
import com.mrd.pt.auth.authentication.PtUserGrantAuthenticationProvider;
import com.mrd.pt.auth.handler.PtOauth2AuthenticationSuccessHandler;
import com.mrd.pt.auth.handler.PtOauth2ErrorAuthenticationFailureHandler;
import com.mrd.pt.auth.service.JpaUserDetailsService;
import com.mrd.pt.auth.service.oauth2.jpa.JpaOAuth2AuthorizationConsentService;
import com.mrd.pt.auth.service.oauth2.jpa.JpaOAuth2AuthorizationService;
import com.mrd.pt.auth.service.oauth2.jpa.JpaRegisteredClientRepository;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2AccessTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AuthorizationServerSecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http,
                                                                      JpaRegisteredClientRepository jpaRegisteredClientRepository,
                                                                      JpaOAuth2AuthorizationService jpaOAuth2AuthorizationService,
                                                                      JpaOAuth2AuthorizationConsentService jpaOAuth2AuthorizationConsentService,
                                                                      JpaUserDetailsService userDetailsService,
                                                                      PtOauth2ErrorAuthenticationFailureHandler ptOauth2ErrorAuthenticationFailureHandler,
                                                                      PtOauth2AuthenticationSuccessHandler ptOauth2AuthenticationSuccessHandler,
                                                                      OAuth2TokenGenerator<?> tokenGenerator) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .registeredClientRepository(jpaRegisteredClientRepository)
                .authorizationService(jpaOAuth2AuthorizationService)
                .authorizationConsentService(jpaOAuth2AuthorizationConsentService)
                .tokenEndpoint(tokenEndpointConfigurer -> {
                    tokenEndpointConfigurer.errorResponseHandler(ptOauth2ErrorAuthenticationFailureHandler);
                    tokenEndpointConfigurer.accessTokenRequestConverter(new PtUserGrantAuthenticationConvert());
                    tokenEndpointConfigurer.authenticationProvider(new PtUserGrantAuthenticationProvider(userDetailsService, passwordEncoder(), jpaOAuth2AuthorizationService, tokenGenerator));
                    tokenEndpointConfigurer.accessTokenResponseHandler(ptOauth2AuthenticationSuccessHandler);
                })
                .oidc(Customizer.withDefaults());
        return http
                .securityMatcher(http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).getEndpointsMatcher())
                .exceptionHandling((exceptions) -> exceptions.defaultAccessDeniedHandlerFor(new PtAccessDeniedHandler(), AnyRequestMatcher.INSTANCE)
                                .authenticationEntryPoint(new PtAuthenticationEntryPoint())
//                        .defaultAuthenticationEntryPointFor(
//                                new LoginUrlAuthenticationEntryPoint("/login"),
//                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
//                        )
                )
                .userDetailsService(userDetailsService)
                .build();
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    public JwtDecoder jwtDecoder(RsaKeyConfigProperties rsaKeyConfigProperties) {
        NimbusJwtDecoder build = NimbusJwtDecoder.withPublicKey(rsaKeyConfigProperties.publicKey()).build();
        return build;
    }

    @Bean
    JwtEncoder jwtEncoder(RsaKeyConfigProperties rsaKeyConfigProperties) {
        RSAKey jwk = new RSAKey.Builder(rsaKeyConfigProperties.publicKey()).privateKey(rsaKeyConfigProperties.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public OAuth2TokenGenerator<?> tokenGenerator(JWKSource<SecurityContext> jwkSource,OAuth2TokenCustomizer<OAuth2TokenClaimsContext> oAuth2TokenCustomizer) {
        JwtGenerator jwtGenerator = new JwtGenerator(new NimbusJwtEncoder(jwkSource));
        OAuth2AccessTokenGenerator oAuth2AccessTokenGenerator = new OAuth2AccessTokenGenerator();
        oAuth2AccessTokenGenerator.setAccessTokenCustomizer(oAuth2TokenCustomizer);
        OAuth2RefreshTokenGenerator oAuth2RefreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(jwtGenerator, oAuth2AccessTokenGenerator, oAuth2RefreshTokenGenerator);

    }
}