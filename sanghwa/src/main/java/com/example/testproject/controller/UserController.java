package com.example.testproject.controller;

import com.example.testproject.domain.User;
import com.example.testproject.service.GoogleLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("gdg")
public class UserController {

    private final GoogleLoginService googleLoginService;

    @GetMapping("/getJWT")
    public User getUser(Principal principal) {
        return googleLoginService.test(principal);
    }
}
