package com.example.gfup2.domain.model;


import com.example.gfup2.dto.SignupForm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "사용자 이메일", nullable = false, example = "k12@gmail.com")
    private String emailId;

    @Column(nullable = false)
    @Schema(description = "사용자 비밀번호", nullable = false, example = "pwd")
    private String password;

    @Column(nullable = false, unique = true)
    @Schema(description = "사용자 핸드폰 번호", nullable = false, example = "010-12-31")
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    @Schema(description = "사용자 알람 이메일", nullable = false, example = "k12@gmail.com")
    private String alarmEmailInfo;

    @Column(nullable = false, unique = true)
    @Schema(description = "사용자 알람 핸드폰 번호", nullable = false, example = "010-12-31")
    private String alarmPhoneNumberInfo;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public static User from(SignupForm form, String password, UserRoleEnum role){
        User user = new User();
        user.emailId = form.getEmailId();
        user.password = password;
        user.phoneNumber = form.getPhoneNumber();
        user.alarmEmailInfo = form.getEmailId();
        user.alarmPhoneNumberInfo = form.getPhoneNumber();
        user.role = role;
        return user;
    }




}
