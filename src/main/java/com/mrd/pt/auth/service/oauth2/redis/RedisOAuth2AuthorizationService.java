package com.mrd.pt.auth.service.oauth2.redis;


import com.mrd.pt.auth.entity.oauth2.redis.OAuth2AuthorizationGrantAuthorization;
import com.mrd.pt.auth.redis.RedisOAuth2ModelMapper;
import com.mrd.pt.auth.repository.oauth2.redis.OAuth2AuthorizationGrantAuthorizationRepository;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class RedisOAuth2AuthorizationService implements OAuth2AuthorizationService {

	private final RegisteredClientRepository registeredClientRepository;

	private final OAuth2AuthorizationGrantAuthorizationRepository authorizationGrantAuthorizationRepository;

	public RedisOAuth2AuthorizationService(RegisteredClientRepository registeredClientRepository,
			OAuth2AuthorizationGrantAuthorizationRepository authorizationGrantAuthorizationRepository) {
		Assert.notNull(registeredClientRepository, "registeredClientRepository cannot be null");
		Assert.notNull(authorizationGrantAuthorizationRepository,
				"authorizationGrantAuthorizationRepository cannot be null");
		this.registeredClientRepository = registeredClientRepository;
		this.authorizationGrantAuthorizationRepository = authorizationGrantAuthorizationRepository;
	}

	@Override
	public void save(OAuth2Authorization authorization) {
		Assert.notNull(authorization, "authorization cannot be null");
		OAuth2AuthorizationGrantAuthorization authorizationGrantAuthorization = RedisOAuth2ModelMapper.convertOAuth2AuthorizationGrantAuthorization(authorization);
		this.authorizationGrantAuthorizationRepository.save(authorizationGrantAuthorization);
	}

	@Override
	public void remove(OAuth2Authorization authorization) {
		Assert.notNull(authorization, "authorization cannot be null");
		this.authorizationGrantAuthorizationRepository.deleteById(authorization.getId());
	}

	@Nullable
	@Override
	public OAuth2Authorization findById(String id) {
		Assert.hasText(id, "id cannot be empty");
		return this.authorizationGrantAuthorizationRepository.findById(id)
			.map(this::toOAuth2Authorization)
			.orElse(null);
	}

	@Nullable
	@Override
	public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
		Assert.hasText(token, "token cannot be empty");
		OAuth2AuthorizationGrantAuthorization authorizationGrantAuthorization = null;
		if (tokenType == null) {
			authorizationGrantAuthorization = this.authorizationGrantAuthorizationRepository
				.findByStateOrAuthorizationCode_TokenValue(token, token);
			if (authorizationGrantAuthorization == null) {
				authorizationGrantAuthorization = this.authorizationGrantAuthorizationRepository
						.findByAccessToken_TokenValueOrRefreshToken_TokenValue(token, token);
			}
			if (authorizationGrantAuthorization == null) {
				authorizationGrantAuthorization = this.authorizationGrantAuthorizationRepository
						.findByIdToken_TokenValue(token);
			}
			if (authorizationGrantAuthorization == null) {
				authorizationGrantAuthorization = this.authorizationGrantAuthorizationRepository
						.findByDeviceStateOrDeviceCode_TokenValueOrUserCode_TokenValue(token, token, token);
			}
		}
		else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
			authorizationGrantAuthorization = this.authorizationGrantAuthorizationRepository.findByState(token);
			if (authorizationGrantAuthorization == null) {
				authorizationGrantAuthorization = this.authorizationGrantAuthorizationRepository
					.findByDeviceState(token);
			}
		}
		else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
			authorizationGrantAuthorization = this.authorizationGrantAuthorizationRepository
				.findByAuthorizationCode_TokenValue(token);
		}
		else if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType)) {
			authorizationGrantAuthorization = this.authorizationGrantAuthorizationRepository
				.findByAccessToken_TokenValue(token);
		}
		else if (OidcParameterNames.ID_TOKEN.equals(tokenType.getValue())) {
			authorizationGrantAuthorization = this.authorizationGrantAuthorizationRepository
				.findByIdToken_TokenValue(token);
		}
		else if (OAuth2TokenType.REFRESH_TOKEN.equals(tokenType)) {
			authorizationGrantAuthorization = this.authorizationGrantAuthorizationRepository
				.findByRefreshToken_TokenValue(token);
		}
		else if (OAuth2ParameterNames.USER_CODE.equals(tokenType.getValue())) {
			authorizationGrantAuthorization = this.authorizationGrantAuthorizationRepository
				.findByUserCode_TokenValue(token);
		}
		else if (OAuth2ParameterNames.DEVICE_CODE.equals(tokenType.getValue())) {
			authorizationGrantAuthorization = this.authorizationGrantAuthorizationRepository
				.findByDeviceCode_TokenValue(token);
		}
		return authorizationGrantAuthorization != null ? toOAuth2Authorization(authorizationGrantAuthorization) : null;
	}

	private OAuth2Authorization toOAuth2Authorization(
			OAuth2AuthorizationGrantAuthorization authorizationGrantAuthorization) {
		RegisteredClient registeredClient = this.registeredClientRepository
			.findById(authorizationGrantAuthorization.getRegisteredClientId());
		OAuth2Authorization.Builder builder = OAuth2Authorization.withRegisteredClient(registeredClient);
		RedisOAuth2ModelMapper.mapOAuth2AuthorizationGrantAuthorization(authorizationGrantAuthorization, builder);
		return builder.build();
	}

}