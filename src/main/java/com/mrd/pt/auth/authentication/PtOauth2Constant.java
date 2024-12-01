package com.mrd.pt.auth.authentication;

import org.springframework.security.oauth2.core.AuthorizationGrantType;

public interface PtOauth2Constant {

    AuthorizationGrantType GRANT_TYPE_PT_USER = new AuthorizationGrantType("authorization_pt_user");;
}
