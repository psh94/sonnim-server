package com.psh94.sonnim_server.common.auth;

import com.psh94.sonnim_server.common.auth.jwt.CustomUserDetails;
import com.psh94.sonnim_server.common.auth.jwt.JwtUtil;
import com.psh94.sonnim_server.common.auth.jwt.RefreshToken;
import com.psh94.sonnim_server.common.redis.repository.RefreshTokenRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public Map<String, String> login(@Valid @RequestBody LoginForm loginForm) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword())
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getId();
            String accessToken = jwtUtil.generateAccessToken(userId);
            String refreshToken = jwtUtil.generateRefreshToken(userId);

            refreshTokenRepository.deleteByUserId(userId);
            refreshTokenRepository.save(new RefreshToken(userId, refreshToken));

            Map<String, String> response = new HashMap<>();
            response.put("accessToken", accessToken);
            response.put("refreshToken", refreshToken);

            return response;
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid login credentials");
        }
    }

    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestBody Map<String, String> tokenRequest) {
        String refreshToken = tokenRequest.get("refreshToken");

        if (jwtUtil.validateToken(refreshToken)) {
            Long userId = jwtUtil.getUserIdFromToken(refreshToken);
            String newAccessToken = jwtUtil.generateAccessToken(userId);

            Map<String, String> response = new HashMap<>();
            response.put("accessToken", newAccessToken);
            return response;
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }
}