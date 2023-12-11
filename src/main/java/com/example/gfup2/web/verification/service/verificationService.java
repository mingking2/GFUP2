package com.example.gfup2.web.verification.service;

import com.example.gfup2.config.AdminConfig;
import com.example.gfup2.web.verification.dto.EmailDto;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class verificationService {
    private final JavaMailSender mailSender;
    private final AdminConfig adminConfig;

    @Autowired
    public verificationService(JavaMailSender mailSender, AdminConfig adminConfig) {
        this.mailSender = mailSender;
        this.adminConfig = adminConfig;
    }

    private String makeRandomNum(){
        Random random = new Random();
        int randomNum = random.nextInt(900000) + 100000;

        return String.valueOf(randomNum);
    }

    public void mailCreate(EmailDto emailDto) throws MessagingException {
        log.debug("Host: " + adminConfig.getHost());
        log.debug("Port: " + adminConfig.getPort());
        log.debug("AdminMail: " + adminConfig.getAdminmail());
        log.debug("Password: " + adminConfig.getPassword());
        log.debug("SMTP Auth: " + adminConfig.isSmtpAuth());
        log.debug("SMTP SSL Enable: " + adminConfig.isSmtpSslEnable());
        log.debug("SMTP SSL Trust: " + adminConfig.getSmtpSslTrust());

        //MimeMessage는 이메일 메시지를 나타내는 클래스로, 이메일의 제목, 본문, 수신자, 발신자, 첨부 파일 등을 설정할 수 있음.
        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper h = new MimeMessageHelper(mail, "UTF-8");
        h.setFrom(adminConfig.getAdminmail()); // 앞서 설정한 본인의 Naver Email. 발신메일
        String setToMail = emailDto.getEmail(); // 클라이언트의 이메일
        h.setTo(setToMail);

        // 이메일 제목 설정
        h.setSubject("GFUP 인증번호 6자리 입니다.");

        String VerificationNumber = makeRandomNum();
        h.setText(VerificationNumber);

        mailSender.send(mail);
    }

}
