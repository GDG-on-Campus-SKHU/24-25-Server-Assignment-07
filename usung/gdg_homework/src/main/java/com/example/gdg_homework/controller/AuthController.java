package com.example.gdg_homework.controller;

import com.example.gdg_homework.dto.TokenDto;
import com.example.gdg_homework.service.GoogleLoginService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api")
public class AuthController {

    private final GoogleLoginService googleLoginService;

    @GetMapping("callback/google")
    public TokenDto googleCallback(@RequestParam(name = "code") String code) {
        String googleAccessToken = googleLoginService.getGoogleAccessToken(code);
        return loginOrSignup(googleAccessToken);
    }

    private TokenDto loginOrSignup(String googleAccessToken) {
        return googleLoginService.loginOrSignUp(googleAccessToken);
    }
}
