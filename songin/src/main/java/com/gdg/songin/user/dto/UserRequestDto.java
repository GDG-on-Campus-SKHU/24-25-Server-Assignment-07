package com.gdg.songin.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserRequestDto {
    Long UserId;
    String password;
    String nickName;
    String email;
}
