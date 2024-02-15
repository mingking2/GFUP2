package com.example.gfup2.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix="mail")
@PropertySource("classpath:security.properties")
@Setter
public class EmailConfig {
    private String host;
    private int port;
    private String username;
    private String password;
    private boolean smtpAuth;
    private boolean smtpSslEnable;
    //private String smtpSslTrust;
    private boolean smtpStarttlsEnable;

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.host);
        mailSender.setPort(this.port);
        mailSender.setUsername(this.username);
        mailSender.setPassword(this.password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", this.smtpAuth);
        props.put("mail.smtp.ssl.enable", this.smtpSslEnable);
        props.put("mail.smtp.starttls.enable", this.smtpStarttlsEnable);
        //props.put("mail.smtp.ssl.trust", this.smtpSslTrust);

        return mailSender;
    }
}
