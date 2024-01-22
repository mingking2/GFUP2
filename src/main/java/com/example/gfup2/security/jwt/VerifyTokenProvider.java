package com.example.gfup2.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import com.example.gfup2.config.JwtProps;
import com.example.gfup2.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VerifyTokenProvider extends TokenManager{
    private final long authTokenExpirationMinutes;
    private final long registerTokenExpirationMinutes;

    public  VerifyTokenProvider(JwtProps props){
        super(props);
        this.authTokenExpirationMinutes = props.getAuthTokenExpirationMinutes();
        this.registerTokenExpirationMinutes = props.getRegisterTokenExpirationMinutes();
    }

    public String getPreAuthenticationToken(String authDetails, String verifyNumber){
        Map<String, String> data = new HashMap<String, String>();
        data.put("vnm", verifyNumber);
        return createToken(
                Util.getCurrentDatePlusMinutes(this.authTokenExpirationMinutes),
                authDetails,
                AuthType.PreAuth.toString(),
                data
        );
    }

    public String getPostAuthenticationToken(String authDetails){
        return createToken(
                Util.getCurrentDatePlusMinutes(this.registerTokenExpirationMinutes),
                authDetails,
                AuthType.PostAuth.toString()
        );
    }

    public VerifyData getPreAuthenticationTokenData(String token){
        List<String> keys = new ArrayList<String>();
        keys.add("vnm");
        TokenData dt =  getTokenDataFromToken(token, keys);
        return new VerifyData(dt.subject(), dt.type(), dt.data().get("vnm"));
    }

    public VerifyData getPostAuthenticationTokenData(String token){
        TokenData dt = getTokenDataFromToken(token);
        return new VerifyData(dt.subject(), dt.type(), null);
    }

    @Getter
    @AllArgsConstructor
    public static class VerifyData{
        private String authDetails;
        private String authType;
        private String verifyNumber;
    }
    public enum AuthType{
        PreAuth, PostAuth
    }
}
