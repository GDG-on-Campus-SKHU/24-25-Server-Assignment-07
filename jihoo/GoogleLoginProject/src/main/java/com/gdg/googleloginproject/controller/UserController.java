package com.gdg.googleloginproject.controller;

import com.gdg.googleloginproject.dto.response.TokenDto;
import com.gdg.googleloginproject.dto.request.UserLoginDto;
import com.gdg.googleloginproject.dto.request.UserSignUpDto;
import com.gdg.googleloginproject.dto.response.UserInfoDto;
import com.gdg.googleloginproject.service.UserService;
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

    @GetMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok().body(userService.loginUser(userLoginDto));
    }

    @GetMapping
    public ResponseEntity<UserInfoDto> getUser(Principal principal) {
        return ResponseEntity.ok().body(userService.getUser(principal));
    }
}
