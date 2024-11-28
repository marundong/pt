package com.mrd.pt.auth.service.oauth2.jpa;

import com.mrd.pt.auth.entity.oauth2.jpa.AuthorizationConsent;
import com.mrd.pt.auth.repository.oauth2.jpa.AuthorizationConsentRepository;
import com.mrd.pt.auth.service.oauth2.redis.RedisOAuth2AuthorizationConsentService;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Component
public class JpaOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {
	private final AuthorizationConsentRepository authorizationConsentRepository;
	private final RedisOAuth2AuthorizationConsentService redisOAuth2AuthorizationConsentService;
	private final RegisteredClientRepository jpaRegisteredClientRepository;

	public JpaOAuth2AuthorizationConsentService(AuthorizationConsentRepository authorizationConsentRepository,RedisOAuth2AuthorizationConsentService redisOAuth2AuthorizationConsentService, RegisteredClientRepository jpaRegisteredClientRepository) {
		Assert.notNull(authorizationConsentRepository, "authorizationConsentRepository cannot be null");
		Assert.notNull(jpaRegisteredClientRepository, "registeredClientRepository cannot be null");
		this.authorizationConsentRepository = authorizationConsentRepository;
		this.redisOAuth2AuthorizationConsentService = redisOAuth2AuthorizationConsentService;
		this.jpaRegisteredClientRepository = jpaRegisteredClientRepository;
	}

	@Override
	public void save(OAuth2AuthorizationConsent authorizationConsent) {
		Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
		AuthorizationConsent save = this.authorizationConsentRepository.save(toEntity(authorizationConsent));
		OAuth2AuthorizationConsent oAuth2AuthorizationConsent = toObject(save);
		redisOAuth2AuthorizationConsentService.save(oAuth2AuthorizationConsent);
	}

	@Override
	public void remove(OAuth2AuthorizationConsent authorizationConsent) {
		Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
		this.authorizationConsentRepository.deleteByRegisteredClientIdAndPrincipalName(
				authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());

		redisOAuth2AuthorizationConsentService.remove(authorizationConsent);
	}

	@Override
	public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
		Assert.hasText(registeredClientId, "registeredClientId cannot be empty");
		Assert.hasText(principalName, "principalName cannot be empty");
		OAuth2AuthorizationConsent oAuth2AuthorizationConsent = redisOAuth2AuthorizationConsentService.findById(registeredClientId, principalName);
		if(oAuth2AuthorizationConsent == null){
			oAuth2AuthorizationConsent =  this.authorizationConsentRepository.findByRegisteredClientIdAndPrincipalName(
					registeredClientId, principalName).map(this::toObject).orElse(null);
			if(oAuth2AuthorizationConsent !=null){
				redisOAuth2AuthorizationConsentService.save(oAuth2AuthorizationConsent);
			}
		}
		return oAuth2AuthorizationConsent;
	}

	private OAuth2AuthorizationConsent toObject(AuthorizationConsent authorizationConsent) {
		String registeredClientId = authorizationConsent.getRegisteredClientId();
		RegisteredClient registeredClient = this.jpaRegisteredClientRepository.findById(registeredClientId);
		if (registeredClient == null) {
			throw new DataRetrievalFailureException(
					"The RegisteredClient with id '" + registeredClientId + "' was not found in the RegisteredClientRepository.");
		}

		OAuth2AuthorizationConsent.Builder builder = OAuth2AuthorizationConsent.withId(
				registeredClientId, authorizationConsent.getPrincipalName());
		if (authorizationConsent.getAuthorities() != null) {
			for (String authority : StringUtils.commaDelimitedListToSet(authorizationConsent.getAuthorities())) {
				builder.authority(new SimpleGrantedAuthority(authority));
			}
		}

		return builder.build();
	}

	private AuthorizationConsent toEntity(OAuth2AuthorizationConsent authorizationConsent) {
		AuthorizationConsent entity = new AuthorizationConsent();
		entity.setRegisteredClientId(authorizationConsent.getRegisteredClientId());
		entity.setPrincipalName(authorizationConsent.getPrincipalName());

		Set<String> authorities = new HashSet<>();
		for (GrantedAuthority authority : authorizationConsent.getAuthorities()) {
			authorities.add(authority.getAuthority());
		}
		entity.setAuthorities(StringUtils.collectionToCommaDelimitedString(authorities));

		return entity;
	}
}