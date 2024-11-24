package com.gdg.googleloginexample.dto;

import com.gdg.googleloginexample.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoDto {
    private String username;
    private String email;
    private String role;

    public static UserInfoDto fromUser(User user) {
        return UserInfoDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
