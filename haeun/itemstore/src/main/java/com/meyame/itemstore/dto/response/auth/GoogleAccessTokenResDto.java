package com.meyame.itemstore.dto.response.auth;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GoogleAccessTokenResDto {
        @SerializedName("access_token")
        private String googleAccessToken;
}
