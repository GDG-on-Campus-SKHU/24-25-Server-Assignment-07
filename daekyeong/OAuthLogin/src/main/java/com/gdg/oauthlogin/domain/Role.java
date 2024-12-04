package com.gdg.oauthlogin.domain;

public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String key;
    Role(String key) {
        this.key = key;
    }
}