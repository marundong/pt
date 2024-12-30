package com.mrd.pt.auth.redis;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.mrd.pt.auth.entity.AuthPtUser;
import com.mrd.pt.auth.entity.AuthPtUserMixin;
import com.mrd.pt.system.entity.PtUser;
import com.mrd.pt.auth.entity.PtUserMixin;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.jackson2.SecurityJackson2Modules;

@ReadingConverter
public class BytesToUsernamePasswordAuthenticationTokenConverter
		implements Converter<byte[], UsernamePasswordAuthenticationToken> {

	private final Jackson2JsonRedisSerializer<UsernamePasswordAuthenticationToken> serializer;

	public BytesToUsernamePasswordAuthenticationTokenConverter() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModules(SecurityJackson2Modules
			.getModules(BytesToUsernamePasswordAuthenticationTokenConverter.class.getClassLoader()));
		objectMapper.addMixIn(AuthPtUser.class, AuthPtUserMixin.class);
		objectMapper.addMixIn(PtUser.class, PtUserMixin.class);
		this.serializer = new Jackson2JsonRedisSerializer<>(objectMapper, UsernamePasswordAuthenticationToken.class);
	}

	@Override
	public UsernamePasswordAuthenticationToken convert(byte[] value) {
		return this.serializer.deserialize(value);
	}

}