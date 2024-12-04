package com.gdg.googleloginexample.controller;

import com.gdg.googleloginexample.domain.User;
import com.gdg.googleloginexample.dto.TokenDto;
import com.gdg.googleloginexample.dto.UserInfoDto;
import com.gdg.googleloginexample.dto.UserLoginDto;
import com.gdg.googleloginexample.dto.UserSignUpDto;
import com.gdg.googleloginexample.service.GoogleLoginService;
import com.gdg.googleloginexample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final GoogleLoginService googleLoginService;
    private final UserService userService;

    @GetMapping("/test")
    public User getUser(Principal principal) {
        return googleLoginService.test(principal);
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserInfoDto> signUp(@RequestBody UserSignUpDto userSignUpDto) {
        UserInfoDto userInfoDto = userService.signUp(userSignUpDto);
        return ResponseEntity.ok(userInfoDto);
    }

    @PostMapping("/admin/signUp")
    public ResponseEntity<UserInfoDto> signUpAdmin(@RequestBody UserSignUpDto userSignUpDto) {
        UserInfoDto userInfoDto =userService.signUpAdmin(userSignUpDto);
        return ResponseEntity.ok(userInfoDto);
    }

    @GetMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserLoginDto userLoginDto) {
        TokenDto response = userService.login(userLoginDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserInfoDto>> getAllUsers(Principal principal) {

        return ResponseEntity.ok(userService.getAllUsers());
    }
}