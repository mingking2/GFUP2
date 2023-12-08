package com.example.gfup2.web.user.controller;

import com.example.gfup2.domain.user.UserDetailsImpl;
import com.example.gfup2.domain.user.entity.User;
import com.example.gfup2.web.auth.jwt.JwtUtil;
import com.example.gfup2.web.auth.dto.TokenDto;
import com.example.gfup2.web.user.dto.SigninForm;
import com.example.gfup2.web.user.dto.SignupForm;
import com.example.gfup2.web.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "User 컨트롤러", description = "USER API입니다.")
@RequiredArgsConstructor
@RestController
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "회원가입", description = "사용자의 정보를 입력받아 회원가입합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "401", description = "인가 기능이 확인되지 않은 접근"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @PostMapping("/auth/signup")
    public ResponseEntity<User> registerUser(@RequestBody @Valid SignupForm form) {
        return ResponseEntity.ok(userService.registerUser(form));
    }

    @Operation(summary = "로그인", description = "아이디와 비밀번호를 입력받아 로그인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "인가 기능이 확인되지 않은 접근"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @PostMapping("/auth/signin")
    public ResponseEntity<TokenDto> loginUser(@RequestBody @Valid SigninForm form) {
        return ResponseEntity.ok(userService.loginUser(form));
    }

    @Operation(summary = "로그아웃", description = "로그아웃합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "401", description = "인가 기능이 확인되지 않은 접근"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @PostMapping("/auth/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        //String accessToken = jwtUtil.parseJwtAccess(request);
        String refreshToken = jwtUtil.parseJwtRefresh(request);
        return userService.logoutUser(refreshToken);
    }

    @Operation(summary = "인가 테스트용", description = "인가상태인지 확인하고 test를 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "테스트 성공"),
            @ApiResponse(responseCode = "401", description = "인가 기능이 확인되지 않은 접근"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().body("test!");
    }

    @Operation(summary = "인증된 아이디와 비밀번호 로그 출력", description = "토큰을 통해 인증된 정보를 확인합니다.")
    @GetMapping("/user/search")
    public void searchUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("emailId: " + userDetails.getUsername());
        log.info("pwd: " + userDetails.getPassword());
    }

    @Operation(summary = "요청 헤더 확인", description = "요청 헤더에서 인증상태를 확인한다.")
    @GetMapping("/get-header")
    public String getHeader(@RequestHeader("Authorization") String authorization) {
        return userService.getHeaders(authorization);
    }


}
