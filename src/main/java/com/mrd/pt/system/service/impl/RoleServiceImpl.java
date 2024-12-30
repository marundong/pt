package com.mrd.pt.system.service.impl;

import com.mrd.pt.system.entity.PtRole;
import com.mrd.pt.system.repository.RoleRepository;
import com.mrd.pt.system.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author marundong
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    @Override
    public PtRole save(PtRole ptRole) {
        return roleRepository.save(ptRole);
    }

    @Override
    public List<PtRole> list() {
        return roleRepository.findAll();
    }
}
