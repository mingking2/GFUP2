package com.example.gfup2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VerifyNumberDto {

    @NotBlank(message = "숫자를 입력해야 합니다.")
    @Pattern(regexp = "\\d+", message = "숫자만 입력해야 합니다.")
    String number;

}
