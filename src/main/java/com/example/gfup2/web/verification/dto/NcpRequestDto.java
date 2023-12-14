package com.example.gfup2.web.verification.dto;

import lombok.*;
import java.util.List;

//java 객체를 json으로 변환시, getter 및 setter 필요
@Getter
@Setter
//객체생성 및 빌드패턴 생성 시 필요
@AllArgsConstructor
@Builder
public class NcpRequestDto {
    String type;
    String contentType;
    String countryCode;
    String from;
    String content;
    List<MessageDto> messages;
}