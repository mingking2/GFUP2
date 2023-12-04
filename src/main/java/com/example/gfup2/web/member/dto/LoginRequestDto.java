package com.example.gfup2.web.member.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class LoginRequestDto {


    @Column(nullable=false)
    @NotBlank(message = "이메일은 필수 값입니다.")
    @Email(message = "유효한 이메일 주소를 입력해주세요")
    @Size(min=2, max=64, message = "2이상 64이하 이메일을 입력해주세요.")
    private String emailId;

    @Column(nullable=false)
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
            message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String password;

}
