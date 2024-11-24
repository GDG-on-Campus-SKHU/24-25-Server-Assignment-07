package com.gdg.seun.controller;

import com.gdg.seun.dto.CustomUserInfoDto;
import com.gdg.seun.dto.LoginRequest;
import com.gdg.seun.dto.SignUpRequest;
import com.gdg.seun.dto.TokenDto;
import com.gdg.seun.domain.User;
import com.gdg.seun.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public CustomUserInfoDto signUp(@RequestBody SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest);
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginRequest loginRequest) {
        return authService.loginWithCredentials(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @PostMapping("/register")
    public User register(@RequestBody String email, @RequestBody String password, @RequestBody String name) {
        return authService.register(email, password, name);
    }

    @GetMapping("/callback/google")
    public TokenDto googleCallback(@RequestParam(name = "code") String code) {
        String googleAccessToken = authService.getGoogleAccessToken(code);
        return authService.loginOrSignUpWithGoogle(googleAccessToken);
    }
}
