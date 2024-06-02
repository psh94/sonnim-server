package com.psh94.sonnim_server.common.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginForm {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
