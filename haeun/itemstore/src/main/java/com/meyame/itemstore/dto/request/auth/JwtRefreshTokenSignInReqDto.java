package com.meyame.itemstore.dto.request.auth;

public record JwtRefreshTokenSignInReqDto(
        String email,
        String password
){
}
