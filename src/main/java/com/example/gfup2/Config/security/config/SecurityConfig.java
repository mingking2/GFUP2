package com.example.gfup2.Config.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf((httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())) //내가 찍음
                .formLogin((formLogin) -> formLogin.disable())
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity
                .authorizeHttpRequests(
                authorize ->  authorize
                        .requestMatchers("/member/login").permitAll()
                        .requestMatchers("/member/register").permitAll()
                        .anyRequest().authenticated()
                )
                .build();
        }

}


