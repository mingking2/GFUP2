package com.example.gfup2.web.auth.jwt;

import com.example.gfup2.domain.user.UserDetailsServiceImpl;
import com.example.gfup2.web.auth.dto.TokenDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
@Slf4j
public class jwtFilter extends OncePerRequestFilter {


    private JwtUtil jwtUtil;
    private UserDetailsServiceImpl userDetailsService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (!isAuthenticationRequired(uri)) {
            handleAuthentication(request, response);
        }


        filterChain.doFilter(request, response);
    }

    private boolean isAuthenticationRequired(String uri) {
        return uri.equals("/auth/signup") || uri.equals("/auth/signin");
    }

    private void handleAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = jwtUtil.parseJwtAccess(request);
        log.info(accessToken);
        String refreshToken = jwtUtil.parseJwtRefresh(request);

        if (accessToken != null && jwtUtil.isTokenValid(accessToken)) {
            authenticateWithToken(accessToken);
        } else if (jwtUtil.refreshTokenValidation(refreshToken)) {
            handleRefreshToken(refreshToken, response);
        }
    }

    private void authenticateWithToken(String accessToken) {
        String username = jwtUtil.getUsername(accessToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        log.info("authenticated user with username: {}", username);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private void handleRefreshToken(String refreshToken, HttpServletResponse response) {
        String loginEmail = jwtUtil.getUsername(refreshToken);
        TokenDto tokenDto = jwtUtil.generateToken(loginEmail);
        response.setHeader("accessToken", tokenDto.getAccessToken());
        response.setHeader("refreshToken", tokenDto.getRefreshToken());
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginEmail);
        authenticateWithToken(tokenDto.getAccessToken());
        log.info("refreshed and authenticated user with username: {}", loginEmail);
    }

}
