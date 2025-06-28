package com.psh94.sonnim_server.common.auth;

import com.psh94.sonnim_server.common.auth.jwt.CustomUserDetails;
import com.psh94.sonnim_server.common.auth.jwt.JwtUtil;
import com.psh94.sonnim_server.common.auth.jwt.RefreshToken;
import com.psh94.sonnim_server.common.redis.repository.RefreshTokenRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;

    @PostMapping("/login")
    public Map<String, String> login(@Valid @RequestBody LoginForm loginForm) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()) // 인증 요청
            );
            // 인증 성공 후 사용자 정보 가져오기
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getId();

            // JWT 토큰 생성
            String accessToken = jwtUtil.generateAccessToken(userId);
            String refreshToken = jwtUtil.generateRefreshToken(userId);

            // Redis 저장: userId → refreshToken, refreshToken → userId
            Duration ttl = jwtUtil.getRefreshTokenDuration(); // .yml 기반 TTL

            redisTemplate.opsForValue().set("RefreshToken:" + userId, refreshToken, ttl);
            redisTemplate.opsForValue().set("RefreshTokenMeta:" + refreshToken, userId.toString(), ttl);

            // token 객체 생성 및 저장
            Map<String, String> response = new HashMap<>();
            response.put("accessToken", accessToken);
            response.put("refreshToken", refreshToken);

            return response;
        } catch (AuthenticationException e) {
            throw new RuntimeException("로그인 자격이 유효하지 않습니다.");
        }
    }

    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestBody Map<String, String> tokenRequest) {
        String refreshToken = tokenRequest.get("refreshToken");
        String userIdStr = redisTemplate.opsForValue().get("RefreshTokenMeta:" + refreshToken);

        if (userIdStr == null || !jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("refresh 토큰이 유효하지 않습니다.");
        }

        Long userId = Long.valueOf(userIdStr);
        String newAccessToken = jwtUtil.generateAccessToken(userId);
        String newRefreshToken = jwtUtil.generateRefreshToken(userId);

        Duration ttl = jwtUtil.getRefreshTokenDuration();
        redisTemplate.opsForValue().set("RefreshToken:" + userId, newRefreshToken, ttl);
        redisTemplate.opsForValue().set("RefreshTokenMeta:" + newRefreshToken, userIdStr, ttl);

        // 기존 토큰은 삭제 (optional)
        redisTemplate.delete("RefreshTokenMeta:" + refreshToken);


        Map<String, String> response = new HashMap<>();
        response.put("accessToken", newAccessToken);
        response.put("refreshToken", newRefreshToken);
        return response;
    }

    @PostMapping("/logout")
    public Map<String, String> logout(@RequestBody Map<String, String> tokenRequest) {
        String refreshToken = tokenRequest.get("refreshToken");
        String userIdStr = redisTemplate.opsForValue().get("RefreshTokenMeta:" + refreshToken);

        if (userIdStr == null || !jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("refresh 토큰이 유효하지 않습니다.");
        }

        Long userId = Long.valueOf(userIdStr);
        redisTemplate.delete("RefreshToken:" + userId);
        redisTemplate.delete("RefreshTokenMeta:" + refreshToken);

        Map<String, String> response = new HashMap<>();
        response.put("message", "로그아웃에 성공하였습니다.");
        return response;
    }
}