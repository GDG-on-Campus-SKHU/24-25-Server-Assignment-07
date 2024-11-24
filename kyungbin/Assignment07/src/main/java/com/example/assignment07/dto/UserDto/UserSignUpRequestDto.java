package com.example.assignment07.dto.UserDto;

import com.example.assignment07.domain.user.Role;
import com.example.assignment07.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserSignUpRequestDto {
    private String email;
    private String name;
    private String password;

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .password(password)
                .role(Role.USER)
                .build();
    }
}
