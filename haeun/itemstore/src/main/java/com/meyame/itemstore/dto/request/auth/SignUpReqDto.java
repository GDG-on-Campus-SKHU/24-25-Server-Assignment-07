package com.meyame.itemstore.dto.request.auth;

public record SignUpReqDto(
        String email,
        String password,
        String name
){
}
