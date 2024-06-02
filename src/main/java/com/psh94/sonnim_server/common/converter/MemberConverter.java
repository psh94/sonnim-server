package com.psh94.sonnim_server.common.converter;

import com.psh94.sonnim_server.common.auth.LoginForm;
import com.psh94.sonnim_server.domain.member.dto.MemberDTO;
import com.psh94.sonnim_server.domain.member.dto.SignUpRequest;
import com.psh94.sonnim_server.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberConverter {

    private final PasswordEncoder passwordEncoder;

    public Member toEntity(SignUpRequest signUpRequest){
        return Member.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .name(signUpRequest.getName())
                .phone(signUpRequest.getPhone())
                .role(signUpRequest.getRole())
                .build();
    }

    public Member toEntity(LoginForm loginForm){
        return Member.builder()
                .email(loginForm.getEmail())
                .password(passwordEncoder.encode(loginForm.getPassword()))
                .build();
    }

    public MemberDTO toDTO(Member member){
        return MemberDTO.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .phone(member.getPhone())
                .role(member.getRole())
                .build();
    }
}
