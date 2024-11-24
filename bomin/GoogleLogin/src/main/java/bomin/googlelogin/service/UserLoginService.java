package bomin.googlelogin.service;
import bomin.googlelogin.domain.User;
import bomin.googlelogin.dto.request.UserLoginDto;
import bomin.googlelogin.dto.request.UserSignDto;
import bomin.googlelogin.dto.response.TokenDto;
import bomin.googlelogin.dto.response.UserResponseDto;
import bomin.googlelogin.jwt.TokenProvider;
import bomin.googlelogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserLoginService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    //사용자 회원가입
    @Transactional
    public TokenDto signUp(UserSignDto userSignDto){
        User user = userRepository.save(User.builder()
                .email(userSignDto.getEmail())
                .password(passwordEncoder.encode(userSignDto.getPassword()))
                .build());

        String accessToken = tokenProvider.createAccessToken(user);
        String refreshToken = tokenProvider.createRefreshToken(user);
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    //refresh토큰을 이용한 로그인
    @Transactional
    public TokenDto login(UserLoginDto userLoginDto){
        User user = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일 아닙니다."));

        // 새로운 액세스 토큰 생성
        String newAccessToken = tokenProvider.createAccessToken(user);

        return TokenDto.builder()
                .accessToken(newAccessToken)
                .build();
    }
}
