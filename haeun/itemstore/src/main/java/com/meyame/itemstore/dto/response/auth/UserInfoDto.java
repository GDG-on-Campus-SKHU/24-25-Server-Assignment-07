package com.meyame.itemstore.dto.response.auth;

import com.meyame.itemstore.domain.auth.User;
import lombok.Builder;

@Builder
public record UserInfoDto (
        String email,
        String name,
        String pictureUrl
){
    public static UserInfoDto from(User user){
        return UserInfoDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .pictureUrl(user.getPictureUrl())
                .build();
    }
}
