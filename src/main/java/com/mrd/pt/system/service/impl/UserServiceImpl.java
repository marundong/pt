package com.mrd.pt.system.service.impl;

import com.mrd.pt.system.entity.PtUser;
import com.mrd.pt.system.repository.UserRepository;
import com.mrd.pt.system.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author marundong
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<PtUser> list() {
        return userRepository.findAll();
    }
}
