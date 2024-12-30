package com.mrd.pt.system.repository;

import com.mrd.pt.system.entity.PtRole;
import com.mrd.pt.system.entity.PtUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<PtRole, Long> {
}