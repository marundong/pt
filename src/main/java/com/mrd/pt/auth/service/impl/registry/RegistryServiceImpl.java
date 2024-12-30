package com.mrd.pt.auth.service.impl.registry;

import com.mrd.pt.system.entity.PtUser;
import com.mrd.pt.system.repository.UserRepository;
import com.mrd.pt.auth.request.UserRegistryRequest;
import com.mrd.pt.auth.service.registry.RegistryService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author marundong
 */
@Service
@AllArgsConstructor
public class RegistryServiceImpl implements RegistryService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    /**
     * 注册用户
     *
     * @param userRegistryRequest 用户注册参数
     */
    @Override
    public void registryUser(UserRegistryRequest userRegistryRequest){

        String encode = passwordEncoder.encode(userRegistryRequest.getPassword());
        PtUser ptUser = new PtUser();
        ptUser.setUsername(userRegistryRequest.getUsername());
        ptUser.setPassword(encode);
        userRepository.save(ptUser);
    }
}
