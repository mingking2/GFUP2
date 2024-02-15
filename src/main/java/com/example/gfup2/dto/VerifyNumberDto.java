package com.example.gfup2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyNumberDto {

    @NotBlank(message = "숫자를 입력해야 합니다.")
    @Pattern(regexp = "\\d+", message = "숫자만 입력해야 합니다.")
    @Size(max=30)
    String number;
}

