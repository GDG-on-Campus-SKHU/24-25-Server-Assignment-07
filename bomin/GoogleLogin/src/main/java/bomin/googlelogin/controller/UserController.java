package bomin.googlelogin.controller;
import bomin.googlelogin.domain.User;
import bomin.googlelogin.dto.request.UserLoginDto;
import bomin.googlelogin.dto.request.UserSignDto;
import bomin.googlelogin.dto.response.TokenDto;
import bomin.googlelogin.dto.response.UserInfoDto;
import bomin.googlelogin.service.GoogleLoginService;
import bomin.googlelogin.service.UserLoginService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserController {
    private final UserLoginService userLoginService;
    private final GoogleLoginService googleLoginService;

    //user 회원가입
    @PostMapping("/signUp")
    public ResponseEntity<TokenDto> signUp(@RequestBody UserSignDto userSignDto) {
        TokenDto tokenDto = userLoginService.signUp(userSignDto);
        return ResponseEntity.ok(tokenDto);
    }

    //user 로그인후, 만료된 액세스 토큰 갱신하기 위해 리플 토큰사용.
    @PostMapping("/login")
    public ResponseEntity<TokenDto> refreshAccessToken(@RequestBody UserLoginDto userLoginDto) {
        // 요청 본문에서 바로 Refresh Token 문자열 처리
        TokenDto tokenDto = userLoginService.login(userLoginDto);
        return ResponseEntity.ok(tokenDto);
    }

    //회원 가입되었는지 확인해보기
    @GetMapping("/test")
    public UserInfoDto getUser(Principal principal){
        return googleLoginService.test(principal);
    }
}