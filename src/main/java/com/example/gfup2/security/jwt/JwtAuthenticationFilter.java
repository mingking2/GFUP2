package com.example.gfup2.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.gfup2.constants.Constants;
import com.example.gfup2.security.UserDetailsServiceImpl;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Order(0)
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final RefreshAccessTokenProvider refreshAccessTokenProvider;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            Optional<String> token = Optional.ofNullable(this.extractToken(request));
            if(token.isPresent()){
                RefreshAccessTokenProvider.TokenData tokenData = this.refreshAccessTokenProvider.getTokenDataFromToken(token.get());
                String userName = tokenData.subject();
                this.authentication(userName);
                if (tokenData.type().equals(Constants.REFRESH_TOKEN)){
                    RefreshAccessTokenProvider.TokenInfo tokenInfo = this.refreshAccessTokenProvider.getAccessToken(userName);
                    response.addHeader(
                            Constants.ACCESS_TOKEN,
                            tokenInfo.getAccessToken()
                    );
                    response.addHeader(
                            Constants.ACCESS_TOKEN_EXPIRATION,
                            tokenInfo.getExpirationTimeFromAccessToken().toString()
                    );
                }
            }
        }catch (ExpiredJwtException e){
            // 토큰 만료 시 처리
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token expired\"}");
            return;
        }catch (JwtException e){
            // 그 외 JWT 오류 시 처리
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Invalid token\"}");
            return;
        }catch (Exception e){
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Internal server error\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }

    public void authentication(String username){
        UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(username);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        username, null,
                        userDetails.getAuthorities()
                )
        );
    }

    public String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

