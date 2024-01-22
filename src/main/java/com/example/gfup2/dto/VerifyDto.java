package com.example.gfup2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerifyDto {

    private String token;
    @NotBlank(message = "숫자를 입력해야 합니다.")
    @Pattern(regexp = "\\d+", message = "숫자만 입력해야 합니다.")
    @Size(max=30)
    String number;
}
