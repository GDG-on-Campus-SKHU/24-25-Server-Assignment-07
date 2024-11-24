package junwatson.mychat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSignInRequestDto {
    private String email;
    private String password;
}