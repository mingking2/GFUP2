package com.example.gfup2.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.gfup2.security.NoEncodingPasswordEncoder;
import com.example.gfup2.security.jwt.JwtAuthEntryPoint;
import com.example.gfup2.security.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
        return new NoEncodingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests->
                        requests
                                .requestMatchers("/static/**").permitAll()
                                .requestMatchers("/*").permitAll()
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/verify/**").permitAll()
                                .requestMatchers(
                                        "/swagger-resources/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs",
                                        "/api-docs/**"
                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(config->
                        config.authenticationEntryPoint(this.jwtAuthEntryPoint)
                )
                .sessionManagement(it->
                        it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
