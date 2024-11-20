package com.meyame.itemstore.controller.auth;

import com.meyame.itemstore.domain.auth.Role;
import com.meyame.itemstore.dto.request.auth.JwtRefreshTokenSignInReqDto;
import com.meyame.itemstore.dto.request.auth.SignUpReqDto;
import com.meyame.itemstore.dto.response.auth.JwtRefreshTokenResDto;
import com.meyame.itemstore.dto.response.auth.SignUpResDto;
import com.meyame.itemstore.service.auth.LocalLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/auth")
public class AdminAuthController {

    // 관리자는 자체 회원가입/로그인 기능만 제공
    private final LocalLoginService localLoginService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResDto> adminSignUp(@RequestBody SignUpReqDto signUpReqDto) {
        SignUpResDto res = localLoginService.adminSignUp(signUpReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/login/refresh")
    public ResponseEntity<JwtRefreshTokenResDto> refreshTokenSignIn(@RequestBody JwtRefreshTokenSignInReqDto jwtRefreshTokenSignInReqDto) {
        JwtRefreshTokenResDto token = localLoginService.signInWithRefreshToken(jwtRefreshTokenSignInReqDto, Role.ADMIN);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
