package com.mrd.pt.auth.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationEvents {
    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        // ...
        log.error("auth failure:{}", success.getAuthentication());
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {
        // ...
        AuthenticationException exception = failures.getException();
        log.error("auth failure:{}", exception.getMessage());
        log.warn("auth failure error exception info:", exception);
    }

//    @EventListener
//    public void onFailureBadCredentialsEvent(AuthenticationFailureBadCredentialsEvent failures) {
//		// ...
//
//        throw new BizException(SysResultCode.AUTH_FAILED);
//    }

}