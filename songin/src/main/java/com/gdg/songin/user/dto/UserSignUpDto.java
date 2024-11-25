package com.gdg.songin.user.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserSignUpDto {
    private String email;
    private String password;
    private String name;

}
