package com.example.gfup2.web.verification.controller;

import com.example.gfup2.web.verification.dto.EmailDto;
import com.example.gfup2.web.verification.service.verificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "verify 컨트롤러", description = "이메일 및 전화번호 인증 API 입니다.")
@RestController
@Slf4j
public class VerificationController {
    private final JavaMailSender mailSender;
    private final verificationService vefifyService;

    public VerificationController(JavaMailSender mailSender, verificationService vefifyService) {
        this.mailSender = mailSender;
        this.vefifyService = vefifyService;
    }

    @PostMapping("/verifyMail")
    public ResponseEntity<EmailDto> emailValidate(@RequestBody @Valid EmailDto emailDto){
        try{
            vefifyService.mailCreate(emailDto);
            return ResponseEntity.ok().build();
        } catch (MessagingException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
}

    @PostMapping("/verifyPhoneNum")
    public void phoneValidate(HttpSession httpSession){

    }
}
