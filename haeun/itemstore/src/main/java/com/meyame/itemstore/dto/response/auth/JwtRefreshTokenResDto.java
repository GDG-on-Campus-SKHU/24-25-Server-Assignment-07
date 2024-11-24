package com.meyame.itemstore.dto.response.auth;

import lombok.Builder;

@Builder
public record JwtRefreshTokenResDto(
        String accessToken,
        String refreshToken
){
}
