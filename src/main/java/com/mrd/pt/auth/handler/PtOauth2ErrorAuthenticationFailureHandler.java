package com.mrd.pt.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrd.pt.auth.code.AuthErrorResultCode;
import com.mrd.pt.common.response.JsonResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author marundong
 */
@Slf4j
@Component
public class PtOauth2ErrorAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Resource
    private ObjectMapper objectMapper;

    /**
     * Called when an authentication attempt fails.
     *
     * @param request   the request during which the authentication attempt occurred.
     * @param response  the response.
     * @param exception the exception which was thrown to reject the authentication
     *                  request.
     * errorCode类型参见OAuth2ErrorCodes {@link org.springframework.security.oauth2.core.OAuth2ErrorCodes}
     *
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof OAuth2AuthenticationException) {
            // 处理oauth2认证失败
            OAuth2Error error = ((OAuth2AuthenticationException) exception).getError();
            log.error("oauth2 authentication failure: {}", objectMapper.writeValueAsString(error));
            log.error("oauth2 authentication failure detail:", exception);
            String errorCode = error.getErrorCode();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            AuthErrorResultCode authErrorResultCodeByOauth2ErrorCode = AuthErrorResultCode.getAuthErrorResultCodeByOauth2ErrorCode(errorCode);
            JsonResponse jsonResponse = new JsonResponse(authErrorResultCodeByOauth2ErrorCode);
            if(authErrorResultCodeByOauth2ErrorCode.equals(AuthErrorResultCode.AUTH_FAILED_COMMON)){
                jsonResponse.setMsg(errorCode);
            }
            response.getWriter().print(objectMapper.writeValueAsString(jsonResponse));
        } else {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            AuthErrorResultCode authErrorResultCodeByOauth2ErrorCode = AuthErrorResultCode.AUTH_FAILED_COMMON;
            JsonResponse jsonResponse = new JsonResponse(authErrorResultCodeByOauth2ErrorCode);
            jsonResponse.setMsg(exception.getMessage());
            response.getWriter().print(objectMapper.writeValueAsString(jsonResponse));
        }

    }
}
