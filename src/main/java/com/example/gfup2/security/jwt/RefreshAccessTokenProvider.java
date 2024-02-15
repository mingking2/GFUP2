package com.example.gfup2.security.jwt;

import lombok.Getter;
import org.springframework.stereotype.Service;
import com.example.gfup2.config.JwtProps;
import com.example.gfup2.constants.Constants;
import com.example.gfup2.util.Util;

import java.util.Date;

@Service
public class RefreshAccessTokenProvider extends TokenManager {
    private final long refreshTokenExpirationMinutes;
    private final long accessTokenExpirationMinutes;

    public RefreshAccessTokenProvider(JwtProps props){
        super(props);
        this.refreshTokenExpirationMinutes = props.getRefreshTokenExpirationMinutes();
        this.accessTokenExpirationMinutes = props.getAccessTokenExpirationMinutes();
    }

    public TokenInfo getAccessToken(String userName){
            Date accessTokenExpiration = Util.getCurrentDatePlusMinutes(this.accessTokenExpirationMinutes);
            String accessToken = createToken(
                    accessTokenExpiration,
                    userName,
                    Constants.ACCESS_TOKEN
            );
            return new TokenInfo(accessToken, null, accessTokenExpiration.getTime(), null);
    }

    public TokenInfo getToken(String userName){
        Date accessTokenExpiration = Util.getCurrentDatePlusMinutes(this.accessTokenExpirationMinutes);
        Date refreshTokenExpiration = Util.getCurrentDatePlusMinutes(this.refreshTokenExpirationMinutes);
        String accessToken = createToken(
                accessTokenExpiration,
                userName,
                Constants.ACCESS_TOKEN
        );
        String refreshToken = createToken(
                refreshTokenExpiration,
                userName,
                Constants.REFRESH_TOKEN
        );
        return new TokenInfo(accessToken, refreshToken, accessTokenExpiration.getTime(), refreshTokenExpiration.getTime());
    }

    @Getter
    public static class TokenInfo {
        private final String accessToken;
        private final String refreshToken;
        private final Long expirationTimeFromAccessToken;
        private final Long expirationTimeFromRefreshToken;

        private TokenInfo(String accessToken, String refreshToken, Long accessTokenExpiration, Long refreshTokenExpiration){
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.expirationTimeFromAccessToken = accessTokenExpiration;
            this.expirationTimeFromRefreshToken = refreshTokenExpiration;
        }
    }
}
