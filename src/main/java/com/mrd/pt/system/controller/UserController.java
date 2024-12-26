package com.mrd.pt.system.controller;

import com.mrd.pt.auth.entity.AuthPtUser;
import com.mrd.pt.common.response.JsonResponse;
import com.mrd.pt.system.response.UserInfoResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author marundong
 */
@RestController
@RequestMapping("user")
public class UserController {


    @GetMapping("userInfo")
    public JsonResponse<UserInfoResult> userInfo(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        UserInfoResult userInfoResult = new UserInfoResult();
        return JsonResponse.success(userInfoResult);
    }
}
