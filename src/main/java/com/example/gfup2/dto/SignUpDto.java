package com.example.gfup2.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class SignUpDto {
    @NotBlank(message = "이메일은 필수 값입니다.")
    @Email(message="유효한 이메일 주소를 입력해주세요.")
    @Size(min=5, max=254, message="5이상, 64이하 이메일을 입력해주세요.")
    private String email;

    @NotBlank(message="비밀번호를 입력해주세요.")
    // @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$", message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String password;

    @NotBlank(message = "전화번호는 필수 값입니다.")
    @Pattern(regexp="^\\d{3}-\\d{3,4}-\\d{4}$", message="유효한 휴대폰 번호를 입력해주세요.")
    private String phoneNumber;

    @NotBlank(message="이메일 인증 안됨")
    private String emailToken;

    @NotBlank(message="전화번호 인증 안됨")
    private String phoneNumberToken;
}
