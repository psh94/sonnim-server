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
        validateDuplicatedEmail(signUpRequest.getEmail());
        signUpRequest.encodePassword(passwordEncoder);

        Member memberEntity = MemberConverter.toEntity(signUpRequest);
        Member savedMember = memberRepository.save(memberEntity);
        return MemberConverter.toDTO(savedMember);
    }

    private void validateDuplicatedEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("이미 존재하는 이메일입니다.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDTO findMemberByAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginedMemberEmail = getCurrentUserEmail(authentication);

        Member foundMember = memberRepository.findByEmail(loginedMemberEmail)
                .orElseThrow(()-> new MemberNotFoundException("회원을 찾을 수 없습니다."));

        if (!foundMember.getEmail().equals(loginedMemberEmail)) {
            throw new RuntimeException("접근이 거부되었습니다.");
        }

        return MemberConverter.toDTO(foundMember);
    }

    private String getCurrentUserEmail(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        if (authentication.getPrincipal() instanceof UserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUsername(); // email을 반환
        } else {
            return authentication.getPrincipal().toString();
        }
    }

    @Transactional
    public void deleteMember(DeleteMemberRequest deleteMemberRequest) {
        Member foundMember = memberRepository.findByEmail(deleteMemberRequest.getEmail())
                .orElseThrow(() -> new MemberNotFoundException("회원을 찾을 수 없습니다."));

        if(!passwordEncoder.matches(deleteMemberRequest.getPassword(), foundMember.getPassword())){
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        memberRepository.deleteByEmail(foundMember.getEmail());
    }
}
