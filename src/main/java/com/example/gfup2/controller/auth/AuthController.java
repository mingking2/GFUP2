package com.example.gfup2.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.gfup2.domain.service.UserService;
import com.example.gfup2.dto.SignInDto;
import com.example.gfup2.dto.SignUpDto;
import com.example.gfup2.exception.CheckError;
import com.example.gfup2.exception.ValidException;
import com.example.gfup2.security.jwt.RefreshAccessTokenProvider;
import com.example.gfup2.service.EmailService;
import com.example.gfup2.service.SmsService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final EmailService emailService;
    private final SmsService smsService;

    @PostMapping("/signup")
    public ResponseEntity<?> authSignup(@RequestBody @Valid SignUpDto dt, BindingResult bindingResult){
        try{
            CheckError.checkValidException(bindingResult);

            String email = dt.getEmail();
            String phoneNumber = dt.getPhoneNumber();
            String password = dt.getPassword();
            String emailToken = dt.getEmailToken();
            String phoneNumberToken = dt.getPhoneNumberToken();

            if (!email.equals(emailService.getEmailByToken(emailToken))){
                return new ResponseEntity<String>("입력된 이메일과 인증된 이메일이 다름", HttpStatus.BAD_REQUEST);
            }
            if (!phoneNumber.equals(smsService.getPhoneNumberByToken(phoneNumberToken))){
                return new ResponseEntity<String>("입력된 전화번호와 인증된 이메일이 다름", HttpStatus.BAD_REQUEST);
            }
            this.userService.registerUser(email, phoneNumber, password);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(ValidException e){
            return new ResponseEntity<Map<String, String>>(e.getErrors(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authSignIn(@RequestBody @Valid SignInDto dt, BindingResult bindingResult){
        try{
            CheckError.checkValidException(bindingResult);
            return new ResponseEntity<RefreshAccessTokenProvider.TokenInfo>(
                    this.userService.logIn(dt.getEmail(), dt.getPassword()),HttpStatus.OK
            );
        }catch(ValidException e){
            return new ResponseEntity<Map<String, String>>(e.getErrors(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
