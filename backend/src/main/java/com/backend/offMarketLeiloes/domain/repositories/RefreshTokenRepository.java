package com.backend.offMarketLeiloes.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.offMarketLeiloes.domain.entities.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByTokenAndUserId(String token, UUID userId);

    void deleteByUserEmail(String email);
}
