package com.example.gfup2.domain.member.repository;

import com.example.gfup2.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long memberId);
    Member save(Member member);
    Optional<Member> findByemailId(String email);

}