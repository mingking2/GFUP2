package com.example.gfup2.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import com.example.gfup2.config.JwtProps;
import com.example.gfup2.constants.Constants;

import javax.crypto.SecretKey;
import java.util.*;

public class TokenManager {
    private final SecretKey secretKey;
    private final String issuer;

    public record TokenData(String issuer, Date expiration, String subject, String type, String token, Map<String, String> data){ }

    public TokenManager(JwtProps props){
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(props.getSecretKey()));
        this.issuer = props.getIssuer();
    }

    public String createToken(Date expiration, String userName,  String type, Map<String, String> claims){
        JwtBuilder token = Jwts.builder()
                .issuer(this.issuer)
                .subject(userName)
                .claim(Constants.TOKEN_TYPE, type)
                //.audience(userName)
                .expiration(expiration)
                //.issuedAt(Util.getCurrentDate())
                .signWith(this.secretKey, Jwts.SIG.HS512);

        for (String key : claims.keySet()){
            token.claim(key, claims.get(key));
        }
        return token.compact();
    }

    public String createToken(Date expiration, String userName,  String type){
        return createToken(expiration, userName, type, new HashMap<String,String>());
    }

    public TokenData getTokenDataFromToken(String token, List<String> keys){
        Claims jws= Jwts.parser().verifyWith(this.secretKey).build().parseSignedClaims(token).getPayload();
        String issuer = jws.getIssuer();
        Date expiration = jws.getExpiration();
        String subject = jws.getSubject();
        String type = jws.get(Constants.TOKEN_TYPE,String.class);
        Map<String, String> dt = new HashMap<String, String>();
        for(String key : keys){
            dt.put(key, jws.get(key, String.class));
        }
        return new TokenData(issuer, expiration, subject, type, token, dt);
    }

    public TokenData getTokenDataFromToken(String token){
        return getTokenDataFromToken(token, new ArrayList<>());
    }
}