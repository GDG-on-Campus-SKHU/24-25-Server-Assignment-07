package com.meyame.itemstore.dto.request.user;

public record UserUpdateReqDto(
        String email,
        String password,
        String name
){
}
