package com.coursera.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomAuthenticationProvider(CustomUserDetailsService customUserDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = (String) authentication.getCredentials();
        log.debug("User trying logging in with "+name);
        AuthenticatedUser authenticatedUser = customUserDetailsService.loadUserByUsername(name);
        if(!bCryptPasswordEncoder.matches(password,authenticatedUser.getPassword())){
            throw new BadCredentialsException("Sorry, that password isn't right!");
        }
        if(authenticatedUser.getUser().getLocked()){
            throw  new LockedException("Sorry, Your account is locked! please contact admin.");
        }
        return new UsernamePasswordAuthenticationToken(authenticatedUser,authentication.getCredentials(),authenticatedUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
