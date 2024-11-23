package junwatson.mychat.controller;

import junwatson.mychat.dto.response.TokenDto;
import junwatson.mychat.service.GoogleLoginService;
import junwatson.mychat.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authorization")
@Slf4j
public class AuthorizationController {

    private final GoogleLoginService googleLoginService;

    @GetMapping("/google")
    public TokenDto googleCallback(@RequestParam(name = "code") String code) {
        log.info("AuthorizationController.googleCallback() called");

        String googleAccessToken = googleLoginService.getGoogleAccessToken(code);
        return googleLoginService.loginOrSignUp(googleAccessToken);
    }

}
