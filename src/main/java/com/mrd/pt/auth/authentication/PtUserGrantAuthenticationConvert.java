package com.mrd.pt.auth.authentication;

import com.mrd.pt.auth.code.AuthErrorResultCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class PtUserGrantAuthenticationConvert implements AuthenticationConverter {
    @Override
    public Authentication convert(HttpServletRequest request) {
        MultiValueMap<String, String> formParameters = PtOAuth2EndpointUtils.getFormParameters(request);
        // grant_type (REQUIRED)
        String grantType = formParameters.getFirst(OAuth2ParameterNames.GRANT_TYPE);
        if (!PtOauth2Constant.GRANT_TYPE_PT_USER.getValue().equals(grantType)) {
            return null;
        }
        String username = formParameters.getFirst(OAuth2ParameterNames.USERNAME);
        String password = formParameters.getFirst(OAuth2ParameterNames.PASSWORD);

        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new OAuth2AuthenticationException(AuthErrorResultCode.AUTH_FAILED_INVALID_USERNAME_PASSWORD.getOauth2ErrorCode());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> additionalParameters = new HashMap<>();
        formParameters.forEach((key, value) -> {
            if (!key.equals(OAuth2ParameterNames.GRANT_TYPE) && !key.equals(OAuth2ParameterNames.CLIENT_ID)
                    && !key.equals(OAuth2ParameterNames.CODE) && !key.equals(OAuth2ParameterNames.REDIRECT_URI)) {
                additionalParameters.put(key, (value.size() == 1) ? value.get(0) : value.toArray(new String[0]));
            }
        });
        return new PtUserGrantAuthenticationToken(PtOauth2Constant.GRANT_TYPE_PT_USER,authentication,additionalParameters);
    }
}
