package com.psh94.sonnim_server.common.redis.repository;

import com.psh94.sonnim_server.common.auth.jwt.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUserId(Long userId);
}
