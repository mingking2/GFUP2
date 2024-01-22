package com.example.gfup2.controller.verify;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.gfup2.dto.PhoneNumberDto;
import com.example.gfup2.dto.TokenDto;
import com.example.gfup2.dto.VerifyDto;
import com.example.gfup2.exception.CheckError;
import com.example.gfup2.exception.ValidException;
import com.example.gfup2.service.SmsService;

import java.util.Map;

@RestController
@RequestMapping("/verify/sms")
@RequiredArgsConstructor
public class SmsController {
    private final SmsService smsService;
    @PostMapping
    public ResponseEntity<?> verifySms(@RequestBody @Valid PhoneNumberDto dt, BindingResult bindingResult){
        try{
            CheckError.checkValidException(bindingResult);
            return new ResponseEntity<TokenDto>(
                    new TokenDto(this.smsService.sendVerifyNumberBySms(dt.getPhoneNumber())),
                    HttpStatus.OK
            );
        }catch(ValidException e){
            return new ResponseEntity<Map<String, String>>(e.getErrors(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/number")
    public ResponseEntity<?> verifySmsNumber(@RequestBody @Valid VerifyDto dt, BindingResult bindingResult){
        try{
            CheckError.checkValidException(bindingResult);
            return new ResponseEntity<TokenDto>(
                    new TokenDto(this.smsService.verify(dt.getToken(), dt.getNumber())),
                    HttpStatus.OK
            );
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

