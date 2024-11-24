package com.gdg.googleloginproject.dto.response;

import com.gdg.googleloginproject.domain.User;
import lombok.Builder;

@Builder
public record UserInfoDto(
        Long id,
        String username,
        String email,
        String password,
        String role,
        String loginMethod
) {

    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole().name())
                .loginMethod(user.getLoginMethod().name())
                .build();
    }
}
