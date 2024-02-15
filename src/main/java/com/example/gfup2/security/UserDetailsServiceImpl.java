package com.example.gfup2.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.gfup2.domain.model.User;
import com.example.gfup2.domain.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user_ = userRepository.findByEmailId(username)
                .orElseThrow(()->new UsernameNotFoundException("user not found"));
        return new UserDetailsImpl(user_);
    }
}
