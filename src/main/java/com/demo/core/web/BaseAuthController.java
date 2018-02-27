package com.demo.core.web;

import java.util.Set;

import com.demo.core.security.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseAuthController extends BaseController {
    public BaseAuthController() {
    }

    public User getUser() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    public String getUserId() {
        return this.getUser().getUserId();
    }

    public Set<GrantedAuthority> getAuthority() {
        return this.getUser().getAuthorities();
    }
}
