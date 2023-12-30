package com.coursera.security;

import com.coursera.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class AuthenticatedUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public AuthenticatedUser(User user) {
        super(user.getUserName(), user.getPassword(), getAuthorities(user));
        this.user = user;
    }

    public static Collection<GrantedAuthority> getAuthorities(User user) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        try {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()));
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        return grantedAuthorities;
    }


}
