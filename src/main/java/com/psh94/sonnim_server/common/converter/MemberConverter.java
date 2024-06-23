package com.psh94.sonnim_server.common.converter;

import com.psh94.sonnim_server.common.auth.LoginForm;
import com.psh94.sonnim_server.domain.member.dto.MemberDTO;
import com.psh94.sonnim_server.domain.member.dto.SignUpRequest;
import com.psh94.sonnim_server.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public static Member toEntity(SignUpRequest signUpRequest){
        return Member.builder()
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .name(signUpRequest.getName())
                .phone(signUpRequest.getPhone())
                .role(signUpRequest.getRole())
                .build();
    }

    public static Member toEntity(LoginForm loginForm){
        return Member.builder()
                .email(loginForm.getEmail())
                .password(loginForm.getPassword())
                .build();
    }

    public static MemberDTO toDTO(Member member){
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
