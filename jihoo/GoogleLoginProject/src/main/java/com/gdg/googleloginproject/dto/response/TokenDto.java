package com.gdg.googleloginproject.dto.response;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenDto {

    @SerializedName("access_token")
    private String accessToken;

    public static TokenDto from(String accessToken) {
       return TokenDto.builder()
               .accessToken(accessToken)
               .build();
    }
}
