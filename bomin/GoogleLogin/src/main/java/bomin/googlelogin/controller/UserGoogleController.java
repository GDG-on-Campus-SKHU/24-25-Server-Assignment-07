package bomin.googlelogin.controller;
import bomin.googlelogin.domain.User;
import bomin.googlelogin.dto.response.TokenDto;
import bomin.googlelogin.service.GoogleLoginService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RequiredArgsConstructor(access= AccessLevel.PROTECTED)
@RestController
@RequestMapping("/api")
public class UserGoogleController {
    private final GoogleLoginService googleLoginService;

    @GetMapping("callback/google")
    public TokenDto googleCallback(@RequestParam(name = "code") String code){
        String googleAccessToken = googleLoginService.getGoogleAccessToken(code);
        return loginOrSignUp(googleAccessToken);
    }

    private TokenDto loginOrSignUp(String googleAccessToken){
        return googleLoginService.loginOrSignUp(googleAccessToken);
    }
}
