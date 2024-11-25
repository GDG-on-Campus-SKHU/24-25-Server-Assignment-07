package com.gdg.oauthlogin.service;

import com.gdg.oauthlogin.domain.LoginType;
import com.gdg.oauthlogin.domain.Role;
import com.gdg.oauthlogin.domain.User;
import com.gdg.oauthlogin.dto.TokenDto;
import com.gdg.oauthlogin.dto.UserInfo;
import com.gdg.oauthlogin.dto.UserSignUpDto;
import com.gdg.oauthlogin.jwt.TokenProvider;
import com.gdg.oauthlogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class NormalLoginService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenDto signUp(UserSignUpDto userSignUpDto) {
        if(userRepository.findByEmail(userSignUpDto.getEmail()).isPresent())
            throw new RuntimeException("이미 가입된 이메일입니다.");

        User user = userRepository.save(User.builder()
                .email(userSignUpDto.getEmail())
                .password(passwordEncoder.encode(userSignUpDto.getPassword()))
                .name(userSignUpDto.getName())
                .role(Role.USER)
                .loginType(LoginType.EMAIL)
                .build());

        String accessToken = tokenProvider.createAccessToken(user);

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    @Transactional(readOnly = true)
    public UserInfo findByPrincipal(Principal principal) {
        Long id = Long.parseLong(principal.getName());

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        return UserInfo.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .role(user.getRole())
                .loginType(user.getLoginType())
                .build();
    }

    @Transactional(readOnly = true)
    public UserInfo getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        return UserInfo.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .role(user.getRole())
                .loginType(user.getLoginType())
                .build();
    }

    @Transactional
    public void deleteUser(Principal principal) {
        Long userId = Long.parseLong(principal.getName());

        userRepository.deleteById(userId);
    }
}
