package bomin.googlelogin.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
class RefreshTokenDto {
    private String refreshToken;
}