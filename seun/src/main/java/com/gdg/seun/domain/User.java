package com.gdg.seun.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String profileUrl;

    @Column(nullable = true)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private LoginMethod loginMethod;


    @Builder
    public User(String email, String name, String profileUrl, String password, Role role, LoginMethod loginMethod) {
        this.email = email;
        this.name = name;
        this.profileUrl = profileUrl;
        this.password = password;
        this.role = role;
        this.loginMethod = loginMethod;
    }
}
