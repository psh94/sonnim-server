package com.psh94.sonnim_server.common.auth;

import com.psh94.sonnim_server.common.auth.jwt.CustomUserDetails;
import com.psh94.sonnim_server.common.auth.jwt.JwtUtil;
import com.psh94.sonnim_server.common.auth.jwt.RefreshToken;
import com.psh94.sonnim_server.common.redis.repository.RefreshTokenRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginForm loginForm) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()) // 인증 요청
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getId();
            String accessToken = jwtUtil.generateAccessToken(userId);
            String refreshToken = jwtUtil.generateRefreshToken(userId);

            refreshTokenRepository.save(RefreshToken.builder()
                    .userId(userId)
                    .token(refreshToken)
                    .build());

            AuthResponse response = AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials");
        }
    }

    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestBody Map<String, String> tokenRequest) {
        String refreshTokenFromRequest = tokenRequest.get("refreshToken");

        // Redis에서 리프레시 토큰 조회
        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshTokenFromRequest);
        System.out.println("storedToken = " + storedToken);

        if (storedToken != null && jwtUtil.validateToken(storedToken.getToken())) {
            Long userId = jwtUtil.getUserIdFromToken(storedToken.getToken());
            String newAccessToken = jwtUtil.generateAccessToken(userId);
            String newRefreshToken = jwtUtil.generateRefreshToken(userId);

            refreshTokenRepository.save(RefreshToken.builder()
                    .userId(userId)
                    .token(newRefreshToken)
                    .build());

            Map<String, String> response = new HashMap<>();
            response.put("accessToken", newAccessToken);
            response.put("refreshToken", newRefreshToken);
            return response;
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }

    @PostMapping("/logout")
    public Map<String, String> logout(@RequestBody Map<String, String> tokenRequest) {
        String refreshTokenFromRequest = tokenRequest.get("refreshToken");

        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshTokenFromRequest);

        if (storedToken != null && jwtUtil.validateToken(storedToken.getToken())) {
            refreshTokenRepository.deleteByUserId(storedToken.getUserId());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Logged out successfully");
            return response;
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }
}