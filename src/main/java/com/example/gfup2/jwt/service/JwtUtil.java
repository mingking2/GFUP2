    package com.example.gfup2.jwt.service;

    import com.example.gfup2.jwt.UserDetail.UserDetailsServiceImpl;
    import com.example.gfup2.jwt.dto.TokenDto;
    import com.example.gfup2.jwt.entity.RefreshToken;
    import com.example.gfup2.jwt.repository.RefreshTokenRepository;
    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.SignatureAlgorithm;
    import io.jsonwebtoken.security.Keys;
    import jakarta.annotation.PostConstruct;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.security.core.Authentication;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.stereotype.Component;

    import java.security.Key;
    import java.util.Base64;
    import java.util.Date;
    import java.util.Optional;

    @Slf4j
    @Component
    @RequiredArgsConstructor
    public class JwtUtil {

        private final RefreshTokenRepository refreshTokenRepository;
        private final UserDetailsServiceImpl userDetailsService;
        @Value("${token.accesstoken-valid-period}")
        private String ACCESS_TIME;
        @Value("${token.refreshtoken-valid-period}")
        private String REFRESH_TIME;
        public static final String ACCESS_TOKEN = "Access_Token";
        public static final String REFRESH_TOKEN = "Refresh_Token";

        @Value("${jwt.secretkey}")
        private String secretKey;
        private Key key;
        private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // bean으로 등록 되면서 딱 한번 실행이 됩니다.
        @PostConstruct
        public void init() {
            byte[] bytes = Base64.getDecoder().decode(secretKey);
            key = Keys.hmacShaKeyFor(bytes);
        }

        // header 토큰을 가져오는 기능
        public String getHeaderToken(HttpServletRequest request, String type) {
            return type.equals("Access") ? request.getHeader(ACCESS_TOKEN) :request.getHeader(REFRESH_TOKEN);
        }

        // 토큰 생성
        public TokenDto createAllToken(String emailId) {
            return new TokenDto(createToken(emailId, "Access"), createToken(emailId, "Refresh"));
        }

        public String createToken(String emailId, String type) {

            Date date = new Date();

            long time = type.equals("Access") ? Long.parseLong(ACCESS_TIME) : Long.parseLong(REFRESH_TIME);

            return Jwts.builder()
                    .setSubject(emailId)
                    .setExpiration(new Date(date.getTime() + time))
                    .setIssuedAt(date)
                    .signWith(key, signatureAlgorithm)
                    .compact();
        }

        // 토큰 검증 - jwtSecretKey와 동일한지 비교함.
        public Boolean tokenValidation(String token) {
            //parseClaimsJws함수에서 만약 해당 토큰 만료시, ExpiredJwtException 예외를 발생시킴
            try {
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
                return true;
            } catch (Exception ex) { //기타 예외는 모두 false로 처리 -> ExpiredJwtException도 기타로 치부됨
                return false;
            }
        }

        // refreshToken 토큰 검증
        // db에 저장되어 있는 token과 비교
        // db에 저장한다는 것이 jwt token을 사용한다는 강점을 상쇄시키기에 redis를 사용하는게 좋다.(나중에 하지 뭐)
        //  -> (in-memory db기 때문에 조회속도가 빠르고 주기적으로 삭제하는 기능이 기본적으로 존재하기 때문)
        public Boolean refreshTokenValidation(String token) {
            // 1차 토큰 검증
            if(!tokenValidation(token)) return false;
            // DB에 저장한 토큰 비교
            Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccountEmail(getEmailFromToken(token));
            return refreshToken.isPresent() && token.equals(refreshToken.get().getRefreshToken());
        }

        // 인증 객체 생성
        public Authentication createAuthentication(String email) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            // spring security 내에서 가지고 있는 객체입니다. (UsernamePasswordAuthenticationToken)
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        }

        // 토큰에서 email 가져오는 기능
        public String getEmailFromToken(String token) {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        }

        // 어세스 토큰 헤더 설정
        public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
            response.setHeader("Authorization", "Bearer " + accessToken);
        }

        // 리프레시 토큰 헤더 설정
        public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
            response.setHeader("Authorization", "Bearer " + refreshToken);
        }

    }
