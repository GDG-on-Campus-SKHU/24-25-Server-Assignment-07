package com.example.mydatecourse.service;

import com.example.mydatecourse.domain.User;
import com.example.mydatecourse.dto.TokenDto;
import com.example.mydatecourse.dto.UserInfoDto;
import com.example.mydatecourse.dto.UserLoginDto;
import com.example.mydatecourse.dto.UserSignUpDto;
import com.example.mydatecourse.jwt.TokenProvider;
import com.example.mydatecourse.repository.DateCourseRepository;
import com.example.mydatecourse.repository.UserRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public UserInfoDto signUp(UserSignUpDto userSignUpDto) {
        User user = userRepository.save(userSignUpDto.toEntity());
//        if(userRepository.existsByEmail((userSignUpDto.getEmail())))
//            throw new IllegalArgumentException("존재하는 회원입니다.");
        return UserInfoDto.from(user);
    }

    @Transactional
    public TokenDto login(UserLoginDto userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        String accessToken = tokenProvider.createAccessToken(user);
        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    @Transactional
    public UserInfoDto findPrincipal(Principal principal) {
        Long id = Long.parseLong(principal.getName());
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return UserInfoDto.from(user);
    }
}
