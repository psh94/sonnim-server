package com.psh94.sonnim_server.domain.member.dto;

import com.psh94.sonnim_server.domain.member.constant.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    @NotNull
    private Long id;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String phone;
    @NotNull
    private Role role;


}
