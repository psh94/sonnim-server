package com.psh94.sonnim_server.domain.member.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteMemberRequest {

    @NotBlank
    @Email(regexp = "^[\\w\\.-]+@[\\w\\.-]+\\.[a-z]{2,}$", message = "유효한 이메일 주소를 입력해주세요.")
    private String email;

    @NotBlank
    private String password;

    @Builder
    public DeleteMemberRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
