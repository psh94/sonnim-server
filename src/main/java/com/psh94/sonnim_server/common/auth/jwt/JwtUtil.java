package com.psh94.sonnim_server.common.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key;
    private final long accessTokenValidity;     // access 토큰 유효기간
    private final long refreshTokenValidity;    // refresh 토큰 유효기간

    public JwtUtil(@Value("${jwt.secret}") String secretKey,
                   @Value("${jwt.access-token-validity}") long accessTokenValidity,
                   @Value("${jwt.refresh-token-validity}") long refreshTokenValidity) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);    // secretKey를 디코딩하여 byte 배열로 변환
        this.key = Keys.hmacShaKeyFor(keyBytes);                // byte 배열을 Key 객체로 변환
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String generateAccessToken(Long userId) {
        return generateToken(userId, accessTokenValidity);
    }

    public String generateRefreshToken(Long userId) {
        return generateToken(userId, refreshTokenValidity);
    }

    private String generateToken(Long userId, long validity) {
        long now = (new Date()).getTime();
        Date expiration = new Date(now + validity);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date(now))
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return Long.valueOf(claims.getSubject());
    }

    public String extractUsername(String token) {
        return getUserIdFromToken(token).toString();
    }

    public Duration getRefreshTokenDuration() {
        return Duration.ofMillis(refreshTokenValidity);
    }
}