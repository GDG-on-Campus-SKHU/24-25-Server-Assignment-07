package com.gdg.seun.controller;

import com.gdg.seun.domain.User;
import com.gdg.seun.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("gdg")
public class UserController {

    private final AuthService authService;

    @GetMapping("/test")
    public User getUser(Principal principal) {
        return authService.test(principal);
    }
}
