package com.example.assignment07.dto.UserDto;

import lombok.AllArgsConstructor;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenDto {

    @SerializedName("access_token")
    private String accessToken;
}
