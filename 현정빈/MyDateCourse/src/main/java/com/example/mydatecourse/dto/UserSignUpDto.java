package com.example.mydatecourse.dto;

import com.example.mydatecourse.domain.Role;
import com.example.mydatecourse.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserSignUpDto {
    private String email;
    private String name;
    private String password;

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .role(Role.USER)
                .build();
    }
}
