package com.example.gfup2.web.member.service;

import com.example.gfup2.domain.entity.UserRoleEnum;
import com.example.gfup2.jwt.dto.TokenDto;
import com.example.gfup2.jwt.entity.RefreshToken;
import com.example.gfup2.jwt.repository.RefreshTokenRepository;
import com.example.gfup2.jwt.service.JwtUtil;
import com.example.gfup2.domain.entity.Member;
import com.example.gfup2.repository.MemberRepository;
import com.example.gfup2.web.member.dto.LoginRequestDto;
import com.example.gfup2.web.member.dto.RegisterRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService{
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void register(RegisterRequestDto register) {
        memberRepository.findByemailId(register.getEmailId())
                .ifPresent(user -> {
                    throw new RuntimeException();
                });

        Member member = register.toEntity();
        member.encodePassword(passwordEncoder); // 비밀번호 해시화
        UserRoleEnum role = UserRoleEnum.USER;
        member.setRoles(role);
        memberRepository.save(member);
    }

    public TokenDto login(LoginRequestDto login, HttpServletResponse response){
        log.debug("login");
        // 아이디 검사
        Member member = memberRepository.findByemailId(login.getEmailId()).orElseThrow(
                () -> new RuntimeException("Not found Account")
        );

        // 비밀번호 검사
        if(!passwordEncoder.matches(login.getPassword(), member.getPassword())) {
            throw new RuntimeException("Not matches Password");
        }

        // 아이디 정보로 Token생성
        TokenDto tokenDto = jwtUtil.createAllToken(login.getEmailId()); //jwtUtil.createAllToken(loginReqDto.getNickname());

        // Refresh토큰 있는지 확인
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccountEmail(login.getEmailId());

        // 있다면 새토큰 발급후 업데이트
        // 없다면 새로 만들고 디비 저장
        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), login.getEmailId());
            refreshTokenRepository.save(newToken);
        }
        // response 헤더에 Access Token / Refresh Token 넣음
        setHeader(response, tokenDto);
        return tokenDto;
    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

}
