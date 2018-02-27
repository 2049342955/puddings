package com.demo.core.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

public class SimpleGrantedAuthority implements GrantedAuthority, Comparable<SimpleGrantedAuthority>, Serializable {
    private String role;

    @JsonCreator
    public SimpleGrantedAuthority(@JsonProperty("role") String role) {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
    }

    @JsonIgnore
    public String getAuthority() {
        return this.role;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof SimpleGrantedAuthority ? this.role.equals(((SimpleGrantedAuthority)obj).role) : false;
        }
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }

    public int compareTo(SimpleGrantedAuthority o) {
        if (o.getAuthority() == null) {
            return -1;
        } else {
            return this.getAuthority() == null ? 1 : this.getAuthority().compareTo(o.getAuthority());
        }
    }
}
