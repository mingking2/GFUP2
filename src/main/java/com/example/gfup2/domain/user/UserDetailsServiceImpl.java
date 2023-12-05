package com.example.gfup2.domain.user;

import com.example.gfup2.domain.user.entity.User;
import com.example.gfup2.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userEmailId) throws UsernameNotFoundException {
        User user = userRepository.findByEmailId(userEmailId)
                .orElseThrow(()->new UsernameNotFoundException("user not found"));

        return UserDetailsImpl.from(user);
    }
}
