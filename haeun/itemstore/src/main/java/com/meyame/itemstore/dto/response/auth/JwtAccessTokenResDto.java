package com.meyame.itemstore.dto.response.auth;

import lombok.Builder;

@Builder
public record JwtAccessTokenResDto(
       String accessToken
){
    public static JwtAccessTokenResDto from(String accessToken) {
        return JwtAccessTokenResDto.builder()
                .accessToken(accessToken)
                .build();
    }
}
