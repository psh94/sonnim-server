package com.psh94.sonnim_server.common.auth.jwt;

import com.psh94.sonnim_server.common.exception.MemberNotFoundException;
import com.psh94.sonnim_server.domain.member.entity.Member;
import com.psh94.sonnim_server.domain.member.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 요청 헤더에서 Authorization 헤더를 가져옴
        final String header = request.getHeader("Authorization");
        final String token;
        final Long userId;

        // Authorization 헤더가 없거나 "Bearer "로 시작하지 않으면 다음 필터로 넘김
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // "Bearer " 7자 이후 토큰 값을 추출
        token = header.substring(7);
        userId = jwtUtil.getUserIdFromToken(token);

        Member member = memberRepository.findById(userId)
                .orElseThrow(()-> new MemberNotFoundException("회원을 찾을 수 없습니다."));

        // 현재 SecurityContext에 인증 정보가 없는 경우에만 처리
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(member.getEmail());

            // 토큰이 유효한 경우
            if (jwtUtil.validateToken(token)) {
                // 인증 토큰을 생성하여 SecurityContext에 설정
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}