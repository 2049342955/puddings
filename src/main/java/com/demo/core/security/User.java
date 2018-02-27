package com.demo.core.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

public class User implements UserDetails, CredentialsContainer {
    private String userId;
    private String password;
    private String username;
    private Set<String> authoritiesSet;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Map<String, Object> additionalAttributes;

    public User() {
        this.additionalAttributes = new ConcurrentHashMap();
    }

    @JsonCreator
    public User(@JsonProperty("userId") String userId, @JsonProperty("password") String password, @JsonProperty("username") String username, @JsonProperty("authoritiesSet") Set<String> authoritiesSet, @JsonProperty("accountNonExpired") boolean accountNonExpired, @JsonProperty("accountNonLocked") boolean accountNonLocked, @JsonProperty("credentialsNonExpired") boolean credentialsNonExpired, @JsonProperty("enabled") boolean enabled, @JsonProperty("additionalAttributes") Map<String, Object> additionalAttributes) {
        this.additionalAttributes = new ConcurrentHashMap();
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.authoritiesSet = authoritiesSet;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.additionalAttributes = additionalAttributes;
    }

    public User(String userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(userId, username, password, true, true, true, true, authorities);
    }

    public User(String userId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        this.additionalAttributes = new ConcurrentHashMap();
        if (!StringUtils.isEmpty(username) && password != null) {
            this.userId = userId;
            this.username = username;
            this.password = password;
            this.enabled = enabled;
            this.accountNonExpired = accountNonExpired;
            this.credentialsNonExpired = credentialsNonExpired;
            this.accountNonLocked = accountNonLocked;
            this.authoritiesSet = this.authoritiesToSet(authorities);
        } else {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
    }

    private Set<String> authoritiesToSet(Collection<? extends GrantedAuthority> authorities) {
        return (Set)authorities.stream().map((authority) -> {
            return authority.getAuthority();
        }).collect(Collectors.toSet());
    }

    @JsonIgnore
    public Set<GrantedAuthority> getAuthorities() {
        return (Set)this.authoritiesSet.stream().map((value) -> {
            return new SimpleGrantedAuthority(value);
        }).collect(Collectors.toSet());
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void eraseCredentials() {
        this.password = null;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<String> getAuthoritiesSet() {
        return this.authoritiesSet;
    }

    public boolean equals(Object rhs) {
        return rhs instanceof User ? this.username.equals(((User)rhs).username) : false;
    }

    public int hashCode() {
        return this.username.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("UserId: ").append(this.userId).append("; ");
        sb.append("Username: ").append(this.username).append("; ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("Enabled: ").append(this.enabled).append("; ");
        sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
        sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
        sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");
        if (!this.authoritiesSet.isEmpty()) {
            sb.append("Granted Authorities: ");
            boolean first = true;
            Iterator var3 = this.authoritiesSet.iterator();

            while(var3.hasNext()) {
                String auth = (String)var3.next();
                if (!first) {
                    sb.append(",");
                }

                first = false;
                sb.append(auth);
            }
        } else {
            sb.append("Not granted any authorities");
        }

        return sb.toString();
    }

    public Map<String, Object> getAdditionalAttributes() {
        return this.additionalAttributes;
    }

    public void setAdditionalAttributes(Map<String, Object> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    public void addAttribute(String key, Object attr) {
        if (attr != null) {
            this.additionalAttributes.put(key, attr);
        }

    }

    public void addAttributes(Map<String, Object> maps) {
        if (maps != null && !maps.isEmpty()) {
            this.additionalAttributes.putAll(maps);
        }

    }

    public Object getAttribute(String key) {
        return this.additionalAttributes.get(key);
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        private static final long serialVersionUID = 410L;

        private AuthorityComparator() {
        }

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            if (g2.getAuthority() == null) {
                return -1;
            } else {
                return g1.getAuthority() == null ? 1 : g1.getAuthority().compareTo(g2.getAuthority());
            }
        }
    }
}
