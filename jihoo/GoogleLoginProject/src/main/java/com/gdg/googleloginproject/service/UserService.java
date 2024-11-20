package com.gdg.googleloginproject.service;

import com.gdg.googleloginproject.domain.LoginMethod;
import com.gdg.googleloginproject.domain.Role;
import com.gdg.googleloginproject.domain.User;
import com.gdg.googleloginproject.dto.response.TokenDto;
import com.gdg.googleloginproject.dto.request.UserLoginDto;
import com.gdg.googleloginproject.dto.request.UserSignUpDto;
import com.gdg.googleloginproject.dto.response.UserInfoDto;
import com.gdg.googleloginproject.jwt.TokenProvider;
import com.gdg.googleloginproject.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public UserInfoDto saveUser(UserSignUpDto userSignUpDto) {

        if(isDuplicate(userSignUpDto.email())){
            throw new RuntimeException("중복된 아이디 입니다.");
        }

        User user = userRepository.save(User.builder()
                .email(userSignUpDto.email())
                .username(userSignUpDto.username())
                .password(passwordEncoder.encode(userSignUpDto.password()))
                .role(Role.USER)
                .loginMethod(LoginMethod.LOCAL) //로컬 회원가입
                .build());

        return UserInfoDto.from(user);
    }

    //이메일 중복검사 메서드
    private boolean isDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public TokenDto loginUser(UserLoginDto userLoginDto) {
        User findUser = userRepository.findByEmail(userLoginDto.email())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if(!passwordEncoder.matches(userLoginDto.password(), findUser.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        String accessToken = tokenProvider.createAccessToken(findUser);

        return TokenDto.from(accessToken);
    }
}
