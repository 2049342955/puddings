package com.demo.core.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContext {
    public SecurityContext() {
    }

    public static User getCurrentUser() {
        return (User)SecurityContextHolder.getContext().getAuthentication().getDetails();
    }

    public Collection<GrantedAuthority> getRoles() {
        return getCurrentUser().getAuthorities();
    }
}
