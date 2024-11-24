package com.example.assignment07.controller;

import com.example.assignment07.domain.user.User;
import com.example.assignment07.dto.UserDto.TokenDto;
import com.example.assignment07.dto.UserDto.UserInfoResponseDto;
import com.example.assignment07.dto.UserDto.UserLoginRequestDto;
import com.example.assignment07.dto.UserDto.UserSignUpRequestDto;
import com.example.assignment07.service.GoogleLoginService;
import com.example.assignment07.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final GoogleLoginService googleLoginService;


    // 회원가입
    @PostMapping
    public ResponseEntity<UserInfoResponseDto> signUp(@RequestBody UserSignUpRequestDto userSignUpRequestDto) {
        UserInfoResponseDto response = userService.signUp(userSignUpRequestDto);
        return ResponseEntity.ok().body(response);

    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        TokenDto token = userService.login(userLoginRequestDto);
        return ResponseEntity.ok().body(token);
    }

    // 사용자 정보 조회
    @GetMapping("/me")
    public ResponseEntity<UserInfoResponseDto> getUserInfo(Principal principal) {
        UserInfoResponseDto userInfo = userService.findByPrincipal(principal);
        return ResponseEntity.ok(userInfo);
    }

    // 전체 사용자 조회
    @GetMapping("/list")
    public ResponseEntity<List<UserInfoResponseDto>> getAllUsers(Principal principal) {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/test")
    public User getUser(Principal principal) {return googleLoginService.test(principal);
    }

}
