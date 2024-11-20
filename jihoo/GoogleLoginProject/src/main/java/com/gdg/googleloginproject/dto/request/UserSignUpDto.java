package com.gdg.googleloginproject.dto.request;

public record UserSignUpDto(
        String email,
        String password,
        String username
) {
}
