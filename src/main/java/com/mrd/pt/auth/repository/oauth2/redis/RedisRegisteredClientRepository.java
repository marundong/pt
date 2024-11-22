package com.mrd.pt.auth.repository.oauth2.redis;


import com.mrd.pt.auth.entity.oauth2.redis.OAuth2RegisteredClient;
import com.mrd.pt.auth.repository.oauth2.mapper.Oauth2StructMapper;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.util.Assert;

public class RedisRegisteredClientRepository implements RegisteredClientRepository {

	private final OAuth2RegisteredClientRepository registeredClientRepository;

	public RedisRegisteredClientRepository(OAuth2RegisteredClientRepository registeredClientRepository) {
		Assert.notNull(registeredClientRepository, "registeredClientRepository cannot be null");
		this.registeredClientRepository = registeredClientRepository;
	}

	@Override
	public void save(RegisteredClient registeredClient) {
		Assert.notNull(registeredClient, "registeredClient cannot be null");
		OAuth2RegisteredClient oauth2RegisteredClient = Oauth2StructMapper.INSTANCE.convertOAuth2RegisteredClient(registeredClient);
		this.registeredClientRepository.save(oauth2RegisteredClient);
	}

	@Nullable
	@Override
	public RegisteredClient findById(String id) {
		Assert.hasText(id, "id cannot be empty");
		return this.registeredClientRepository.findById(id).map(a->Oauth2StructMapper.INSTANCE.convertRegisteredClient(a)).orElse(null);
	}

	@Nullable
	@Override
	public RegisteredClient findByClientId(String clientId) {
		Assert.hasText(clientId, "clientId cannot be empty");
		OAuth2RegisteredClient oauth2RegisteredClient = this.registeredClientRepository.findByClientId(clientId);
		return oauth2RegisteredClient != null ? Oauth2StructMapper.INSTANCE.convertRegisteredClient(oauth2RegisteredClient) : null;
	}

}