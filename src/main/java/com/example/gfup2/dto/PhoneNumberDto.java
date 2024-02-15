package com.example.gfup2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberDto {
    @NotBlank(message = "전화번호는 필수 값입니다.")
    @Pattern(regexp="^\\d{3}-\\d{3,4}-\\d{4}$", message="유효한 휴대폰 번호를 입력해주세요.")
    private String phoneNumber;
}
