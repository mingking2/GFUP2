package com.example.gfup2.service;

import com.example.gfup2.config.EmailConfig;
import com.example.gfup2.dto.EmailDto;
import com.example.gfup2.dto.VerifyNumberDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import static com.example.gfup2.util.MakeRandomNum.makeRandomNum;

@Service
@Slf4j
@RequiredArgsConstructor
public class VerifyMailService {
    private final JavaMailSender mailSender;
    private final EmailConfig emailConfig;
    private final String VERIFYNUM = makeRandomNum();

    public void sendMail(EmailDto emailDto) throws MessagingException {
        //MimeMessage는 이메일 메시지를 나타내는 클래스로, 이메일의 제목, 본문, 수신자, 발신자, 첨부 파일 등을 설정할 수 있음.
        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper h = new MimeMessageHelper(mail, "UTF-8");
        h.setFrom(emailConfig.getAdminmail()); // 앞서 설정한 본인의 Naver Email. 발신메일
        String setToMail = emailDto.getEmail(); // 클라이언트의 이메일
        h.setTo(setToMail);

        // 이메일 제목 설정
        h.setSubject("GFUP 인증번호 6자리 입니다.");
        //이메일 내용 설정
        h.setText(VERIFYNUM);
        //이메일 보내기
        mailSender.send(mail);
    }

    public boolean checkVerifyNum(VerifyNumberDto verifyNumberDto) {
        String ClientNum = verifyNumberDto.getNumber();
        if (ClientNum.equals(VERIFYNUM)) {
            return true;
        }
        return false;
    }

}
