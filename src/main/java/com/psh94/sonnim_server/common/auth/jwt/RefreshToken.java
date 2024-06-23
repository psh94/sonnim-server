package com.psh94.sonnim_server.common.auth.jwt;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;


@Getter
@NoArgsConstructor
@RedisHash("RefreshToken")
public class RefreshToken {

    @Id
    private String id;

    @Indexed
    private Long userId;

    @Indexed
    private String token;

    @Builder
    public RefreshToken(Long userId, String token) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.token = token;
    }
}
