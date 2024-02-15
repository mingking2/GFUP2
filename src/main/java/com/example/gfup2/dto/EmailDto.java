package com.example.gfup2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {
    @NotBlank(message = "이메일은 필수 값입니다.")
    @Email(message="유효한 이메일 주소를 입력해주세요.")
    @Size(min=5, max=254, message="5이상, 64이하 이메일을 입력해주세요.")
    private String email;
}

