package com.example.gfup2.web.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TokenDto {

    private String accessToken;
    private String refreshToken;
    private long accessTokenExpiresIn;
}