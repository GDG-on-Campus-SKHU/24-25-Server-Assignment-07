package com.meyame.itemstore.domain.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meyame.itemstore.domain.UserItem;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true) // 구글 사용자는 비밀번호 필요 없음
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true) // 구글 사용자만
    private String pictureUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider; // GOOGLE || LOCAL

    @JsonIgnore
    @OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserItem> userItemList = new ArrayList<>(); // user가 보유한 UserItem 목록

    @Builder
    public User(Long id, String email, String name, String password, String pictureUrl, Role role, AuthProvider authProvider) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.pictureUrl = pictureUrl;
        this.role = role;
        this.authProvider = authProvider;
    }

    public void update(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
