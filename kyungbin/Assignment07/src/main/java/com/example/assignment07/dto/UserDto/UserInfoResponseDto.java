package com.example.assignment07.dto.UserDto;

import com.example.assignment07.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoResponseDto {
    private String email;
    private String name;
    private String role;

    public static UserInfoResponseDto from(User user) {
        return UserInfoResponseDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().name())
                .build();
    }
}
