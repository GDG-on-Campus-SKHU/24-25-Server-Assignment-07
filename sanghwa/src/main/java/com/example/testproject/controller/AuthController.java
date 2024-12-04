package com.example.testproject.controller;

import com.example.testproject.dto.TokenDto;
import com.example.testproject.service.GoogleLoginService;
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

    // code를 받고 getGoogleAccessToken을 이용하여 AccessToken을 받고, AccessToke을 이용하여 loginOrSignup을 진행한다.
    // 처음에 URL로 진입할 때, callback/google호출한다.
    @GetMapping("callback/google")
    public TokenDto googleCallback(@RequestParam(name = "code") String code) {
        String googleAccessToken = googleLoginService.getGoogleAccessToken(code);
        return loginOrSignup(googleAccessToken);
    }

    @GetMapping("callback/google/admin")
    public TokenDto googleCallbackAdmin(@RequestParam(name = "code") String code) {
        String googleAccessToken = googleLoginService.getGoogleAccessToken(code);
        return loginOrSignup(googleAccessToken);
    }

    //JWT반환
    private TokenDto loginOrSignup(String googleAccessToken) {
        return googleLoginService.loginOrSignUp(googleAccessToken);
    }
}