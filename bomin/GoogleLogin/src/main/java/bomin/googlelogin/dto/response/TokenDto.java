package bomin.googlelogin.dto.response;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TokenDto {
    //GSON에서 제공하는 JSON으로 직렬화 하거나 역직렬화할 때 필요한 필드 네이밍 지정하는 역할
    @SerializedName("access_token")
    private String accessToken;
    private String refreshToken;
}
