package com.mrd.pt.system.controller;

import com.google.common.collect.Lists;
import com.mrd.pt.auth.entity.AuthPtUser;
import com.mrd.pt.common.response.JsonResponse;
import com.mrd.pt.system.entity.PtUser;
import com.mrd.pt.system.response.UserInfoResult;
import com.mrd.pt.system.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author marundong
 */
@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

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

    @PostMapping("list")
    public JsonResponse<Map<String,Object>> list(){
        List<PtUser> list = userService.list();
        Map<String,Object> map = new HashMap<>();
        map.put("list",list);
        map.put("total",1);
        return JsonResponse.success(map);
    }

}
