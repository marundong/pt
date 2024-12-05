package com.mrd.pt.system.controller;

import com.mrd.pt.auth.entity.AuthPtUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author marundong
 */
@RequestMapping("system")
@RestController
public class SystemController {

    @GetMapping("hello")
    public Object testHello(){

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getPrincipal();
        return principal;
    }
}
