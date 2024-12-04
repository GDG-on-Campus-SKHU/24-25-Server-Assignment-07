package com.example.mydatecourse.controller;

import com.example.mydatecourse.domain.User;
import com.example.mydatecourse.dto.TokenDto;
import com.example.mydatecourse.dto.UserInfoDto;
import com.example.mydatecourse.dto.UserLoginDto;
import com.example.mydatecourse.dto.UserSignUpDto;
import com.example.mydatecourse.service.GoogleLoginService;
import com.example.mydatecourse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final GoogleLoginService googleLoginService;
    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<UserInfoDto> signUp(@RequestBody UserSignUpDto userSignUpDto) {
        UserInfoDto userInfoDto = userService.signUp(userSignUpDto);
        return ResponseEntity.ok(userInfoDto);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserLoginDto userLoginDto) {
        TokenDto tokenDto = userService.login(userLoginDto);
        return ResponseEntity.ok(tokenDto);
    }

    @GetMapping("/getUser")
    public ResponseEntity<UserInfoDto> getUser(Principal principal) {
        UserInfoDto userInfoDto = userService.findPrincipal(principal);
        return ResponseEntity.ok(userInfoDto);
    }

    @GetMapping("/google/getUser")
    public User getUserGoogle(Principal principal) {
        return googleLoginService.test(principal);
    }
}
