package com.meyame.itemstore.dto.response.user;

import com.meyame.itemstore.domain.auth.Role;
import com.meyame.itemstore.domain.auth.User;
import lombok.Builder;

@Builder
public record UserInfoResDto(
        String email,
        String name,
        Role role
){
    public static UserInfoResDto from(User user) {
        return UserInfoResDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }
}
