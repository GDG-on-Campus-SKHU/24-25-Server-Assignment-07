package bomin.googlelogin.dto.response;
import bomin.googlelogin.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;

    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}