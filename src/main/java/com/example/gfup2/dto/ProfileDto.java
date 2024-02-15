package com.example.gfup2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    @NotBlank(message = "이메일은 필수 값입니다.")
    @Email(message="유효한 이메일 주소를 입력해주세요.")
    @Size(min=5, max=254, message="5이상, 64이하 이메일을 입력해주세요.")
    private String emailId;

    @NotBlank(message = "전화번호는 필수 값입니다.")
    @Pattern(regexp="^\\d{3}-\\d{3,4}-\\d{4}$", message="유효한 휴대폰 번호를 입력해주세요.")
    private String phoneNumber;

    @NotBlank(message = "이메일은 필수 값입니다.")
    @Email(message="유효한 이메일 주소를 입력해주세요.")
    @Size(min=5, max=254, message="5이상, 64이하 이메일을 입력해주세요.")
    private String alarmEmailInfo;

    @NotBlank(message = "전화번호는 필수 값입니다.")
    @Pattern(regexp="^\\d{3}-\\d{3,4}-\\d{4}$", message="유효한 휴대폰 번호를 입력해주세요.")
    private String alarmPhoneNumberInfo;

}
