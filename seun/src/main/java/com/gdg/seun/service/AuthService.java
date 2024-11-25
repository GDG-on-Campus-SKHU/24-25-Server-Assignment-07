package com.gdg.seun.service;

import com.gdg.seun.domain.LoginMethod;
import com.gdg.seun.domain.User;
import com.gdg.seun.domain.Role;
import com.gdg.seun.dto.CustomUserInfoDto;
import com.gdg.seun.dto.SignUpRequest;
import com.gdg.seun.dto.TokenDto;
import com.gdg.seun.dto.GoogleUserInfoDto;
import com.gdg.seun.jwt.TokenProvider;
import com.gdg.seun.repository.UserRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import java.net.URI;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${google.oauth.token-url}")
    private String GOOGLE_TOKEN_URL;

    @Value("${google.oauth.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${google.oauth.client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    @Value("${google.oauth.redirect-uri}")
    private String GOOGLE_REDIRECT_URI;

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public CustomUserInfoDto signUp(SignUpRequest signUpRequest) {
        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .name(signUpRequest.getName())
                .profileUrl(signUpRequest.getProfileUrl())
                .role(Role.USER)
                .loginMethod(LoginMethod.CUSTOM)
                .build();

        userRepository.save(user);

        return CustomUserInfoDto.from(user);
    }

    public TokenDto loginWithCredentials(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("등록되지 않은 사용자입니다."));

        if (!user.getLoginMethod().equals(LoginMethod.CUSTOM)) {
            throw new RuntimeException("해당 이메일은 CUSTOM 방식으로 로그인할 수 없습니다.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return TokenDto.builder()
                .accessToken(tokenProvider.createAccessToken(user))
                .build();
    }

    public String getGoogleAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = Map.of(
                "code", code,
                "scope", "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email",
                "client_id", GOOGLE_CLIENT_ID,
                "client_secret", GOOGLE_CLIENT_SECRET,
                "redirect_uri", GOOGLE_REDIRECT_URI,
                "grant_type", "authorization_code"
        );

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_URL, params, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String json = responseEntity.getBody();
            Gson gson = new Gson();

            return gson.fromJson(json, TokenDto.class)
                    .getAccessToken();
        }

        throw new RuntimeException("구글 엑세스 토큰을 가져오는데 실패했습니다.");
    }

    public TokenDto loginOrSignUpWithGoogle(String googleAccessToken) {
        GoogleUserInfoDto googleUserInfoDto = getUserInfo(googleAccessToken);

        if (!googleUserInfoDto.getVerifiedEmail()) {
            throw new RuntimeException("이메일 인증이 되지 않은 유저입니다.");
        }

        User user = userRepository.findByEmail(googleUserInfoDto.getEmail())
                .orElseGet(() -> userRepository.save(User.builder()
                        .email(googleUserInfoDto.getEmail())
                        .name(googleUserInfoDto.getName())
                        .profileUrl(googleUserInfoDto.getPictureUrl())
                        .role(Role.USER)
                        .loginMethod(LoginMethod.GOOGLE)
                        .build()));

        if (!user.getLoginMethod().equals(LoginMethod.GOOGLE)) {
            throw new RuntimeException("해당 이메일은 GOOGLE 방식으로 로그인할 수 없습니다.");
        }

        return TokenDto.builder()
                .accessToken(tokenProvider.createAccessToken(user))
                .build();
    }

    private GoogleUserInfoDto getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String json = responseEntity.getBody();
            Gson gson = new Gson();
            return gson.fromJson(json, GoogleUserInfoDto.class);
        }

        throw new RuntimeException("유저 정보를 가져오는데 실패했습니다.");
    }

    public User test(Principal principal) {
        Long id = Long.parseLong(principal.getName());

        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
    }

    public User register(String email, String password, String name) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new RuntimeException("이미 등록된 이메일입니다.");
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .profileUrl(null)
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }
}
