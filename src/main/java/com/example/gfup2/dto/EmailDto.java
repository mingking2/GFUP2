package com.example.gfup2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailDto { //인증번호 보내기 버튼 클릭시(인증번호확인 버튼이 아님) 이메일은 바로 잠금 되게 프론트에서 조정하기

    @Column(nullable=false)
    @NotBlank(message = "이메일은 필수 값입니다.")
    @Email(message = "유효한 이메일 주소를 입력해주세요")
    @Size(min=2, max=64, message = "2이상 64이하 이메일을 입력해주세요.")
    @Schema(description = "사용자 이메일", nullable = false, example = "k12@gmail.com")
    private String email;

}
