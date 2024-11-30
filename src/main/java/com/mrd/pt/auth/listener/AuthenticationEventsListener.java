package com.mrd.pt.auth.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationEventsListener {
    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        // ...
        Object source = success.getSource();
        log.info("auth success:{}",source.getClass().getName());
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {
        AuthenticationException exception = failures.getException();
        if (exception instanceof ProviderNotFoundException) {
            // 不做处理
        } else {
            log.error("authentication Failure:",exception);
        }

    }

}