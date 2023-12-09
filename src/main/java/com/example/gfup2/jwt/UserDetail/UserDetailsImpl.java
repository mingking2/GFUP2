package com.example.gfup2.jwt.UserDetail;

import com.example.gfup2.domain.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private Member member;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl from(Member member) {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(member.getRoles().toString());
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(simpleGrantedAuthority);

        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.member = member;
        userDetails.authorities = collection;

        return userDetails;
    }

    public Member getAccount() {
        return this.member;
    }

    public void setAccount(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmailId(); // 사용자의 이메일을 반환하도록 수정
    }

    @Override
    public boolean isAccountNonExpired() {
        // 사용자 계정이 만료되지 않았는지 여부를 반환하도록 수정
        return true; // 예시로 모두 true로 설정
    }

    @Override
    public boolean isAccountNonLocked() {
        // 사용자 계정이 잠겨있지 않은지 여부를 반환하도록 수정
        return true; // 예시로 모두 true로 설정
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 사용자의 인증 정보가 만료되지 않았는지 여부를 반환하도록 수정
        return true; // 예시로 모두 true로 설정
    }

    @Override
    public boolean isEnabled() {
        // 사용자 계정이 활성화되었는지 여부를 반환하도록 수정
        return true; // 예시로 모두 true로 설정
    }
}