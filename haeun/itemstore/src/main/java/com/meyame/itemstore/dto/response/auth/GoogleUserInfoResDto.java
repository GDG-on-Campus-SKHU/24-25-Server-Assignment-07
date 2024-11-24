package com.meyame.itemstore.dto.response.auth;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GoogleUserInfoResDto {
        private String id;
        private String email;
        @SerializedName("verified_email") // JSON 의 verified_email 값이 verifiedEmail 필드에 매핑된다.
        private Boolean verifiedEmail;
        private String name;
        @SerializedName("given_name")
        private String givenName;
        @SerializedName("family_name")
        private String familyName;
        @SerializedName("picture")
        private String pictureUrl;
        private String locale;
}
