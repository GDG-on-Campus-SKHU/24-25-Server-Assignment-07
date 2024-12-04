package bomin.googlelogin.service;
import bomin.googlelogin.domain.Role;
import bomin.googlelogin.domain.User;
import bomin.googlelogin.dto.response.TokenDto;
import bomin.googlelogin.dto.response.GoogleUserInfo;
import bomin.googlelogin.dto.response.UserInfoDto;
import bomin.googlelogin.jwt.TokenProvider;
import bomin.googlelogin.repository.UserRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final String GOOGLE_CLIENT_ID;
    private final String GOOGLE_CLIENT_SECRET;
    private final String GOOGLE_REDIRECT_URI ="http://localhost:8080/api/callback/google";

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Autowired
    public GoogleLoginService(@Value("${oauth.google-client-id}") String id,
                              @Value("${oauth.google-client-secret}") String secret,
                              UserRepository userRepository, TokenProvider tokenProvider) {
        this.GOOGLE_CLIENT_ID = id;
        this.GOOGLE_CLIENT_SECRET = secret;
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    public String getGoogleAccessToken(String code){
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

    public TokenDto loginOrSignUp(String googleAccessToken) {
        GoogleUserInfo googleUserInfo = getUserInfo(googleAccessToken);

        if (!googleUserInfo.getVerifiedEmail()) {
            throw new RuntimeException("이메일 인증이 되지 않은 유저입니다.");
        }

        User user = userRepository.findByEmail(googleUserInfo.getEmail())
                .orElseGet(() -> userRepository.save(User.builder()
                        .email(googleUserInfo.getEmail())
                        .name(googleUserInfo.getName())
                        .profileUrl(googleUserInfo.getPictureUrl())
                        .role(Role.USER)
                        .build())
                );

        return TokenDto.builder()
                .accessToken(tokenProvider.createAccessToken(user)) //String 반환값
                .build();
    }

    private GoogleUserInfo getUserInfo(String accessToken) {
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
            return gson.fromJson(json, GoogleUserInfo.class);
        }

        throw new RuntimeException("유저 정보를 가져오는데 실패했습니다.");
    }

    public UserInfoDto test(Principal principal) {
        Long id = Long.parseLong(principal.getName());

        User user =  userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        return UserInfoDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
