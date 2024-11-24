package bomin.googlelogin.dto.response;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GoogleUserInfo {
    private String id;
    private String email;
    @SerializedName("verified_email")
    private Boolean verifiedEmail; //사용자가 제공한 이메일 주소가 실제로 그 사용자에게 속하는지 확인
    private String name;
    @SerializedName("given_name")
    private String givenName;
    @SerializedName("family_name")
    private String familyName;
    @SerializedName("picture")
    private String pictureUrl;
    private String locale;
}
