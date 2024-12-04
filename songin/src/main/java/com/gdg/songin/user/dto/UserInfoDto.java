package com.gdg.songin.user.dto;

import com.gdg.songin.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInfoDto {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String address;
    private String role;


    @Builder
    public UserInfoDto(String password, String name, String email, String phoneNumber, String address, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }

    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .role(user.getRole().name())
                .build();
    }
}
