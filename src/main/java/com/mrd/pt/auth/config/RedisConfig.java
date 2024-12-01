package com.mrd.pt.auth.config;

import com.mrd.pt.auth.redis.BytesToClaimsHolderConverter;
import com.mrd.pt.auth.redis.BytesToOAuth2AuthorizationRequestConverter;
import com.mrd.pt.auth.redis.BytesToUsernamePasswordAuthenticationTokenConverter;
import com.mrd.pt.auth.redis.ClaimsHolderToBytesConverter;
import com.mrd.pt.auth.redis.OAuth2AuthorizationRequestToBytesConverter;
import com.mrd.pt.auth.redis.UsernamePasswordAuthenticationTokenToBytesConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.Arrays;

@EnableRedisRepositories("com.mrd.pt.auth.repository.oauth2.redis")
@Configuration(proxyBeanMethods = false)
public class RedisConfig {

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new JedisConnectionFactory();
	}

	@Bean
	public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}

	@Bean
	public RedisCustomConversions redisCustomConversions() {
		return new RedisCustomConversions(Arrays.asList(new UsernamePasswordAuthenticationTokenToBytesConverter(),
				new BytesToUsernamePasswordAuthenticationTokenConverter(),
				new OAuth2AuthorizationRequestToBytesConverter(), new BytesToOAuth2AuthorizationRequestConverter(),
				new ClaimsHolderToBytesConverter(), new BytesToClaimsHolderConverter()));
	}
}