package com.example.gfup2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry
                .addMapping("/auth/**")
                .allowedOrigins("*")
                .allowedMethods("POST");

        registry
                .addMapping("/verify/**")
                .allowedOrigins("*")
                .allowedMethods("POST");

        registry
                .addMapping("/alarm/**")
                .allowedOrigins("*")
                .allowedMethods("POST","GET","PUT","DELETE");

        registry
                .addMapping("/user/**")
                .allowedOrigins("*")
                .allowedMethods("POST","GET","UPDATE","DELETE");
    }
}