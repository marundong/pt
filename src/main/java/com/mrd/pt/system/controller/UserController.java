package com.mrd.pt.system.controller;

import com.mrd.pt.auth.entity.AuthPtUser;
import com.mrd.pt.common.response.JsonResponse;
import com.mrd.pt.system.response.UserInfoResult;
import graphql.com.google.common.collect.Lists;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author marundong
 */
@RestController
@RequestMapping("user")
public class UserController {


    @GetMapping("info")
    public JsonResponse<UserInfoResult> userInfo(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getPrincipal();
        UserInfoResult userInfoResult = new UserInfoResult();
        if (principal instanceof AuthPtUser authPtUser) {
            userInfoResult.setUserId(authPtUser.getId().toString());
            userInfoResult.setUsername(authPtUser.getUsername());
            userInfoResult.setRealName(authPtUser.getUsername());
//            userInfoResult.setHomePath("/workspace");
            userInfoResult.setRoles(Lists.newArrayList("super"));
        }
        return JsonResponse.success(userInfoResult);
    }
}
