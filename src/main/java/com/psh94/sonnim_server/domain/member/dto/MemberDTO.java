package com.psh94.sonnim_server.domain.member.dto;

import com.psh94.sonnim_server.domain.member.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@ToString(exclude = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    @NotNull  // ID는 Long 타입이므로 @NotNull을 사용
    private Long id;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10,11}$", message = "유효한 전화번호를 입력해주세요.")
    private String phone;

    @NotNull
    private Role role;
}
