package com.mrd.pt.auth.repository;

import com.mrd.pt.auth.entity.PtUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<PtUser, String> {
    Optional<PtUser> findByUsername(String username);
}