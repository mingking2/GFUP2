package com.example.gfup2.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix="gfup.security.jwt")
@Getter
@Setter
@Validated
public class JwtProps {
    @Size(min=60, message="Secret Key must be at least 60 characters long")
    private String secretKey;

    @Min(value=10, message="Refresh_Token_Expiration_Minute must be at least 10 minuts long")
    private long refreshTokenExpirationMinutes;

    @Min(value=10, message="Access_Token_Expiration_Minute must be at least 10 minuts long")
    private long accessTokenExpirationMinutes;

    @Min(value=1, message="Access_Token_Expiration_Minute must be at least 10 minuts long")
    private long authTokenExpirationMinutes;

    @Min(value=5, message="Access_Token_Expiration_Minute must be at least 10 minuts long")
    private long registerTokenExpirationMinutes;

    @NotBlank
    private String issuer;
}
