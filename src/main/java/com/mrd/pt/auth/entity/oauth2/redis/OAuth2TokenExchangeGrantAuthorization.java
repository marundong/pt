package com.mrd.pt.auth.entity.oauth2.redis;

import java.util.Set;

public class OAuth2TokenExchangeGrantAuthorization extends OAuth2AuthorizationGrantAuthorization {

	public OAuth2TokenExchangeGrantAuthorization(String id, String registeredClientId, String principalName,
			Set<String> authorizedScopes, AccessToken accessToken) {
		super(id, registeredClientId, principalName, authorizedScopes, accessToken, null);
	}

}