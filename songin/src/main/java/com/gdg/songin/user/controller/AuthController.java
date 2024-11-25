package com.gdg.songin.user.controller;

import com.gdg.songin.user.dto.TokenDto;
import com.gdg.songin.user.service.GoogleLoginService;
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
    } //구글 토큰으로 엑세스 토큰 발급

    private TokenDto loginOrSignup(String googleAccessToken) {
        return googleLoginService.loginOrSignUp(googleAccessToken);
    }
}