package com.gdg.songin.user.service;


import com.gdg.songin.jwt.TokenProvider;
import com.gdg.songin.user.domain.Role;
import com.gdg.songin.user.domain.User;
import com.gdg.songin.user.dto.TokenDto;
import com.gdg.songin.user.dto.UserInfo;
import com.gdg.songin.user.repository.UserRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.security.Principal;
import java.util.Map;

@Service
public class GoogleLoginService {

    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";

    @Value("${songinseok.client_id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${songinseok.client_secret}")
    private String GOOGLE_CLIENT_SECRET;

    private final String GOOGLE_REDIRECT_URI = "http://localhost:8080/api/callback/google";

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public GoogleLoginService(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
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

        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            String json = responseEntity.getBody();
            Gson gson = new Gson();

            return gson.fromJson(json, TokenDto.class)
                    .getAccessToken();
        }
        throw new RuntimeException("구글 엑세스 토큰을 가져오는데 실패했습니다");
    }

    //아래 getUserInfo로 받아온 사용자 정보를 이용해서 email이 있으면 로그인되고 없으면 회원가입 되게끔
    public TokenDto loginOrSignUp(String googleAccessToken) {
        UserInfo userInfo = getUserInfo(googleAccessToken);

        if(!userInfo.getVerifiedEmail()) {
            throw new RuntimeException("이메일이 인증이 되지 않은 유저입니다.");
        }

        User user = userRepository.findByEmail(userInfo.getEmail())
                .orElseGet(() -> userRepository.save(User.builder()
                        .email(userInfo.getEmail())
                        .name(userInfo.getName())
                        .profileUrl(userInfo.getPictureUrl())
                        .role(Role.USER)
                        .build())
                );
        return TokenDto.builder()
                .accessToken(tokenProvider.createAccessToken(user))
                .build();
    }

    //구글에서 받는 엑세스 토큰으로 그것을 리소스 서버로 보내고 검증을 보내면 사용자 정보를 받아오는 것
    private UserInfo getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);


        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            String json = responseEntity.getBody();
            Gson gson = new Gson();
            return gson.fromJson(json, UserInfo.class);
        }
        throw new RuntimeException("유저 정보를 가져오는데 실패하셨습니다.");
    }

    public User test(Principal principal){
        Long id = Long.parseLong(principal.getName());

        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
    }

}
