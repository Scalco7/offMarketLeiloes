package com.backend.offMarketLeiloes.application.features.authentication.commands.refreshToken;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.backend.offMarketLeiloes.application.features.authentication.AuthenticationResponse;
import com.backend.offMarketLeiloes.application.features.authentication.commands.refreshToken.viewModels.RefreshTokenRequest;
import com.backend.offMarketLeiloes.domain.entities.RefreshToken;
import com.backend.offMarketLeiloes.domain.entities.Account;
import com.backend.offMarketLeiloes.domain.repositories.RefreshTokenRepository;
import com.backend.offMarketLeiloes.infrastructure.authentication.TokenService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenCommand {

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenService tokenService;

    @Transactional
    public AuthenticationResponse execute(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenRepository
                .findByTokenAndAccountId(request.getRefreshToken(), UUID.fromString(request.getAccountId()))
                .orElseThrow(() -> new RuntimeException("Refresh Token inválido para esta conta"));

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh Token expirado");
        }

        Account account = refreshToken.getAccount();

        refreshTokenRepository.delete(refreshToken);

        return tokenService.generateAuthenticationResponse(account);
    }
}
