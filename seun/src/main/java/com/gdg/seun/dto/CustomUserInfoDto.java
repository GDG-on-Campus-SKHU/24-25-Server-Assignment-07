package com.gdg.seun.dto;

import com.gdg.seun.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CustomUserInfoDto {
    private String email;
    private String name;
    private String role;
    private String loginMethod;

    public static CustomUserInfoDto from(User user) {
        return CustomUserInfoDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().name())
                .loginMethod(user.getLoginMethod().toString())
                .build();
    }
}
