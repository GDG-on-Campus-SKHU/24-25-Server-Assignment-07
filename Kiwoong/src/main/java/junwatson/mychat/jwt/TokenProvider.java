package junwatson.mychat.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import junwatson.mychat.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static junwatson.mychat.jwt.TokenConstant.*;

@Component
@Slf4j
public class TokenProvider {

    @Value("${jwt.secret}") // 비밀 키를 외부 설정 파일에서 주입받도록 수정
    private String secretKey;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)); // 비밀 키를 기반으로 키 생성
    }

    public String createAccessToken(Member member) {
        log.info("TokenProvider.createAccessToken() called");

        long nowTime = System.currentTimeMillis();
        Date accessTokenExpiredTime = new Date(nowTime + ACCESS_TOKEN_VALIDITY_TIME);

        return Jwts.builder()
                .setSubject(member.getId().toString())
                .claim(ROLE_CLAIM, member.getRole().name())
                .claim(TOKEN_TYPE_CLAIM, TokenType.ACCESS)
                .setExpiration(accessTokenExpiredTime)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // 서명 키 변경
                .compact();
    }

    public Authentication getAuthentication(String accessToken) {
        log.info("TokenProvider.getAuthentication() called");

        Claims claims = parseClaims(accessToken);

        if (claims.get(ROLE_CLAIM) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(ROLE_CLAIM).toString().split(","))
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
    }

    public String resolveToken(HttpServletRequest request) {
        log.info("TokenProvider.resolveToken() called");

        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        log.info("TokenProvider.validateToken() called");

        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey()) // 서명 키 변경
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("JWT 토큰이 만료되었습니다: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("지원하지 않는 JWT 토큰입니다: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("JWT 토큰이 잘못되었습니다: {}", e.getMessage());
        } catch (SignatureException e) {
            log.error("JWT 서명 불일치: {}", e.getMessage());
        }
        return false;
    }

    public boolean hasProperType(String token, TokenType tokenType) {
        log.info("TokenProvider.hasProperType() called");

        Claims claims = parseClaims(token);
        String tokenTypeClaim = (String) claims.get(TOKEN_TYPE_CLAIM);

        return tokenType == TokenType.valueOf(tokenTypeClaim);
    }

    public Claims parseClaims(String accessToken) {
        log.info("TokenProvider.parseClaims() called");

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey()) // 서명 키 변경
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰: {}", e.getMessage());
            return e.getClaims();
        } catch (SignatureException e) {
            log.error("토큰 복호화에 실패했습니다: {}", e.getMessage());
            throw new RuntimeException("토큰 복호화에 실패했습니다.");
        }
    }
}
