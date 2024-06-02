package com.psh94.sonnim_server.common.auth.jwt;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@Entity
@Getter
@RedisHash("refreshToken")
@NoArgsConstructor
public class RefreshToken {

    @Id
    private String id;
    private Long userId;
    private String token;

    @Builder
    public RefreshToken(Long userId, String token) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.token = token;
    }
}