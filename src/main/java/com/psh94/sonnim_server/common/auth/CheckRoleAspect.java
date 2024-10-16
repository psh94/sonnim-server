package com.psh94.sonnim_server.common.auth;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Component
public class CheckRoleAspect {

    @Before("@annotation(checkRole)")
    public void checkMemberRole(CheckRole checkRole) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("인증되지 않은 사용자입니다.");
        }

        Set<String> userRoles = getCurrentUserRoles(authentication);
        if (userRoles.isEmpty()) {
            throw new AccessDeniedException("사용자의 역할을 확인할 수 없습니다.");
        }

        Set<String> requiredRoles = Arrays.stream(checkRole.value())
                .map(role -> "ROLE_" + role)
                .collect(Collectors.toSet());

        if (userRoles.stream().noneMatch(requiredRoles::contains)) {
            throw new AccessDeniedException("요구되는 역할이 없습니다.");
        }
    }

    private Set<String> getCurrentUserRoles(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }
}
