package com.example.gfup2.web.member.dto;

import com.example.gfup2.domain.entity.Member;
import com.example.gfup2.domain.entity.UserRoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // Jackson 라이브러리는 JSON 문자열을 자바 객체로 변환(deserialization)할 때 기본 생성자를 사용.
                   // Jackson이 JSON 문자열을 RegisterRequestDto 객체로 변환하려면 RegisterRequestDto 클래스에 기본 생성자가 필요.
                   // 해당 애노테이션이 기본생성자를 제공해 Jackson이 RegisterRequestDto의 객체를 생성하도록 해줌
@AllArgsConstructor //모든 필드를 매개변수로 받는 생성자. 해당 애노테이션으론 Jackson 이용 불가능
@Builder
public class RegisterRequestDto {

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

    @Column(nullable=false)
    @Size(min=9, max=9, message="입력값은 9자리여야 합니다.")
    private String phoneNumber;

    @Column(nullable=false)
    @NotBlank(message = "이메일은 필수 값입니다.")
    @Email(message = "유효한 이메일 주소를 입력해주세요")
    @Size(min=2, max=64, message = "2이상 64이하 이메일을 입력해주세요.")
    private String alarmEmailInfo;

    @Column(nullable=false)
    @Size(min=9, max=9, message="입력값은 9자리여야 합니다.")
    private String alarmPhoneNumberInfo;


    @Builder
    public Member toEntity(){ //해당 Dto를 entity로 변환
        return Member.builder()
                .emailId(emailId)
                .password(password)
                .phoneNumber(phoneNumber)
                .alarmEmailInfo(alarmEmailInfo)
                .alarmPhoneNumberInfo(alarmPhoneNumberInfo)
                .build();
    }

}
