package com.mrd.pt.auth.request;

import lombok.Data;

/**
 * @author marundong
 */
@Data
public class AuthResult {

    private String accessToken;

    private String refreshToken;

    private int expire;
}
