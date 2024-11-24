package com.example.testproject.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String profileUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(Long id, String email, String name, String profileUrl, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profileUrl = profileUrl;
        this.role = role;
    }
}