package com.psh94.sonnim_server.service;


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
import com.psh94.sonnim_server.domain.member.service.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberServiceImpl memberService;

    private SignUpRequest signUpRequest;
    private Member member;
    private MemberDTO memberDTO;
    private DeleteMemberRequest deleteMemberRequest;

    @BeforeEach
    void setUp() {
        signUpRequest = SignUpRequest.builder()
                .email   ("test@test.com")
                .password("testpw")
                .name    ("kim")
                .phone   ("010-1234-1234")
                .build();

        member = Member.builder()
                .email      ("test@test.com")
                .password   ("encodedpw")
                .name       ("Kim")
                .phone      ("010-1234-1234")
                .build();

        memberDTO = MemberDTO.builder()
                .id         (1L)
                .email      ("test@test.com")
                .password   ("testpw")
                .name       ("Kim")
                .phone      ("010-1234-1234")
                .build();

        deleteMemberRequest = DeleteMemberRequest.builder()
                .email("test@test.com")
                .password("testpw")
                .build();
    }

    @Test
    void 회원_생성_성공() {
        when(memberRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(false);
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        Member memberEntity = MemberConverter.toEntity(signUpRequest);
        MemberDTO expectedMemberDTO = MemberConverter.toDTO(memberEntity);
        MemberDTO result = memberService.createMember(signUpRequest);

        assertNotNull(result);
        assertEquals(expectedMemberDTO.getEmail(), result.getEmail());
    }

    @Test
    void 회원_생성_이메일_중복_실패() {
        when(memberRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> {
            memberService.createMember(signUpRequest);
        });
    }

    @Test
    @Transactional(readOnly = true)
    void 회원_조회_성공 (){
        String email = "test@test.com";
        Authentication authentication = mock(Authentication.class);
        CustomUserDetails userDetails = mock(CustomUserDetails.class);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(email);
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));

        MemberDTO expectedMemberDTO = MemberConverter.toDTO(member);
        MemberDTO result = memberService.findMemberByAuth();

        assertNotNull(result);
        assertEquals(memberDTO.getEmail(), result.getEmail());
    }

    @Test
    @Transactional(readOnly = true)
    void 회원_조회_실패() {
        String email = "test2@test.com";
        Authentication authentication = mock(Authentication.class);
        CustomUserDetails userDetails = mock(CustomUserDetails.class);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(email);
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));

        when(memberRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(MemberNotFoundException.class, () -> {
            memberService.findMemberByAuth();
        });
    }

    @Test
    void 회원_삭제_성공() {
        when(memberRepository.findByEmail(deleteMemberRequest.getEmail())).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(deleteMemberRequest.getPassword(), member.getPassword())).thenReturn(true);

        memberService.deleteMember(deleteMemberRequest);

        verify(memberRepository, times(1)).deleteByEmail(deleteMemberRequest.getEmail());
    }

    @Test
    void 회원_삭제_비밀번호_불일치_실패() {
        DeleteMemberRequest deleteMemberRequest = DeleteMemberRequest.builder()
                .email("test@test.com")
                .password("errorpw")
                .build();

        when(memberRepository.findByEmail(deleteMemberRequest.getEmail())).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(deleteMemberRequest.getPassword(), member.getPassword())).thenReturn(false);

        assertThrows(PasswordMismatchException.class, () -> {
            memberService.deleteMember(deleteMemberRequest);
        });

        verify(memberRepository, never()).deleteByEmail(anyString());
    }

    @Test
    void 회원_삭제_회원_조회_실패() {
        DeleteMemberRequest deleteMemberRequest = DeleteMemberRequest.builder()
                .email("error@test.com")
                .password("errorpw")
                .build();

        when(memberRepository.findByEmail(deleteMemberRequest.getEmail())).thenReturn(Optional.empty());

        assertThrows(MemberNotFoundException.class, () -> {
            memberService.deleteMember(deleteMemberRequest);
        });

        verify(memberRepository, never()).deleteByEmail(anyString());
    }
}