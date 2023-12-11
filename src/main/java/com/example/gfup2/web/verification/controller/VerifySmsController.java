package com.example.gfup2.web.verification.controller;

import com.example.gfup2.web.verification.dto.SmsDto;
import com.example.gfup2.web.verification.dto.VerifyNumberDto;
import com.example.gfup2.web.verification.service.VerifySmsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Tag(name = "verifySms 컨트롤러", description = "전화번호 인증 API 입니다.")
@RestController
@RequestMapping("/verify/Sms")
@Slf4j
public class VerifySmsController {
    private final VerifySmsService verifySmsService;

    public VerifySmsController(VerifySmsService verifySmsService) {
        this.verifySmsService = verifySmsService;
    }

    @PostMapping
    public ResponseEntity<Void> verifySms(@RequestBody @Valid SmsDto smsDto) {
        try {
            verifySmsService.sendSms(smsDto);
            return ResponseEntity.ok().build();
        } catch (UnsupportedEncodingException | URISyntaxException | NoSuchAlgorithmException | InvalidKeyException |
                 JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); //서버 로직 문제일 경우 500 반환
        }
    }

    @PostMapping("/Number")
    public ResponseEntity<Void> verifySmsNumber(@RequestBody @Valid VerifyNumberDto verifyNumberDto) {
        try {
            verifySmsService.checkVerifyNum(verifyNumberDto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //인증틀릴경우 400 반환
        }
    }

}
