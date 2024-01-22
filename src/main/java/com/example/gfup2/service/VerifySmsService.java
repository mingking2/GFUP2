package com.example.gfup2.service;

import com.example.gfup2.config.AdminSmsConfig;
import com.example.gfup2.dto.MessageDto;
import com.example.gfup2.dto.NcpRequestDto;
import com.example.gfup2.dto.NcpResponseDto;
import com.example.gfup2.dto.VerifyNumberDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.example.gfup2.util.MakeRandomNum.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class VerifySmsService {
    private final AdminSmsConfig adminSmsConfig;
    private final String VERIFYNUM = makeRandomNum();

    public String makeSignature(Long time) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/" + adminSmsConfig.getServiceId() + "/messages";
        String timestamp = time.toString();
        String accessKey = adminSmsConfig.getAccessKey();
        String secretKey = adminSmsConfig.getSecretKey();

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);

        return encodeBase64String;
    }

    public void sendSms(SmsDto smsDto) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Long time = System.currentTimeMillis();
        String accessKey = adminSmsConfig.getAccessKey();
        String phone = adminSmsConfig.getAdminphone();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time.toString());
        headers.set("x-ncp-iam-access-key", accessKey);
        headers.set("x-ncp-apigw-signature-v2", makeSignature(time));

        MessageDto messageDto = new MessageDto(); // 메시지 생성
        messageDto.setTo(smsDto.getPhoneNumber());

        List<MessageDto> messages = new ArrayList<>();
        messages.add(messageDto);

        String content = "GUFP 인증번호:" + VERIFYNUM;

        NcpRequestDto request = NcpRequestDto.builder()
                .type("SMS")
                .contentType("COMM")
                .countryCode("82")
                .from(phone)
                .content(content)
                .messages(messages)
                .build();

            ObjectMapper objectMapper = new ObjectMapper();
            String body = objectMapper.writeValueAsString(request);
            HttpEntity<String> httpBody = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + adminSmsConfig.getServiceId() + "/messages"), httpBody, NcpResponseDto.class);
    }

    public boolean checkVerifyNum(VerifyNumberDto verifyNumberDto) {
        String ClientNum = verifyNumberDto.getNumber();
        if (ClientNum.equals(VERIFYNUM)) {
            return true;
        }
        return false;
    }

}
