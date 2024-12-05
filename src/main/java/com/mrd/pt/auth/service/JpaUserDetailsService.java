package com.mrd.pt.auth.service;

import com.mrd.pt.auth.entity.AuthPtUser;
import com.mrd.pt.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthPtUser user = userRepository
                .findByUsername(username)
                .map(AuthPtUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User name not found: " + username));
        return user;

    }
}