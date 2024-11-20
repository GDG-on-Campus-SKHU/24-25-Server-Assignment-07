package com.meyame.itemstore.service.auth;

import com.meyame.itemstore.domain.auth.AuthProvider;
import com.meyame.itemstore.domain.auth.RefreshToken;
import com.meyame.itemstore.domain.auth.Role;
import com.meyame.itemstore.domain.auth.User;
import com.meyame.itemstore.dto.request.auth.JwtRefreshTokenSignInReqDto;
import com.meyame.itemstore.dto.request.auth.SignUpReqDto;
import com.meyame.itemstore.dto.request.user.UserUpdateReqDto;
import com.meyame.itemstore.dto.response.auth.JwtAccessTokenResDto;
import com.meyame.itemstore.dto.response.auth.JwtRefreshTokenResDto;
import com.meyame.itemstore.dto.response.auth.SignUpResDto;
import com.meyame.itemstore.dto.response.user.UserInfoResDto;
import com.meyame.itemstore.dto.response.user.UserUpdateResDto;
import com.meyame.itemstore.exception.CustomException;
import com.meyame.itemstore.jwt.JwtTokenProvider;
import com.meyame.itemstore.repository.auth.RefreshTokenRepository;
import com.meyame.itemstore.repository.auth.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.meyame.itemstore.exception.ErrorCode.*;

// 자체 로그인
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LocalLoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    // 회원가입
    @Transactional
    public SignUpResDto signUp(SignUpReqDto signUpReqDto) {
        // 이메일 중복 체크
        String email = signUpReqDto.email();
        if (userRepository.existsByEmail(email)) {
            throw new CustomException(DUPLICATE_EMAIL);
        }
        // 패스워드 암호화
        String password = signUpReqDto.password();
        String encodedPassword = passwordEncoder.encode(password);

        User user = userRepository.save(User.builder()
                .email(signUpReqDto.email())
                .password(encodedPassword)
                .name(signUpReqDto.name())
                .role(Role.USER) // 기본 권한은 USER
                .authProvider(AuthProvider.LOCAL) // 항상 LOCAL 로 설정
                .build());

        return SignUpResDto.from(user);
    }

    // refresh token 을 이용한 로그인
    @Transactional
    public JwtRefreshTokenResDto signInWithRefreshToken(JwtRefreshTokenSignInReqDto refreshTokenSignInReqDto) {
        User user = userRepository.findByEmail(refreshTokenSignInReqDto.email())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        // 비밀번호 검증
        if(!passwordEncoder.matches(refreshTokenSignInReqDto.password(), user.getPassword())) {
            throw new CustomException(INCORRECT_PASSWORD);
        }
        // access token + refresh token 생성
        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);

        // 기존 refresh token 삭제 + 새롭게 생성 후 저장
        refreshTokenRepository.deleteByUserId(user.getId());
        refreshTokenRepository.save(RefreshToken.builder()
                .userId(user.getId()) // User 엔티티의 id를 userId로 설정
                .refreshToken(refreshToken)
                .expiration(LocalDateTime.now().plusSeconds(jwtTokenProvider.getRefreshTokenValidityTime()))
                .build());

        return new JwtRefreshTokenResDto(accessToken, refreshToken);
    }

    // access token 재발급
    public JwtAccessTokenResDto reissueAccessToken(String refreshToken) {
        // refresh token 의 유효성 검사
        if(!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw new CustomException(INVALID_REFRESH_TOKEN);
        }
        // db에 저장된 refresh token 과 요청에서 가져온 refresh token 이 일치하는지 확인
        Long userId = jwtTokenProvider.getUserIdFromRefreshToken(refreshToken);
        RefreshToken storedRefreshToken = refreshTokenRepository.findByUserId(userId);

        if(!storedRefreshToken.getRefreshToken().equals(refreshToken)) {
            throw new CustomException(INCORRECT_REFRESH_TOKEN);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        String newAccessToken = jwtTokenProvider.createAccessToken(user);

        return JwtAccessTokenResDto.from(newAccessToken);
    }

    // 자체 로그인한 사용자 정보 조회
    @Transactional(readOnly = true)
    public UserInfoResDto getUserInfo(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        return UserInfoResDto.from(user);
    }

    // 자체 로그인한 사용자 정보 수정
    @Transactional
    public UserUpdateResDto updateUserInfo(UserUpdateReqDto userUpdateReqDto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        user.update(userUpdateReqDto.email(),userUpdateReqDto.password(),userUpdateReqDto.name());

        return UserUpdateResDto.from(user);
    }

    // 자체 로그인한 사용자 정보 삭제
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
