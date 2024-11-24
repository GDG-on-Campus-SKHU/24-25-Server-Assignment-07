package bomin.googlelogin.dto.request;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginDto {
    private String id;
    private String email;
    private String password;
}
