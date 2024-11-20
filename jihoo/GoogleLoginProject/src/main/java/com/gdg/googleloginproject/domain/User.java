package com.gdg.googleloginproject.domain;

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

    @Column(nullable = true)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = true)
    private String profileUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private LoginMethod loginMethod; //자체 로그인인지, 구글로그인인지 판별

    @Builder
    public User(Long id, String email, String password, String username, String profileUrl, Role role, LoginMethod loginMethod) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.profileUrl = profileUrl;
        this.role = role;
        this.loginMethod = loginMethod;
    }
}
