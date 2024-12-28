package com.mrd.pt.system.response;

import lombok.Data;

import java.util.List;

/**
 * @author marundong
 */
@Data
public class UserInfoResult {

    private Long id;


    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String realName;

    /**
     * 用户角色
     */
    private List<String> roles;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户描述
     */
    private String desc;

    /**
     * 首页地址
     */
    private String homePath;

    /**
     * accessToken
     */
    private String token;
}
