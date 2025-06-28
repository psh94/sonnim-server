package com.psh94.sonnim_server.common.auth.jwt;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;



@Getter
@NoArgsConstructor
@RedisHash("RefreshToken")
public class RefreshToken {

    @Id
    private String token;
    private Long userId;


    @Builder
    public RefreshToken(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}
