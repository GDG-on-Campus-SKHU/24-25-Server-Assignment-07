package com.gdg.googleloginproject.service;

import com.gdg.googleloginproject.domain.LoginMethod;
import com.gdg.googleloginproject.domain.Role;
import com.gdg.googleloginproject.domain.User;
import com.gdg.googleloginproject.dto.response.TokenDto;
import com.gdg.googleloginproject.dto.request.UserLoginDto;
import com.gdg.googleloginproject.dto.request.UserSignUpDto;
import com.gdg.googleloginproject.dto.response.UserInfoDto;
import com.gdg.googleloginproject.exception.CustomException;
import com.gdg.googleloginproject.exception.ErrorMessage;
import com.gdg.googleloginproject.jwt.TokenProvider;
import com.gdg.googleloginproject.repository.UserRepository;
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
    public UserInfoDto saveUser(UserSignUpDto userSignUpDto) {

        if(isDuplicate(userSignUpDto.email())){
            throw new CustomException(ErrorMessage.USEREMAIL_IS_EXIST);
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

    @Transactional(readOnly = true)
    public UserInfoDto getUser(Principal principal) {
        Long userId = Long.parseLong(principal.getName());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_IS_NOT_EXIST));

        return UserInfoDto.from(user);
    }

    @Transactional
    public TokenDto loginUser(UserLoginDto userLoginDto) {
        User findUser = userRepository.findByEmail(userLoginDto.email())
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_IS_NOT_EXIST));

        if(!passwordEncoder.matches(userLoginDto.password(), findUser.getPassword())){
            throw new CustomException(ErrorMessage.PASSWORDS_DO_NOT_MATCH);
        }
        String accessToken = tokenProvider.createAccessToken(findUser);

        return TokenDto.from(accessToken);
    }
}
