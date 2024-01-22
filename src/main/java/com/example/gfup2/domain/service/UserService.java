package com.example.gfup2.domain.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.gfup2.domain.model.User;
import com.example.gfup2.domain.repository.UserRepository;
import com.example.gfup2.exception.EmailException;
import com.example.gfup2.exception.LogInException;
import com.example.gfup2.exception.VerifyException;
import com.example.gfup2.security.UserDetailsImpl;
import com.example.gfup2.security.jwt.RefreshAccessTokenProvider;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RefreshAccessTokenProvider refreshAccessTokenProvider;

    public void registerUser(String emailId, String phoneNumber, String password) throws EmailException {
        password = this.passwordEncoder.encode(password);

        Optional<User> found = this.userRepo.findByEmailId(emailId);
        if(found.isPresent()) throw new EmailException("이메일 중복");

        this.userRepo.save(new User(
                emailId,
                password,
                phoneNumber,
                emailId,
                phoneNumber
        ));
    }

    public RefreshAccessTokenProvider.TokenInfo logIn(String emailId, String password) throws LogInException{
        password = this.passwordEncoder.encode(password);
        log.info("{} {} 로그인 시도",emailId, password);

        Optional<User> found = this.userRepo.findByEmailId(emailId);
        if(found.isEmpty() || !this.passwordEncoder.matches(password, found.get().getPassword())) throw new LogInException("일치하는 회원정보 없음");

        return this.refreshAccessTokenProvider.getToken(emailId);
    }

    public User getAuthUserFromSecurityContextHolder() throws VerifyException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> found = this.userRepo.findByEmailId(auth.getName());
        if(found.isEmpty()) throw new VerifyException("잘못된 요청");
        return found.get();
    }


}
