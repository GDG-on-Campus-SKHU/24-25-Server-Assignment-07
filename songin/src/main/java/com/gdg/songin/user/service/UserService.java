package com.gdg.songin.user.service;



import com.gdg.songin.jwt.TokenProvider;
import com.gdg.songin.user.domain.Role;
import com.gdg.songin.user.domain.User;
import com.gdg.songin.user.dto.TokenDto;
import com.gdg.songin.user.dto.UserInfoDto;
import com.gdg.songin.user.dto.UserLoginDto;
import com.gdg.songin.user.dto.UserSignUpDto;
import com.gdg.songin.user.repository.UserRepository;
import lombok.AccessLevel;
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
    public UserInfoDto saveUser(UserSignUpDto userSignDto) {
        boolean exists = userRepository.findByEmail(userSignDto.getEmail()).isPresent();
        if (exists) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }
        // 사용자 정보를 저장
        User user = userRepository.save(User.builder()
                .email(userSignDto.getEmail())
                .password(passwordEncoder.encode(userSignDto.getPassword())) // 비밀번호 암호화
                .name(userSignDto.getName())
                .role(Role.USER) // 기본 역할 설정
                .build());
        userRepository.save(user);

        return UserInfoDto.from(user);
    }

    @Transactional
    public TokenDto loginUser(UserLoginDto userLoginDto) {
        User findUser = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if(!passwordEncoder.matches(userLoginDto.getPwd(), findUser.getPassword())){
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        String accessToken = tokenProvider.createAccessToken(findUser);
        return TokenDto.from(accessToken);
    }

    @Transactional(readOnly = true)
    public UserInfoDto getMyInfo(Principal principal) {
        Long userId = Long.parseLong(principal.getName());

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return UserInfoDto.from(findUser);
    }
}
