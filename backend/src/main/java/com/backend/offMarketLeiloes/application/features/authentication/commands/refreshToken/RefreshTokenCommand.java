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

import com.backend.offMarketLeiloes.application.common.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

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
                .orElseThrow(() -> new BusinessException("Sessão inválida. Por favor, faça login novamente.",
                        HttpStatus.UNAUTHORIZED));

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new BusinessException("Sessão expirada. Por favor, faça login novamente.", HttpStatus.UNAUTHORIZED);
        }

        Account account = refreshToken.getAccount();

        refreshTokenRepository.delete(refreshToken);

        return tokenService.generateAuthenticationResponse(account);
    }
}
