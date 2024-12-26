package com.mrd.pt.auth.authentication;

import com.mrd.pt.auth.entity.AuthPtUser;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Collection;
import java.util.Map;

/**
 * @author marundong
 */
@Getter
public class PtUserAuthenticationToken  extends AbstractAuthenticationToken {

    private final AuthPtUser authPtUser;


    private final RegisteredClient registeredClient;

    private final ClientAuthenticationMethod clientAuthenticationMethod;

    private final Map<String, Object> additionalParameters;

    public PtUserAuthenticationToken(Collection<? extends GrantedAuthority> authorities, AuthPtUser authPtUser, RegisteredClient registeredClient, ClientAuthenticationMethod clientAuthenticationMethod, Map<String, Object> additionalParameters) {
        super(authorities);
        this.authPtUser = authPtUser;
        this.registeredClient = registeredClient;
        this.clientAuthenticationMethod = clientAuthenticationMethod;
        this.additionalParameters = additionalParameters;
    }

    public PtUserAuthenticationToken(AuthPtUser authPtUser, OAuth2ClientAuthenticationToken clientAuthenticationToken) {
        super(clientAuthenticationToken.getAuthorities());
        this.authPtUser = authPtUser;
        this.registeredClient = clientAuthenticationToken.getRegisteredClient();
        this.clientAuthenticationMethod = clientAuthenticationToken.getClientAuthenticationMethod();
        this.additionalParameters = clientAuthenticationToken.getAdditionalParameters();
    }

    /**
     * The credentials that prove the principal is correct. This is usually a password,
     * but could be anything relevant to the <code>AuthenticationManager</code>. Callers
     * are expected to populate the credentials.
     *
     * @return the credentials that prove the identity of the <code>Principal</code>
     */
    @Override
    public Object getCredentials() {
        return authPtUser.getPassword();
    }

    /**
     * The identity of the principal being authenticated. In the case of an authentication
     * request with username and password, this would be the username. Callers are
     * expected to populate the principal for an authentication request.
     * <p>
     * The <tt>AuthenticationManager</tt> implementation will often return an
     * <tt>Authentication</tt> containing richer information as the principal for use by
     * the application. Many of the authentication providers will create a
     * {@code UserDetails} object as the principal.
     *
     * @return the <code>Principal</code> being authenticated or the authenticated
     * principal after authentication.
     */
    @Override
    public Object getPrincipal() {
        return authPtUser.getId();
    }
}
