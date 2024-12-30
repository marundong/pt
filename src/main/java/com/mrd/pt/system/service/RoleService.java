package com.mrd.pt.system.service;

import com.mrd.pt.system.entity.PtRole;

import java.util.List;

/**
 * @author marundong
 */
public interface RoleService {

    PtRole save(PtRole ptRole);

    List<PtRole> list();


}
