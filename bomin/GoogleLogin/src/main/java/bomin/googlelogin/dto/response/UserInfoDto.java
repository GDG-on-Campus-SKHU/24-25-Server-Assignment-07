package bomin.googlelogin.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserInfoDto {
    private Long id;
    private String email;
    private String password;
    private String name;
}
