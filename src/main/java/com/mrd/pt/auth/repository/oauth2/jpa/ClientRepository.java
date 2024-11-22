package com.mrd.pt.auth.repository.oauth2.jpa;

import com.mrd.pt.auth.entity.oauth2.jpa.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
	Optional<Client> findByClientId(String clientId);
}