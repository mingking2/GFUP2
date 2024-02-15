package com.example.gfup2.security;

import org.springframework.security.crypto.password.PasswordEncoder;

@Deprecated
public class NoEncodingPasswordEncoder implements PasswordEncoder {
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword){
        return encodedPassword.equals(rawPassword.toString());
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }
}
