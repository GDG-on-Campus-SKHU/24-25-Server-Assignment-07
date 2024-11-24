package com.example.assignment07.domain.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String profileUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = true)
    private Role role;

    @Builder
    public User(Long id, String email, String name, String password, String profileUrl, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.profileUrl = profileUrl;
        this.role = role;
    }
}
