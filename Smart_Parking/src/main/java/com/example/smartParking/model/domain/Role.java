package com.example.smartParking.model.domain;

import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {
    MOBILE_USER,USER, MANAGER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
