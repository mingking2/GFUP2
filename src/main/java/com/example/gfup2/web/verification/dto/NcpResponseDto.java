package com.example.gfup2.web.verification.dto;

import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
public class NcpResponseDto {
    String requestId;
    LocalDateTime requestTime;
    String statusCode;
    String statusName;
}