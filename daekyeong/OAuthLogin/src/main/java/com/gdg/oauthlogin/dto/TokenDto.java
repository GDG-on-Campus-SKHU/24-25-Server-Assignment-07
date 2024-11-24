package com.gdg.oauthlogin.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDto {
    @SerializedName("access_token")
    private String accessToken;
}
