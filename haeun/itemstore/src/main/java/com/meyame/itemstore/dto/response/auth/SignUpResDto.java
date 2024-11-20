package com.meyame.itemstore.dto.response.auth;

import com.meyame.itemstore.domain.auth.AuthProvider;
import com.meyame.itemstore.domain.auth.Role;
import com.meyame.itemstore.domain.auth.User;
import lombok.Builder;

@Builder
public record SignUpResDto(
        Long id,
        String name,
        String email,
        Role role,
        AuthProvider authProvider
){
    public static SignUpResDto from(User user) {
        return SignUpResDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .authProvider(user.getAuthProvider())
                .build();
    }
}
