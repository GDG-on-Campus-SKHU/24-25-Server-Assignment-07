package com.example.assignment07.dto.UserDto;

import com.example.assignment07.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserLoginRequestDto {
    private String email;
    private String password;

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }

}
