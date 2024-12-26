package com.mrd.pt.auth.controller;

import com.mrd.pt.auth.request.UserRegistryRequest;
import com.mrd.pt.auth.service.registry.RegistryService;
import com.mrd.pt.common.response.JsonResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author marundong
 */
@RestController
@RequestMapping("registry")
@AllArgsConstructor
public class RegistryController {

    private final RegistryService registryService;

    @PostMapping("user")
    public JsonResponse registryUser(@RequestBody UserRegistryRequest userRegistryRequest){
        registryService.userInfoResult(userRegistryRequest);
        return JsonResponse.success();
    }
}
