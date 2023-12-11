package com.example.gfup2.web.verification.controller;

import com.example.gfup2.web.verification.dto.EmailDto;
import com.example.gfup2.web.verification.dto.VerifyNumberDto;
import com.example.gfup2.web.verification.service.VerifyMailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "verifyMail 컨트롤러", description = "이메일 인증 API 입니다.")
@RestController
@RequestMapping("/verify/Mail")
@Slf4j
public class VerifyMailController {
    private final VerifyMailService verifyMailService;

    public VerifyMailController(VerifyMailService verifyMailService) {
        this.verifyMailService = verifyMailService;
    }

    @PostMapping
    public ResponseEntity<Void> verifyEmail(@RequestBody @Valid EmailDto emailDto) {
        try {
            verifyMailService.sendMail(emailDto);
            return ResponseEntity.ok().build();
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); //서버 로직 문제일 경우 500 반환
        }
    }

    @PostMapping("/Number")
    public ResponseEntity<Void> verifyEmailNumber(@RequestBody @Valid VerifyNumberDto verifyNumberDto) {
        try {
            verifyMailService.checkVerifyNum(verifyNumberDto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //인증틀릴경우 400 반환
        }
    }

}
