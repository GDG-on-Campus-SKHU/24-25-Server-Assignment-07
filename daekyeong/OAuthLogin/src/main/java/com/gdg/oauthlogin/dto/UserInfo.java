package com.gdg.oauthlogin.dto;

import com.gdg.oauthlogin.domain.LoginType;
import com.gdg.oauthlogin.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserInfo {
    private Long id;
    private String email;
    private String password;
    private String name;
    private Role role;
    private LoginType loginType;
}
