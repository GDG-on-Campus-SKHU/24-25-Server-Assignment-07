package junwatson.mychat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSignUpRequestDto {
    private String email;
    private String name;
    private String password;
    private String profileUrl;
}