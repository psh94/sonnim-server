package com.psh94.sonnim_server.domain.member.service;

import com.psh94.sonnim_server.common.auth.CheckRole;
import com.psh94.sonnim_server.common.auth.jwt.CustomUserDetails;
import com.psh94.sonnim_server.common.converter.MemberConverter;
import com.psh94.sonnim_server.common.exception.EmailAlreadyExistsException;
import com.psh94.sonnim_server.common.exception.MemberNotFoundException;
import com.psh94.sonnim_server.common.exception.PasswordMismatchException;
import com.psh94.sonnim_server.domain.member.dto.DeleteMemberRequest;
import com.psh94.sonnim_server.domain.member.dto.MemberDTO;
import com.psh94.sonnim_server.domain.member.dto.SignUpRequest;
import com.psh94.sonnim_server.domain.member.entity.Member;
import com.psh94.sonnim_server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public MemberDTO createMember(SignUpRequest signUpRequest) {
        validateDuplicatedEmail(signUpRequest.getEmail());              // 이메일 중복 검사
        signUpRequest.encodePassword(passwordEncoder);                  // 비밀번호암호화

        Member memberEntity = MemberConverter.toEntity(signUpRequest);  // DTO to Entity
        Member savedMember = memberRepository.save(memberEntity);       // 회원저장
        return MemberConverter.toDTO(savedMember);                      // Entity to DTO
    }

    private void validateDuplicatedEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("이미 존재하는 이메일입니다.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDTO findMemberByAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 현재 인증된 회원의 인증 객체
        String loginedMemberEmail = getCurrentUserEmail(authentication);                        // 인증 객체의 이메일 반환

        Member foundMember = memberRepository.findByEmail(loginedMemberEmail)                   // 해당 이메일로 조회
                .orElseThrow(()-> new MemberNotFoundException("회원을 찾을 수 없습니다."));

        if (!foundMember.getEmail().equals(loginedMemberEmail)) {                               // 조회한 회원의 이메일과 로그인한 회원의 이메일 비교
            throw new RuntimeException("접근이 거부되었습니다.");
        }

        return MemberConverter.toDTO(foundMember);                                              // Entity to DTO
    }

    private String getCurrentUserEmail(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {                      // 인증 정보가 null이거나 인증되지 않은 경우
            return null;
        }

        if (authentication.getPrincipal() instanceof UserDetails) {                             // 인증된 사용자의 Principal의 타입이 UserDetails인 경우 (Principal은 인증된 유저 객체)
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();  // 해당 Principal을 CustomUserDetails로 형변환
            return userDetails.getUsername();                                                   // email을 반환
        } else {
            return authentication.getPrincipal().toString();
        }
    }

    @Override
    @Transactional
    public void deleteMember(DeleteMemberRequest deleteMemberRequest) {
        Member foundMember = memberRepository.findByEmail(deleteMemberRequest.getEmail())           // 이메일로 회원 조회
                .orElseThrow(() -> new MemberNotFoundException("회원을 찾을 수 없습니다."));

        if(!passwordEncoder.matches(deleteMemberRequest.getPassword(), foundMember.getPassword())){ // 비밀번호 일치 여부 확인
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        memberRepository.deleteById(foundMember.getId());                                     // 회원 삭제
    }
}
