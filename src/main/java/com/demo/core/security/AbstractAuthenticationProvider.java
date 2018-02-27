package com.demo.core.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class AbstractAuthenticationProvider implements AuthenticationProvider {
    private static Logger logger = LoggerFactory.getLogger(AbstractAuthenticationProvider.class);
    private Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
    private ReflectionSaltSource saltSource = new ReflectionSaltSource();
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public AbstractAuthenticationProvider() {
        this.saltSource.setUserPropertyToUse("getUsername");
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = (String)authentication.getCredentials();
        UserDetails userDetails = this.loadUserByUsername(userName);
        if (userDetails == null) {
            logger.debug("Authentication failed: user doesn't exists!");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            Object salt = this.saltSource.getSalt(userDetails);
            if (!this.passwordEncoder.isPasswordValid(userDetails.getPassword(), password, salt)) {
                logger.debug("Authentication failed: password does not match stored value");
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            } else if (userDetails.isAccountNonExpired() && userDetails.isAccountNonLocked() && userDetails.isCredentialsNonExpired() && userDetails.isEnabled()) {
                return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
            } else {
                logger.debug("Authentication failed: user cannot login!");
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        }
    }

    public boolean supports(Class<?> authentication) {
        return true;
    }

    protected abstract UserDetails loadUserByUsername(String var1);
}
