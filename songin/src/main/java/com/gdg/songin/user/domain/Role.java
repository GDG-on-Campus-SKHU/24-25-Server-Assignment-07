package com.gdg.songin.user.domain;

public enum Role {
    USER("ROEL_USER"),
    ADMIN("ROEL_ADMIN");

    private final String key;

    private Role(String key) {
        this.key = key;
    }
}

