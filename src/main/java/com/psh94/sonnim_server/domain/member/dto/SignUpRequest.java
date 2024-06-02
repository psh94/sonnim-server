package com.psh94.sonnim_server.domain.member.dto;

import com.psh94.sonnim_server.domain.member.constant.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(min = 8, message = "비밀번호는 최소 8자리 이상이어야 합니다.")
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "010-\\d{4}-\\d{4}", message = "전화번호를 확인해주세요.")
    private String phone;

    @NotNull
    private Role role;

    @Builder
    public SignUpRequest(String email, String password, String name, String phone, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }
}