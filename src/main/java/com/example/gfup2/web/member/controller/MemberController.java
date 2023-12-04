package com.example.gfup2.web.member.controller;

import com.example.gfup2.web.member.dto.LoginRequestDto;
import com.example.gfup2.web.member.dto.RegisterRequestDto;
import com.example.gfup2.web.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@Tag(name = "MemberController", description = "사용자의 회원가입 및 로그인과 관련된 컨트롤러")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

   @PostMapping("/register")
    public ResponseEntity<RegisterRequestDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
       try {
           memberService.register(registerRequestDto);
           return new ResponseEntity<>(HttpStatus.OK);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }

   @PostMapping("/login")
    public ResponseEntity<LoginRequestDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
       try{
           memberService.login(loginRequestDto, response);
       } catch (Exception e){
           e.printStackTrace();
       }
       return null;
   }

}

