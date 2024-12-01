package com.mrd.pt.auth.code;

import com.mrd.pt.common.code.ResultCode;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;

/**
 * 授权异常码
 *
 * @author marundong
 */
public enum AuthErrorResultCode implements ResultCode {

    AUTH_FAILED_COMMON(false, "10000", "auth failed", ""),
    AUTH_FAILED_INVALID_REQUEST(false, "10001", OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ErrorCodes.INVALID_REQUEST),
    AUTH_FAILED_UNAUTHORIZED_CLIENT(false, "10002", OAuth2ErrorCodes.UNAUTHORIZED_CLIENT, OAuth2ErrorCodes.UNAUTHORIZED_CLIENT),
    AUTH_FAILED_ACCESS_DENIED(false, "10003", OAuth2ErrorCodes.ACCESS_DENIED, OAuth2ErrorCodes.ACCESS_DENIED),
    AUTH_FAILED_UNSUPPORTED_RESPONSE_TYPE(false, "10004", OAuth2ErrorCodes.UNSUPPORTED_RESPONSE_TYPE, OAuth2ErrorCodes.UNSUPPORTED_RESPONSE_TYPE),
    AUTH_FAILED_INVALID_SCOPE(false, "10005", OAuth2ErrorCodes.INVALID_SCOPE, OAuth2ErrorCodes.INVALID_SCOPE),
    AUTH_FAILED_INSUFFICIENT_SCOPE(false, "10006", OAuth2ErrorCodes.INSUFFICIENT_SCOPE, OAuth2ErrorCodes.INSUFFICIENT_SCOPE),
    AUTH_FAILED_INVALID_TOKEN(false, "10007", OAuth2ErrorCodes.INVALID_TOKEN, OAuth2ErrorCodes.INVALID_TOKEN),
    AUTH_FAILED_SERVER_ERROR(false, "10008", OAuth2ErrorCodes.SERVER_ERROR, OAuth2ErrorCodes.SERVER_ERROR),
    AUTH_FAILED_TEMPORARILY_UNAVAILABLE(false, "10009", OAuth2ErrorCodes.TEMPORARILY_UNAVAILABLE, OAuth2ErrorCodes.TEMPORARILY_UNAVAILABLE),
    AUTH_FAILED_INVALID_CLIENT(false, "10010", OAuth2ErrorCodes.INVALID_CLIENT, OAuth2ErrorCodes.INVALID_CLIENT),
    AUTH_FAILED_INVALID_GRANT(false, "10010", OAuth2ErrorCodes.INVALID_GRANT, OAuth2ErrorCodes.INVALID_GRANT),
    AUTH_FAILED_UNSUPPORTED_GRANT_TYPE(false, "10011", OAuth2ErrorCodes.UNSUPPORTED_GRANT_TYPE, OAuth2ErrorCodes.UNSUPPORTED_GRANT_TYPE),
    AUTH_FAILED_UNSUPPORTED_TOKEN_TYPE(false, "10012", OAuth2ErrorCodes.UNSUPPORTED_TOKEN_TYPE, OAuth2ErrorCodes.UNSUPPORTED_TOKEN_TYPE),
    AUTH_FAILED_INVALID_REDIRECT_URI(false, "10013", OAuth2ErrorCodes.INVALID_REDIRECT_URI, OAuth2ErrorCodes.INVALID_REDIRECT_URI),
    AUTH_FAILED_INVALID_USERNAME_PASSWORD(false, "10014", "invalid username or password", "invalid username or password"),
    ;
    private final boolean success;

    private final String code;

    private final String msg;

    private final String oauth2ErrorCode;

    AuthErrorResultCode(boolean success, String code, String msg, String oauth2ErrorCode) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.oauth2ErrorCode = oauth2ErrorCode;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }

    public String getOauth2ErrorCode() {
        return oauth2ErrorCode;
    }

    public static AuthErrorResultCode getAuthErrorResultCodeByOauth2ErrorCode(String oauth2ErrorCode){
        for (AuthErrorResultCode value : AuthErrorResultCode.values()) {
            if(value.getOauth2ErrorCode().equals(oauth2ErrorCode)){
                return value;
            }
        }
        return AUTH_FAILED_COMMON;
    }
}
