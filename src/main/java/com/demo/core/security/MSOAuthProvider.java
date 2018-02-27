package com.demo.core.security;

import org.springframework.security.core.userdetails.UserDetails;

public abstract class MSOAuthProvider extends AbstractAuthenticationProvider {
    public MSOAuthProvider() {
    }

    public abstract UserDetails loadUserByUsername(String var1);
}
