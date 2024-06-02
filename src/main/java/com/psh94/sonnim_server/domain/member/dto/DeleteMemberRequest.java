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
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;

    @Builder
    public DeleteMemberRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
