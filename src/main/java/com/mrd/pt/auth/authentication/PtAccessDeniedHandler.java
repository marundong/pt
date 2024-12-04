package com.mrd.pt.auth.authentication;

import cn.hutool.json.JSONUtil;
import com.mrd.pt.auth.code.AuthErrorResultCode;
import com.mrd.pt.common.response.JsonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * @author marundong
 */
public class PtAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * Handles an access denied failure.
     *
     * @param request               that resulted in an <code>AccessDeniedException</code>
     * @param response              so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     * @throws IOException      in the event of an IOException
     * @throws ServletException in the event of a ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
       // todo 异常定义
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        JsonResponse jsonResponse = new JsonResponse(AuthErrorResultCode.AUTH_FAILED_COMMON);
        jsonResponse.setMsg(accessDeniedException.getMessage());
        response.getWriter().print(JSONUtil.toJsonStr(jsonResponse));
    }
}
