package com.gdg.googleloginproject.service;

import com.gdg.googleloginproject.domain.LoginMethod;
import com.gdg.googleloginproject.domain.Role;
import com.gdg.googleloginproject.domain.User;
import com.gdg.googleloginproject.dto.response.TokenDto;
import com.gdg.googleloginproject.dto.response.UserInfo;
import com.gdg.googleloginproject.exception.CustomException;
import com.gdg.googleloginproject.exception.ErrorMessage;
import com.gdg.googleloginproject.jwt.TokenProvider;
import com.gdg.googleloginproject.repository.UserRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.security.Principal;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleLoginService {

    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";

    @Value("${client_id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${client_secret}")
    private String GOOGLE_CLIENT_SECRET;

    private final String GOOGLE_REDIRECT_URI = "http://localhost:8080/api/callback/google";

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

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

        throw new CustomException(ErrorMessage.FAILED_TO_FETCH_GOOGLE_TOKEN);
    }

    public TokenDto loginOrSignUp(String googleAccessToken) {
        UserInfo userInfo = getUserInfo(googleAccessToken);

        if (!userInfo.getVerifiedEmail()) {
            throw new CustomException(ErrorMessage. EMAIL_NOT_VERIFIED);
        }

        User user = userRepository.findByEmail(userInfo.getEmail())
                .orElseGet(() -> userRepository.save(User.builder()
                        .email(userInfo.getEmail())
                        .username(userInfo.getName())
                        .profileUrl(userInfo.getPictureUrl())
                        .role(Role.USER)
                        .loginMethod(LoginMethod.GOOGLE) //구글로 가입/로그인 구분
                        .build())
                );

        return TokenDto.builder()
                .accessToken(tokenProvider.createAccessToken(user))
                .build();
    }

    private UserInfo getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {

            String json = responseEntity.getBody();
            log.info("Google User Info Response: {}", json);
            Gson gson = new Gson();
            return gson.fromJson(json, UserInfo.class);
        }

        throw new CustomException(ErrorMessage.FAILED_TO_FETCH_USER_INFO);
    }

    public User test(Principal principal) {
        Long id = Long.parseLong(principal.getName());

        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_IS_NOT_EXIST));
    }
}
