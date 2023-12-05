package com.example.gfup2.web.user.service;


import com.example.gfup2.domain.user.entity.User;
import com.example.gfup2.domain.user.entity.UserRoleEnum;
import com.example.gfup2.domain.user.repository.UserRepository;
import com.example.gfup2.web.auth.jwt.JwtUtil;
import com.example.gfup2.web.auth.dto.TokenDto;
import com.example.gfup2.web.user.dto.SigninForm;
import com.example.gfup2.web.user.dto.SignupForm;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;



    public User registerUser(SignupForm form) {
        Optional<User> found = userRepository.findByEmailId(form.getEmailId());
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복중복");
        }

        UserRoleEnum role = UserRoleEnum.USER;

        User user = User.from(form, passwordEncoder.encode(form.getPassword()), role);
        return userRepository.save(user);
    }


    public TokenDto loginUser(SigninForm form) {

        User user = userRepository.findByEmailId(form.getEmailId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않음"));

        //유저가 존재하면
        if (passwordEncoder.matches(form.getPassword(), user.getPassword())) { //패스워드 확인 후 맞으면
            //토큰 발급

            return jwtUtil.generateToken(user.getEmailId());
        }

        throw new IllegalArgumentException("패스워드가 다름");
    }

    public String getHeaders(String authorization) {
        // 인증
        return authorization;
    }

}
