package com.mrd.pt.auth.authentication;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * @author marundong
 */
public class PtOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final OpaqueTokenIntrospector introspector;

    private final RedisTemplate<String,Object> redisTemplate;

    private String clientId;

    private String clientSecret;
    private String uri;
    /**
     * Introspect and verify the given token, returning its attributes.
     * <p>
     * Returning a {@link Map} is indicative that the token is valid.
     *
     * @param token the token to introspect
     * @return the token's attributes
     */
    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        Object o = redisTemplate.opsForValue().get(tokenKey(token));
        if(o!=null){
            return (OAuth2AuthenticatedPrincipal)o;
        }
        OAuth2AuthenticatedPrincipal introspect = introspector.introspect(token);
        Object exp = introspect.getAttribute("exp");
        if(exp !=null && exp instanceof Instant expIstance){
            LocalDateTime localDateTime = DateUtil.toLocalDateTime(expIstance);
            long between = LocalDateTimeUtil.between(LocalDateTime.now(), localDateTime, ChronoUnit.SECONDS);
            if(between>60){
                redisTemplate.opsForValue().set(tokenKey(token),introspect,between-60,TimeUnit.SECONDS);
            }
        }
        return introspect;
    }

    public PtOpaqueTokenIntrospector(RedisTemplate<String,Object> redisTemplate, String uri,String clientId,String clientSecret) {
        this.redisTemplate = redisTemplate;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.uri = uri;
        this.introspector = new NimbusOpaqueTokenIntrospector(uri,clientId,clientSecret);

    }

    private String tokenKey(String token){
        return this.uri+":"+this.clientId+":"+token;
    }
}
