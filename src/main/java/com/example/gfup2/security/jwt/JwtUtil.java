package com.example.gfup2.security.jwt;

import com.example.gfup2.domain.model.RefreshToken;
import com.example.gfup2.domain.repository.RefreshTokenRepository;
import com.example.gfup2.dto.TokenDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${hidden.jwtSecret}")
    private String jwtSecret;
    private final long ACCESS_TOKEN_VALID_PERIOD = 1000L*60*30;
//1000L*60*30; // 1분
    private final long REFRESH_TOKEN_VALID_PERIOD = 1000L*60*60*24*7;
    private final Set<String> invalidatedTokens = new HashSet<>();

    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESH_TOKEN = "refreshToken";

    public TokenDto generateToken(String name){
        Date now = new Date();
        Date accessTokenExpireIn = new Date(now.getTime() + ACCESS_TOKEN_VALID_PERIOD);


        String accessToken = Jwts.builder()
                .setClaims(Jwts.claims().setSubject(name))
                .setIssuedAt(now)
                .setExpiration(accessTokenExpireIn)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(Jwts.claims().setSubject(name))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALID_PERIOD))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();


        return new TokenDto(accessToken, refreshToken, accessTokenExpireIn.getTime());
    }


    public String getUsername(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isTokenValid(String token){
        if (invalidatedTokens.contains(token)) {
            log.info("Token is invalidated");
            return false;
        }
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.info("Token이 만료됫다 ㅇ;ㅣ");
            return false;
        } catch (JwtException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean refreshTokenValidation(String token) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccountEmail(getUsername(token));
        log.info(getUsername(token));
        return refreshToken.isPresent() && token.equals(refreshToken.get().getRefreshToken());
    }

    public String parseJwtAccess(HttpServletRequest request){
        String headerAuth = request.getHeader("accessToken");
        if(StringUtils.hasText(headerAuth)){
            return headerAuth;
        }
        return null;
    }

    public String parseJwtRefresh(HttpServletRequest request){
        String headerAuth = request.getHeader("refreshToken");
        if(StringUtils.hasText(headerAuth)){
            return headerAuth;
        }
        return null;
    }

    public void invalidateToken(String token) {
        invalidatedTokens.add(token);
    }


    // header 토큰을 가져오는 기능
    public String getHeaderToken(HttpServletRequest request, String type) {
        return type.equals("Access") ? request.getHeader(ACCESS_TOKEN) :request.getHeader(REFRESH_TOKEN);
    }


}
