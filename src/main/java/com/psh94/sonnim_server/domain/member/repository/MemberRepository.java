package com.psh94.sonnim_server.domain.member.repository;

import com.psh94.sonnim_server.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository  extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);        // 이메일 중복 여부 확인
    Optional<Member> findByEmail(String email); // 이메일로 회원 조회
    void deleteByEmail(String email);           // 이메일로 회원 삭제
}
