package com.example.gfup2.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.gfup2.domain.model.User;
import com.example.gfup2.domain.service.UserService;
import com.example.gfup2.dto.ProfileDto;
import com.example.gfup2.exception.VerifyException;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> userProfile(){
        try{
            User user = this.userService.getAuthUserFromSecurityContextHolder();

            return new ResponseEntity<ProfileDto>(
                    new ProfileDto(
                            user.getEmailId(),
                            user.getPhoneNumber(),
                            user.getAlarmEmailInfo(),
                            user.getAlarmPhoneNumberInfo()
                    ),
                    HttpStatus.OK
            );
        }catch(VerifyException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/profileUpload")
    public ResponseEntity<String> userProfileUpload(){
        return null;
    }

    @PostMapping("/changePhoneNumber")
    public ResponseEntity<?> userChangePhoneNum(){return null;}
}
