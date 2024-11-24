package bomin.googlelogin.dto.request;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignDto {
    private String name;
    private String email;
    private String password;
}
