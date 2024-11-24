package junwatson.mychat.controller;

import junwatson.mychat.dto.MemberResponseDto;
import junwatson.mychat.dto.MemberSignInRequestDto; // DTO 임포트
import junwatson.mychat.dto.MemberSignUpRequestDto;
import junwatson.mychat.dto.response.TokenDto;
import junwatson.mychat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<TokenDto> signUp(@RequestBody MemberSignUpRequestDto requestDto) {
        TokenDto tokenDto = memberService.signUp(requestDto);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/signin") // 수정된 부분
    public ResponseEntity<TokenDto> signIn(@RequestBody MemberSignInRequestDto requestDto) { // DTO를 매개변수로 변경
        TokenDto tokenDto = memberService.signIn(requestDto); // DTO를 사용
        return ResponseEntity.ok(tokenDto);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> findById(@PathVariable Long memberId) {
        MemberResponseDto memberInfo = memberService.findById(memberId);
        return ResponseEntity.ok(memberInfo);
    }
}
