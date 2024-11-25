package com.gdg.oauthlogin.controller;

import com.gdg.oauthlogin.domain.User;
import com.gdg.oauthlogin.dto.TokenDto;
import com.gdg.oauthlogin.dto.UserInfo;
import com.gdg.oauthlogin.dto.UserSignUpDto;
import com.gdg.oauthlogin.service.GoogleLoginService;
import com.gdg.oauthlogin.service.NormalLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final GoogleLoginService googleLoginService;
    private final NormalLoginService normalLoginService;

    @PostMapping("/signup")
    public TokenDto emailSignUp(@RequestBody UserSignUpDto userSignUpDto) {
        return normalLoginService.signUp(userSignUpDto);
    }

    @GetMapping("/info/my")
    public UserInfo getUserInfoByPrincipal(Principal principal) {
        return normalLoginService.findByPrincipal(principal);
    }

    @GetMapping("/info/{id}")
    public UserInfo getUserInfo(@PathVariable("id") Long id) {
        return normalLoginService.getUserInfo(id);
    }

    @DeleteMapping("/delete")
    public void deleteUser(Principal principal){
        normalLoginService.deleteUser(principal);
    }
}
