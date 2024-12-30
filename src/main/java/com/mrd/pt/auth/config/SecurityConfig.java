package com.mrd.pt.auth.config;

import com.mrd.pt.auth.handler.PtAccessDeniedHandler;
import com.mrd.pt.auth.authentication.PtAuthenticationEntryPoint;
import com.mrd.pt.auth.authentication.PtOpaqueTokenIntrospector;
import com.mrd.pt.auth.service.JpaUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
                                                          RedisTemplate<String,Object> redisTemplate,
                                                          JpaUserDetailsService userDetailsService)
            throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/error/**").permitAll();
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.requestMatchers("/test/test").permitAll();
                    auth.requestMatchers("/registry/**").permitAll();
                    auth.anyRequest().authenticated();
                }).oauth2ResourceServer((oauth2ResourceServer) -> oauth2ResourceServer
//                        .opaqueToken(opaqueTokenConfigurer -> opaqueTokenConfigurer.introspectionUri("http://127.0.0.1:8080/oauth2/introspect").introspectionClientCredentials("1", "1"))
                        .opaqueToken(opaqueTokenConfigurer -> opaqueTokenConfigurer.introspector(new PtOpaqueTokenIntrospector(userDetailsService,redisTemplate,"http://127.0.0.1:8080/oauth2/introspect","1","1")))
                        .authenticationEntryPoint(new PtAuthenticationEntryPoint())
                        .accessDeniedHandler(new PtAccessDeniedHandler())
                )
        ;
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}