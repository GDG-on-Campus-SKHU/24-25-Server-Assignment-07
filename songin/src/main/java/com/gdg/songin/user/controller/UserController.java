package com.gdg.songin.user.controller;

import com.gdg.songin.user.dto.TokenDto;
import com.gdg.songin.user.dto.UserInfoDto;
import com.gdg.songin.user.dto.UserLoginDto;
import com.gdg.songin.user.dto.UserSignUpDto;
import com.gdg.songin.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserInfoDto> saveUser(@RequestBody UserSignUpDto userSignUpDto) {
        return ResponseEntity.ok().body(userService.saveUser(userSignUpDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok().body(userService.loginUser(userLoginDto));
    }

    @GetMapping
    public ResponseEntity<UserInfoDto> getUser(Principal principal) {
        return ResponseEntity.ok().body(userService.getMyInfo(principal));
    }
}
