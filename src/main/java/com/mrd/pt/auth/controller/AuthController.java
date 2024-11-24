package com.mrd.pt.auth.controller;

import com.mrd.pt.auth.dto.AuthDTO;
import com.mrd.pt.auth.entity.AuthPtUser;
import com.mrd.pt.auth.service.AuthService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;
//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Resource
    private RegisteredClientRepository registeredClientRepository;

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody AuthDTO.LoginRequest userLogin) throws IllegalAccessException {
//        Authentication authentication =
//                authenticationManager
//                        .authenticate(new UsernamePasswordAuthenticationToken(
//                                userLogin.username(),
//                                userLogin.password()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        AuthPtUser userDetails = (AuthPtUser) authentication.getPrincipal();
//
//
//        log.info("Token requested for user :{}", authentication.getAuthorities());
//        String token = authService.generateToken(authentication);
//
//        AuthDTO.Response response = new AuthDTO.Response("User logged in successfully", token);
//
//        return ResponseEntity.ok(response);
//    }

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
                        .redirectUri("https://www.baidu.com")
                        .scope(OidcScopes.OPENID)
                        .scope(OidcScopes.PROFILE)
                        .scope("message.read")
                        .scope("message.write")
                        .tokenSettings(TokenSettings.builder().build())
                        .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                        .build();

        registeredClientRepository.save(registeredClient);
    }
}