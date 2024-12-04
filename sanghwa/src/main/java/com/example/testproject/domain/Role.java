package com.example.testproject.domain;

public enum Role {

    USER("ROLE_USER"),
    ADMIN("ROEL_ADMIN");

    private final String key;

    Role(String key) {
        this.key = key;
    }
}