package com.gdg.songin.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserLoginDto {
    public final String email;
    public final String pwd;
}
