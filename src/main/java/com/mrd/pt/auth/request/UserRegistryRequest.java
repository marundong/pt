package com.mrd.pt.auth.request;

import lombok.Data;

/**
 * @author marundong
 */
@Data
public class UserRegistryRequest {

    private String username;

    private String password;

    private String realName;
}
