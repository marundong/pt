package com.mrd.pt.system.service;

import com.mrd.pt.system.entity.PtUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author marundong
 */
@Service
public class PtUserDetailService implements UserDetailsService {
    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return PtUser.builder().username(username).password("{noop}123456").build();
        return PtUser.builder().username(username).password("{MD4}{ESNAe3HCtzZYXRlkbq9/wlrZaf9adKxXJQq1x05K1uc=}db8b526c2ae4b3e1050df4d862b80c53").build();
    }
}
