package com.mrd.pt.auth.controller;

import com.google.common.collect.Lists;
import com.mrd.pt.auth.authentication.PtOauth2Constant;
import com.mrd.pt.common.response.JsonResponse;
import jakarta.annotation.Resource;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Resource
    private RegisteredClientRepository registeredClientRepository;


    @GetMapping("codes")
    public JsonResponse<List> codes(){
        return JsonResponse.success(Lists.newArrayList("AC_100100", "AC_100110", "AC_100120", "AC_100010"));
    }


    @GetMapping("initClient")
    public void initClient(){
        RegisteredClient registeredClient =
                RegisteredClient.withId(UUID.randomUUID().toString())
                        .clientId("1")
                        .clientSecret("{noop}1")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_JWT)
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                        .authorizationGrantType(PtOauth2Constant.GRANT_TYPE_PT_USER)
                        .redirectUri("http://127.0.0.1:8080/test/test")
                        .scope(OidcScopes.OPENID)
                        .scope(OidcScopes.PROFILE)
                        .scope("message.read")
                        .scope("message.write")
                        .tokenSettings(TokenSettings.builder()
                                .accessTokenTimeToLive(Duration.ofHours(2))
                                .refreshTokenTimeToLive(Duration.ofDays(30))
                                // 配置accessToken类型
                                .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                                .build())
                        .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                        .build();

        registeredClientRepository.save(registeredClient);
    }
}