package com.mrd.pt.auth.service.registry;

import com.mrd.pt.auth.request.UserRegistryRequest;

/**
 * @author marundong
 */
public interface RegistryService {


    void registryUser(UserRegistryRequest userRegistryRequest);
}
