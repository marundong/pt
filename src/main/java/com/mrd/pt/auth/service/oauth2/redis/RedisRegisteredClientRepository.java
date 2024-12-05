package com.mrd.pt.auth.service.oauth2.redis;


import com.mrd.pt.auth.entity.oauth2.redis.OAuth2RegisteredClient;
import com.mrd.pt.auth.redis.RedisOAuth2ModelMapper;
import com.mrd.pt.auth.repository.oauth2.redis.OAuth2RegisteredClientRepository;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class RedisRegisteredClientRepository{

	private final OAuth2RegisteredClientRepository registeredClientRepository;

	public RedisRegisteredClientRepository(OAuth2RegisteredClientRepository registeredClientRepository) {
		Assert.notNull(registeredClientRepository, "registeredClientRepository cannot be null");
		this.registeredClientRepository = registeredClientRepository;
	}

	public void save(RegisteredClient registeredClient) {
		Assert.notNull(registeredClient, "registeredClient cannot be null");
		OAuth2RegisteredClient oauth2RegisteredClient = RedisOAuth2ModelMapper.convertOAuth2RegisteredClient(registeredClient);
		this.registeredClientRepository.save(oauth2RegisteredClient);
	}

	@Nullable
	public RegisteredClient findById(String id) {
		Assert.hasText(id, "id cannot be empty");
		return this.registeredClientRepository.findById(id).map(RedisOAuth2ModelMapper::convertRegisteredClient).orElse(null);
	}

	@Nullable
	public RegisteredClient findByClientId(String clientId) {
		Assert.hasText(clientId, "clientId cannot be empty");
		OAuth2RegisteredClient oauth2RegisteredClient = this.registeredClientRepository.findByClientId(clientId);
		return oauth2RegisteredClient != null ? RedisOAuth2ModelMapper.convertRegisteredClient(oauth2RegisteredClient) : null;
	}

}