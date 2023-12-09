package com.example.gfup2.web.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupForm {

    @NotNull
    @Schema(description = "사용자 이메일", nullable = false, example = "k12@gmail.com")
    private String emailId;

    @NotNull
    @Schema(description = "사용자 비밀번호", nullable = false, example = "pwd")
    private String password;

    @NotNull
    @Schema(description = "사용자 핸드폰 번호", nullable = false, example = "010-12-31")
    private String phoneNumber;

    private String adminToken= "";
}
