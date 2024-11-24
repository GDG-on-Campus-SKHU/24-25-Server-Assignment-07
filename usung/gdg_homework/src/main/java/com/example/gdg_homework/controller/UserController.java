package com.example.gdg_homework.controller;

import com.example.gdg_homework.service.GoogleLoginService;
import lombok.RequiredArgsConstructor;
import com.example.gdg_homework.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("gdg")
public class UserController {

    private final GoogleLoginService googleLoginService;

    @GetMapping("/test")
    public User getUser(Principal principal) {
        return googleLoginService.test(principal);
    }
}
