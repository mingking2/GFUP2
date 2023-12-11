package com.example.gfup2.web.verification.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyNumberDto {

    @NotBlank(message = "숫자를 입력해야 합니다.")
    @Pattern(regexp = "\\d+", message = "숫자만 입력해야 합니다.")
    String number;

}
