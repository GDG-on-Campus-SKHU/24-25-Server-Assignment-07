package com.example.testproject.service;

import com.example.testproject.domain.Role;
import com.example.testproject.domain.User;
import com.example.testproject.dto.TokenDto;
import com.example.testproject.dto.UserInfo;
import com.example.testproject.jwt.TokenProvider;
import com.example.testproject.repository.AdminEmailRepository;
import com.example.testproject.repository.UserRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.security.Principal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleLoginService {
    //Oauth2서버의 url, 이곳에다 엑세스 토큰을 요청
    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    //엑세스 토큰을 요청하는데에 필요한 애플리케이션 정보
    //GOOGLE_REDIRECT_URL은 인증을 마치면 리다이렉트될 URL
    private final String GOOGLE_CLIENT_ID = "30189901443-beemg2flckdcsecs72gngvjimho3nftj.apps.googleusercontent.com";
    private final String GOOGLE_CLIENT_SECRET = "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";
    private final String GOOGLE_REDIRECT_URI = "http://localhost:8080/api/callback/google";

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AdminEmailRepository adminEmailRepository;

    //이 메서드는 Google OAuth2 인증 서버에 엑세스 토큰을 요청하는 역할을 해요
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
        //restTemplate을 구글 서버에 POST요청
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_URL, params, String.class);
        //응답을 잘 받았으면 json문자열을 getBody로 받고 TokenDto로 변환한 다음, AccessToken을 얻는다.
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String json = responseEntity.getBody();
            Gson gson = new Gson();

            return gson.fromJson(json, TokenDto.class)
                    .getAccessToken();
        }

        throw new RuntimeException("구글 엑세스 토큰을 가져오는데 실패했습니다.");
    }

    //위에서 얻은 accessToken으로 회원가입or로그인을 진행하고 JWT토큰을 발행한다.
    //
    public TokenDto loginOrSignUp(String googleAccessToken) {
        UserInfo userInfo = getUserInfo(googleAccessToken);

        if (!userInfo.getVerifiedEmail()) {
            throw new RuntimeException("이메일 인증이 되지 않은 유저입니다.");
        }
        if (adminEmailRepository.existsByEmail(userInfo.getEmail())) {
            User user = userRepository.findByEmail(userInfo.getEmail())
                    .orElseGet(() -> userRepository.save(User.builder()
                            .email(userInfo.getEmail())
                            .name(userInfo.getName())
                            .profileUrl(userInfo.getPictureUrl())
                            .role(Role.ADMIN)
                            .build())
                    );
            return TokenDto.builder()
                    .accessToken(tokenProvider.createAccessToken(user))
                    .build();
        } else {
            //repo에 있으면 바로 반환, 없으면 save
            User user = userRepository.findByEmail(userInfo.getEmail())
                    .orElseGet(() -> userRepository.save(User.builder()
                            .email(userInfo.getEmail())
                            .name(userInfo.getName())
                            .profileUrl(userInfo.getPictureUrl())
                            .role(Role.USER)
                            .build())
                    );
            //로그인 or 회원가입을 진행하고 그냥 AccessToken반환, 로그인하고 회원가입이 묶여있어서
            //JWT 토큰을 받으려면 test를 호출해야됨. 6주차 로그인 로직임.
            return TokenDto.builder()
                    .accessToken(tokenProvider.createAccessToken(user))
                    .build();
        }
    }

    //구글에게 토큰으로 사용자 정보를 get, 근데 이게 왜 필요함?
    private UserInfo getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
        //exchange메서드를 사용하여 사용자API에 GET요청 때리기
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String json = responseEntity.getBody();
            Gson gson = new Gson();
            return gson.fromJson(json, UserInfo.class);
        }

        throw new RuntimeException("유저 정보를 가져오는데 실패했습니다.");
    }

    public User test(Principal principal) {
        Long id = Long.parseLong(principal.getName());
        //유저정보와 JWT토큰 반환
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
    }
}

