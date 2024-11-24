package junwatson.mychat.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoDto {
    private String id; // 구글에서 제공하는 ID가 String인 경우 유지
    private String email;

    @SerializedName("verified_email")
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
