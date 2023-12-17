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
            return ResponseEntity.ok().build(); //인증번호 전송이 완료되었습니다. 메시지도 같이 보내주면 좋습니다(클라이언트가 그대로 쓰면 됨)
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); //서버 로직 문제일 경우 500 반환
        }
    }

    @PostMapping("/Number")
    public ResponseEntity<String> verifyEmailNumber(@RequestBody @Valid VerifyNumberDto verifyNumberDto) {
        boolean isCorrectNum = verifyMailService.checkVerifyNum(verifyNumberDto);
        if(isCorrectNum){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("인증번호가 다릅니다.");
        }

    }

}
