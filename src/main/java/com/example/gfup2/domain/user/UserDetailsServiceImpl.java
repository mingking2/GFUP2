package com.example.gfup2.domain.user;

import com.example.gfup2.domain.user.entity.User;
import com.example.gfup2.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userEmailId) throws UsernameNotFoundException {
        User user = userRepository.findByEmailId(userEmailId)
                .orElseThrow(()->new UsernameNotFoundException("user not found"));

        return UserDetailsImpl.from(user);
    }
}
