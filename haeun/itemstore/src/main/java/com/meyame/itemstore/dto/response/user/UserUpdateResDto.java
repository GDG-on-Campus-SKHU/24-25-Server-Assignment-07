package com.meyame.itemstore.dto.response.user;

import com.meyame.itemstore.domain.auth.Role;
import com.meyame.itemstore.domain.auth.User;
import lombok.Builder;

@Builder
public record UserUpdateResDto(
        Long id,
        String email,
        String password,
        String name,
        Role role
){
    public static UserUpdateResDto from(User user) {
        return UserUpdateResDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }
}
