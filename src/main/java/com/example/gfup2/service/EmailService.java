package com.example.gfup2.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.example.gfup2.exception.VerifyException;
import com.example.gfup2.security.jwt.VerifyTokenProvider;
import com.example.gfup2.util.Util;

@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final VerifyTokenProvider verifyTokenProvider;

    public void sendEmail(String email, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = this.javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        this.javaMailSender.send(message);
    }

    public String sendVerifyNumberByEmail(String email) throws MessagingException {
        String randomNum = Util.getRandomNum();
        sendEmail(email,"GFUP 이메일 인증",
                String.format("<p>인증번호 : <span style=\"color:red\">%s<span></p>", randomNum)
        );
        return this.verifyTokenProvider.getPreAuthenticationToken(email, randomNum);
    }

    public String verify(String token, String number) throws VerifyException{
        VerifyTokenProvider.VerifyData authData = this.verifyTokenProvider.getPreAuthenticationTokenData(token);
        if(number.equals(authData.getVerifyNumber())){
            return this.verifyTokenProvider.getPostAuthenticationToken(authData.getAuthDetails());
        }
        throw new VerifyException("인증번호 일치하지 않음");
    }

    public String getEmailByToken(String token) throws VerifyException{
        VerifyTokenProvider.VerifyData authData = this.verifyTokenProvider.getPostAuthenticationTokenData(token);
        if(authData.getAuthType().equals(VerifyTokenProvider.AuthType.PreAuth.toString())){
            throw new VerifyException("인증되지 않은 이메일");
        }
        return authData.getAuthDetails();
    }
}