package com.meyame.itemstore.service;

import com.google.gson.Gson;
import com.meyame.itemstore.domain.auth.AuthProvider;
import com.meyame.itemstore.domain.auth.Role;
import com.meyame.itemstore.domain.auth.User;
import com.meyame.itemstore.dto.response.auth.GoogleAccessTokenResDto;
import com.meyame.itemstore.dto.response.auth.GoogleUserInfoResDto;
import com.meyame.itemstore.dto.response.auth.JwtAccessTokenResDto;
import com.meyame.itemstore.dto.response.auth.UserInfoDto;
import com.meyame.itemstore.exception.CustomException;
import com.meyame.itemstore.jwt.JwtTokenProvider;
import com.meyame.itemstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.security.Principal;
import java.util.Map;

import static com.meyame.itemstore.exception.ErrorCode.*;

@Service
public class GoogleLoginService {

    private final String GOOGLE_CLIENT_ID;
    private final String GOOGLE_CLIENT_SECRET;
    private final String GOOGLE_REDIRECT_URI;

    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 아래처럼 생성자 주입 방식으로 안하면 오류남.....
    public GoogleLoginService(
            @Value("${google.client-id}") String GOOGLE_CLIENT_ID,
            @Value("${google.client-secret}") String GOOGLE_CLIENT_SECRET,
            @Value("${google.redirect-url}") String GOOGLE_REDIRECT_URI,
            UserRepository userRepository,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.GOOGLE_CLIENT_ID = GOOGLE_CLIENT_ID;
        this.GOOGLE_CLIENT_SECRET = GOOGLE_CLIENT_SECRET;
        this.GOOGLE_REDIRECT_URI = GOOGLE_REDIRECT_URI;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /*
    code 를 이용해서 구글의 토큰 엔드포인트로 요청을 보내 구글 액세스 토큰을 받아온다.
    GOOGLE_TOKEN_URL : https://oauth2.googleapis.com/token
     */
    public String getGoogleAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        // 요청 파라미터 정의
        Map<String, String> params = Map.of(
                "code", code,
                "scope", "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email",
                "client_id", GOOGLE_CLIENT_ID,
                "client_secret", GOOGLE_CLIENT_SECRET,
                "redirect_uri", GOOGLE_REDIRECT_URI,
                "grant_type", "authorization_code"
        );
        // 요청 파라미터를 정의했으니 이제 요청을 보내고 구글 Access Token 응답을 받아온다.
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_URL, params, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String json = responseEntity.getBody();
            Gson gson = new Gson(); // JSON -> Java 객체

            return gson.fromJson(json, GoogleAccessTokenResDto.class)
                    .getGoogleAccessToken();
        }

        throw new CustomException(GOOGLE_ACCESS_TOKEN_ERROR);
    }

    // googlee access token 을 사용하여 getUserInfo()를 호출하여 사용자 정보를 받아오고
    // JWT 액세스 토큰을 생성한다.
    public JwtAccessTokenResDto loginOrSignUp(String googleAccessToken) {
        GoogleUserInfoResDto userInfo = getUserInfoByGoogleAccessToken(googleAccessToken);

        if (!userInfo.getVerifiedEmail()) {
            throw new CustomException(GOOGLE_EMAIL_ERROR);
        }

        User user = userRepository.findByEmail(userInfo.getEmail())
                .orElseGet(() -> userRepository.save(User.builder()
                        .email(userInfo.getEmail())
                        .name(userInfo.getName())
                        .pictureUrl(userInfo.getPictureUrl())
                        .role(Role.USER)
                        .authProvider(AuthProvider.GOOGLE)
                        .build())
                );

        return JwtAccessTokenResDto.builder()
                .accessToken(jwtTokenProvider.createAccessToken(user))
                .build();
    }

    // 구글 API 를 호출하여 사용자 정보를 가져온다.
    public GoogleUserInfoResDto getUserInfoByGoogleAccessToken(String googleAccessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + googleAccessToken;

        // HTTP 헤더에 Bearer 토큰을 추가하여 인증 정보를 전달
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + googleAccessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 사용자 정보를 가져오는 GET 요청을 보낸다.
        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        // 응답 데이터를 JSON 으로 받아 GoogleUserInfoResDto 객체로 변환하여 반환
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String json = responseEntity.getBody();
            Gson gson = new Gson();
            return gson.fromJson(json, GoogleUserInfoResDto.class);
        }

        throw new CustomException(GOOGLE_USER_INFO_ERROR);
    }

    public UserInfoDto getGoogleUserInfo(Principal principal) {
        Long id = Long.parseLong(principal.getName());

        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(GOOGLE_USER_INFO_ERROR));

        return UserInfoDto.from(user);
    }
}
