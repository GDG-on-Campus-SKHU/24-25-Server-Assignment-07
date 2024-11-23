package junwatson.mychat.service;

import junwatson.mychat.domain.Member;
import junwatson.mychat.domain.type.MemberRole;
import junwatson.mychat.dto.MemberSignInRequestDto;
import junwatson.mychat.dto.MemberSignUpRequestDto;
import junwatson.mychat.dto.MemberResponseDto;
import junwatson.mychat.dto.response.TokenDto;
import junwatson.mychat.jwt.TokenProvider;
import junwatson.mychat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    public TokenDto signUp(MemberSignUpRequestDto requestDto) {
        // 이메일 중복 검사
        if (memberRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이메일이 중복됩니다.");
        }

        // 비밀번호 해싱
        String hashedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 회원 객체 생성
        Member member = Member.builder()
                .email(requestDto.getEmail())
                .name(requestDto.getName())
                .password(hashedPassword) // 해싱된 비밀번호 저장
                .profileUrl(requestDto.getProfileUrl())
                .role(MemberRole.USER)
                .build();

        // 회원 저장
        memberRepository.save(member);

        // JWT 토큰 생성
        String accessToken = tokenProvider.createAccessToken(member);

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    public TokenDto signIn(MemberSignInRequestDto requestDto) {
        // 이메일로 회원 조회
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        // 비밀번호 확인
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        String accessToken = tokenProvider.createAccessToken(member);

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    public MemberResponseDto findById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        return MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .profileUrl(member.getProfileUrl())
                .build();
    }
}
