package com.mrd.pt.auth.repository.oauth2.redis;


import com.mrd.pt.auth.entity.oauth2.redis.OAuth2RegisteredClient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuth2RegisteredClientRepository extends CrudRepository<OAuth2RegisteredClient, String> {

	OAuth2RegisteredClient findByClientId(String clientId);

}