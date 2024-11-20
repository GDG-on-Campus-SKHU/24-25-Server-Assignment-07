package com.meyame.itemstore.controller.auth;

import com.meyame.itemstore.domain.auth.Role;
import com.meyame.itemstore.dto.request.auth.JwtRefreshTokenSignInReqDto;
import com.meyame.itemstore.dto.request.auth.SignUpReqDto;
import com.meyame.itemstore.dto.response.auth.JwtAccessTokenResDto;
import com.meyame.itemstore.dto.response.auth.JwtRefreshTokenResDto;
import com.meyame.itemstore.dto.response.auth.SignUpResDto;
import com.meyame.itemstore.jwt.JwtTokenProvider;
import com.meyame.itemstore.service.auth.GoogleLoginService;
import com.meyame.itemstore.service.auth.LocalLoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final GoogleLoginService googleLoginService;
    private final LocalLoginService localLoginService;
    private final JwtTokenProvider jwtTokenProvider;

    // 구글 로그인 콜백
    // 구글이 반환한 Authorization Code 를 파라미터에서 가져오고
    // 이걸 사용하여 구글의 토큰 엔드포인트로 구글 Access Token 요청
    // 구글 Access Token 받아오면 그걸로 사용자 확인하고 JWT 토큰 반환
    @GetMapping("/callback/google")
    public JwtAccessTokenResDto googleCallback(@RequestParam(name="code") String code) {
        String googleAccessToken = googleLoginService.getGoogleAccessToken(code);
        return loginOrSignUp(googleAccessToken);
    }

    private JwtAccessTokenResDto loginOrSignUp(String googleAccessToken) {
        return googleLoginService.loginOrSignUp(googleAccessToken);
    }

    // 자체 회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignUpResDto> signUp(@RequestBody SignUpReqDto signUpReqDto) {
        SignUpResDto res = localLoginService.signUp(signUpReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    // 자체 로그인 - 결과로 Access Token + Refresh Token 반환
    @PostMapping("/login/refresh")
    public ResponseEntity<JwtRefreshTokenResDto> refreshTokenSignIn(@RequestBody JwtRefreshTokenSignInReqDto jwtRefreshTokenSignInReqDto) {
        JwtRefreshTokenResDto token = localLoginService.signInWithRefreshToken(jwtRefreshTokenSignInReqDto, Role.USER);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    // access token 만료시 재발급 요청
    // Refresh token 을 헤더에 포함하여 요청
    @PostMapping("/token/reissue")
    public ResponseEntity<JwtAccessTokenResDto> reissueRefreshToken (HttpServletRequest req, HttpServletResponse res) {
        // 요청헤더에서 Refresh Token 추출
        String refreshToken = jwtTokenProvider.resolveToken(req);
        // Refresh Token 을 사용하여 Access Token 재발급
        JwtAccessTokenResDto accessTokenResDto = localLoginService.reissueAccessToken(refreshToken);
        jwtTokenProvider.setAccessTokenHeader(res,accessTokenResDto.accessToken());
        return ResponseEntity.status(HttpStatus.OK).body(accessTokenResDto);
    }
}
