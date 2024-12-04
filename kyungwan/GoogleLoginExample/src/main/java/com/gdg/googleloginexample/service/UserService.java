package com.gdg.googleloginexample.service;

import com.gdg.googleloginexample.domain.Role;
import com.gdg.googleloginexample.domain.User;
import com.gdg.googleloginexample.dto.TokenDto;
import com.gdg.googleloginexample.dto.UserInfoDto;
import com.gdg.googleloginexample.dto.UserLoginDto;
import com.gdg.googleloginexample.dto.UserSignUpDto;
import com.gdg.googleloginexample.jwt.TokenProvider;
import com.gdg.googleloginexample.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public UserInfoDto signUp(UserSignUpDto signUpDto) {
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new IllegalArgumentException("이메일이 이미 사용되고 있습니다.");
        }

        User user = userRepository.save(User.builder()
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .username(signUpDto.getUsername())
                .role(Role.USER)
                .build());

        return UserInfoDto.builder()
                .email(user.getEmail())
                .role(user.getRole().name())
                .username(user.getUsername())
                .build();
    }

    @Transactional
    public UserInfoDto signUpAdmin(UserSignUpDto signUpDto) {
        User user = userRepository.save(User.builder()
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .username(signUpDto.getUsername())
                .role(Role.ADMIN)
                .build());

        return UserInfoDto.builder()
                .email(user.getEmail())
                .role(user.getRole().name())
                .username(user.getUsername())
                .build();
    }

    @Transactional
    public TokenDto login(UserLoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));


        String accessToken = tokenProvider.createAccessToken(user);

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    @Transactional(readOnly = true)
    public List<UserInfoDto> getAllUsers() {
        List<User> allUsers = userRepository.findAll();

        List<UserInfoDto> userInfoDtos = allUsers.stream()
                .map(UserInfoDto::fromUser)
                .toList();

        return userInfoDtos;
    }



}
