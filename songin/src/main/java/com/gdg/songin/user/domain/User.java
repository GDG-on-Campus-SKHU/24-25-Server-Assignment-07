package com.gdg.songin.user.domain;


import com.gdg.songin.orderHistory.domain.OrderHistory;
import com.gdg.songin.user.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    private String name;

    @Column
    private String phoneNumber;

    @Column
    private String address;

    @Column
    private String profileUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderHistory> orders = new ArrayList<>();

    @Builder
    public User(String email, String password, String name, String phoneNumber, String address, String profileUrl, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.profileUrl = profileUrl;
        this.role = role;
    }

    public void update(UserRequestDto userRequestDto) {
        this.password = userRequestDto.getPassword();
        this.name = userRequestDto.getNickName();
        this.email = userRequestDto.getEmail();
    }

}
