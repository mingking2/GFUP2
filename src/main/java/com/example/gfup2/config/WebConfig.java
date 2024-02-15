package com.example.gfup2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry
                .addMapping("/api/auth/**")
                .allowedOrigins("*")
                .allowedMethods("POST");

        registry
                .addMapping("/api/verify/**")
                .allowedOrigins("*")
                .allowedMethods("POST");

        registry
                .addMapping("/api/alarm/**")
                .allowedOrigins("*")
                .allowedMethods("POST","GET","PUT","DELETE");

        registry
                .addMapping("/api/user/**")
                .allowedOrigins("*")
                .allowedMethods("POST","GET","UPDATE","DELETE");
        registry
                .addMapping("/")
                .allowedOrigins("*")
                .allowedMethods("GET");
        registry
                .addMapping("/index.html")
                .allowedOrigins("*")
                .allowedMethods("GET");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver(){
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        if (resourcePath.startsWith("/api/")) return null;

                        Resource requestedResource = location.createRelative(resourcePath);
                        return requestedResource.exists() && requestedResource.isReadable() ? requestedResource : new ClassPathResource("/static/index.html");
                    }
                });
    }
}