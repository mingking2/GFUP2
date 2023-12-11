package com.example.gfup2.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mail")
public class AdminMailConfig {

    private String host;
    private int port;
    private String adminmail;
    private String password;
    private boolean smtpAuth;
    private boolean smtpSslEnable;
    private String smtpSslTrust;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(adminmail);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.ssl.enable", smtpSslEnable);
        props.put("mail.smtp.ssl.trust", smtpSslTrust);

        return mailSender;
    }
}
