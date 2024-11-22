package com.mrd.pt.auth.repository.oauth2.mapper;

import com.mrd.pt.auth.entity.oauth2.redis.OAuth2AuthorizationGrantAuthorization;
import com.mrd.pt.auth.entity.oauth2.redis.OAuth2RegisteredClient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

/**
 * @author marundong
 */
@Mapper
public interface Oauth2StructMapper {

    Oauth2StructMapper INSTANCE = Mappers.getMapper(Oauth2StructMapper.class);

    OAuth2RegisteredClient convertOAuth2RegisteredClient(RegisteredClient registeredClient);

    RegisteredClient convertRegisteredClient(OAuth2RegisteredClient oAuth2RegisteredClient);

    OAuth2AuthorizationGrantAuthorization convertOAuth2AuthorizationGrantAuthorization(OAuth2Authorization oAuth2Authorization);
}
