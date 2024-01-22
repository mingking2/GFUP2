package com.example.gfup2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupForm {

    @Column(nullable=false)
    @NotBlank(message = "이메일은 필수 값입니다.")
    @Email(message = "유효한 이메일 주소를 입력해주세요")
    @Size(min=2, max=64, message = "2이상 64이하 이메일을 입력해주세요.")
    @Schema(description = "사용자 이메일", nullable = false, example = "k12@gmail.com")
    private String emailId;

    @Column(nullable=false)
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
            message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    @Schema(description = "사용자 비밀번호", nullable = false, example = "pwd")
    private String password;

    @Column(nullable = false)
    @Size(min=11, max=11, message="입력값은 11자리여야 합니다.")
    @Schema(description = "사용자 핸드폰 번호", nullable = false, example = "010-12-31")
    private String phoneNumber;

}
