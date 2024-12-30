package com.mrd.pt.auth.config;

import com.mrd.pt.auth.redis.BytesToClaimsHolderConverter;
import com.mrd.pt.auth.redis.BytesToOAuth2AuthorizationRequestConverter;
import com.mrd.pt.auth.redis.BytesToUsernamePasswordAuthenticationTokenConverter;
import com.mrd.pt.auth.redis.ClaimsHolderToBytesConverter;
import com.mrd.pt.auth.redis.OAuth2AuthorizationRequestToBytesConverter;
import com.mrd.pt.auth.redis.UsernamePasswordAuthenticationTokenToBytesConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.Arrays;

@EnableRedisRepositories("com.mrd.pt.auth.repository.oauth2.redis")
@Configuration(proxyBeanMethods = false)
public class AuthRedisConfig {

	@Bean
	public RedisCustomConversions redisCustomConversions() {
		return new RedisCustomConversions(Arrays.asList(new UsernamePasswordAuthenticationTokenToBytesConverter(),
				new BytesToUsernamePasswordAuthenticationTokenConverter(),
				new OAuth2AuthorizationRequestToBytesConverter(), new BytesToOAuth2AuthorizationRequestConverter(),
				new ClaimsHolderToBytesConverter(), new BytesToClaimsHolderConverter()));
	}
}