package com.example.gfup2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.gfup2.exception.VerifyException;
import com.example.gfup2.security.jwt.VerifyTokenProvider;
import com.example.gfup2.util.Util;

@RequiredArgsConstructor
@Service
public class SmsService {
    private final VerifyTokenProvider verifyTokenProvider;
    public void sendSms(String phoneNumber, String htmlContent){

    }

    public String sendVerifyNumberBySms(String phoneNumber){
        String randomNum = Util.getRandomNum();
        sendSms(phoneNumber, null);
        return this.verifyTokenProvider.getPreAuthenticationToken(phoneNumber, randomNum);
    }

    public String verify(String token, String number) throws VerifyException{
        VerifyTokenProvider.VerifyData authData = this.verifyTokenProvider.getPreAuthenticationTokenData(token);
//        if(number.equals(authData.getVerifyNumber())){
//            return this.verifyTokenProvider.getPostAuthenticationToken(authData.getAuthDetails());
//        }
        return this.verifyTokenProvider.getPostAuthenticationToken(authData.getAuthDetails());
        //throw new VerifyException("인증번호 일치하지 않음");
    }

    public String getPhoneNumberByToken(String token) throws VerifyException{
        VerifyTokenProvider.VerifyData authData = this.verifyTokenProvider.getPostAuthenticationTokenData(token);
        if(authData.getAuthType().equals(VerifyTokenProvider.AuthType.PreAuth.toString())){
            throw new VerifyException("인증되지 않은 번호");
        }
        return authData.getAuthDetails();
    }
}

