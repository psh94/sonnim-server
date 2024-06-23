package com.psh94.sonnim_server.common.redis.repository;

import com.psh94.sonnim_server.common.auth.jwt.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    RefreshToken findByToken(String token);
    void deleteByUserId(Long userId);
}
