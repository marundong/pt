//package com.mrd.pt.auth.config;
//
//import java.util.Arrays;
//
//import com.mrd.pt.auth.convert.BytesToClaimsHolderConverter;
//import com.mrd.pt.auth.convert.BytesToOAuth2AuthorizationRequestConverter;
//import com.mrd.pt.auth.convert.BytesToUsernamePasswordAuthenticationTokenConverter;
//import com.mrd.pt.auth.convert.ClaimsHolderToBytesConverter;
//import com.mrd.pt.auth.convert.OAuth2AuthorizationRequestToBytesConverter;
//import com.mrd.pt.auth.convert.UsernamePasswordAuthenticationTokenToBytesConverter;
//import com.mrd.pt.auth.repository.oauth2.redis.OAuth2AuthorizationGrantAuthorizationRepository;
//import com.mrd.pt.auth.repository.oauth2.redis.OAuth2RegisteredClientRepository;
//import com.mrd.pt.auth.repository.oauth2.redis.OAuth2UserConsentRepository;
//import com.mrd.pt.auth.service.oauth2.redis.RedisRegisteredClientRepository;
//import com.mrd.pt.auth.service.oauth2.redis.RedisOAuth2AuthorizationConsentService;
//import com.mrd.pt.auth.service.oauth2.redis.RedisOAuth2AuthorizationService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.convert.RedisCustomConversions;
//import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
//
//@EnableRedisRepositories("com.mrd.pt.auth.repository.oauth2.redis")
//@Configuration(proxyBeanMethods = false)
//public class RedisConfig {
//
//	@Bean
//	public RedisConnectionFactory redisConnectionFactory() {
//		return new JedisConnectionFactory();
//	}
//
//	@Bean
//	public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//		RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
//		redisTemplate.setConnectionFactory(redisConnectionFactory);
//		return redisTemplate;
//	}
//
//	@Bean
//	public RedisCustomConversions redisCustomConversions() {
//		return new RedisCustomConversions(Arrays.asList(new UsernamePasswordAuthenticationTokenToBytesConverter(),
//				new BytesToUsernamePasswordAuthenticationTokenConverter(),
//				new OAuth2AuthorizationRequestToBytesConverter(), new BytesToOAuth2AuthorizationRequestConverter(),
//				new ClaimsHolderToBytesConverter(), new BytesToClaimsHolderConverter()));
//	}
//
//	@Bean
//	public RedisRegisteredClientRepository registeredClientRepository(
//			OAuth2RegisteredClientRepository registeredClientRepository) {
//		return new RedisRegisteredClientRepository(registeredClientRepository);
//	}
//
//	@Bean
//	public RedisOAuth2AuthorizationService authorizationService(RegisteredClientRepository registeredClientRepository,
//																OAuth2AuthorizationGrantAuthorizationRepository authorizationGrantAuthorizationRepository) {
//		return new RedisOAuth2AuthorizationService(registeredClientRepository,
//				authorizationGrantAuthorizationRepository);
//	}
//
//	@Bean
//	public RedisOAuth2AuthorizationConsentService authorizationConsentService(
//			OAuth2UserConsentRepository userConsentRepository) {
//		return new RedisOAuth2AuthorizationConsentService(userConsentRepository);
//	}
//
//}