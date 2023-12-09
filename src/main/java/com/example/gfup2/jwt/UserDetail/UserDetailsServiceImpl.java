package com.example.gfup2.jwt.UserDetail;

import com.example.gfup2.domain.entity.Member;
import com.example.gfup2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByemailId(email)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found Account"));

        return UserDetailsImpl.from(member);
    }
}