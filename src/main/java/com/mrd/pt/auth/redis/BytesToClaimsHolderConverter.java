package com.mrd.pt.auth.redis;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.mrd.pt.auth.entity.AuthPtUser;
import com.mrd.pt.auth.entity.AuthPtUserMixin;
import com.mrd.pt.auth.entity.PtUser;
import com.mrd.pt.auth.entity.PtUserMixin;
import com.mrd.pt.auth.entity.oauth2.redis.OAuth2AuthorizationGrantAuthorization;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;

@ReadingConverter
public class BytesToClaimsHolderConverter
		implements Converter<byte[], OAuth2AuthorizationGrantAuthorization.ClaimsHolder> {

	private final Jackson2JsonRedisSerializer<OAuth2AuthorizationGrantAuthorization.ClaimsHolder> serializer;

	public BytesToClaimsHolderConverter() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper
			.registerModules(SecurityJackson2Modules.getModules(BytesToClaimsHolderConverter.class.getClassLoader()));
		objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
		objectMapper.addMixIn(AuthPtUser.class, AuthPtUserMixin.class);
		objectMapper.addMixIn(PtUser.class, PtUserMixin.class);
		objectMapper.addMixIn(OAuth2AuthorizationGrantAuthorization.ClaimsHolder.class, ClaimsHolderMixin.class);
		this.serializer = new Jackson2JsonRedisSerializer<>(objectMapper,
				OAuth2AuthorizationGrantAuthorization.ClaimsHolder.class);
	}

	@Override
	public OAuth2AuthorizationGrantAuthorization.ClaimsHolder convert(byte[] value) {
		return this.serializer.deserialize(value);
	}

}