package com.backend.offMarketLeiloes.application.features.authentication.commands.refreshToken;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.backend.offMarketLeiloes.application.features.authentication.AuthenticationResponse;
import com.backend.offMarketLeiloes.application.features.authentication.commands.refreshToken.viewModels.RefreshTokenRequest;
import com.backend.offMarketLeiloes.domain.entities.Account;
import com.backend.offMarketLeiloes.domain.entities.RefreshToken;
import com.backend.offMarketLeiloes.domain.repositories.AccountRepository;
import com.backend.offMarketLeiloes.domain.repositories.RefreshTokenRepository;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RefreshTokenCommandTest {

    @Autowired
    private RefreshTokenCommand refreshTokenCommand;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private Account testAccount;
    private String validRefreshToken;

    @BeforeEach
    void setUp() {
        refreshTokenRepository.deleteAll();
        accountRepository.deleteAll();

        testAccount = new Account();
        testAccount.setName("Refresh Account");
        testAccount.setEmail("refresh@example.com");
        testAccount.setPassword("password");
        testAccount = accountRepository.saveAndFlush(testAccount);

        validRefreshToken = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(validRefreshToken);
        refreshToken.setAccount(testAccount);
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(1));
        refreshTokenRepository.saveAndFlush(refreshToken);
    }

    @Test
    void shouldRefreshTokenSuccessfully() {
        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken(validRefreshToken);
        request.setAccountId(testAccount.getId().toString());

        AuthenticationResponse response = refreshTokenCommand.execute(request);

        assertNotNull(response);
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());
    }

    @Test
    void shouldFailWithInvalidToken() {
        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken(UUID.randomUUID().toString());
        request.setAccountId(testAccount.getId().toString());

        assertThrows(RuntimeException.class, () -> refreshTokenCommand.execute(request));
    }

    @Test
    void shouldFailWithExpiredToken() {
        String expiredToken = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(expiredToken);
        refreshToken.setAccount(testAccount);
        refreshToken.setExpiresAt(LocalDateTime.now().minusDays(1));
        refreshTokenRepository.saveAndFlush(refreshToken);

        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken(expiredToken);
        request.setAccountId(testAccount.getId().toString());

        assertThrows(RuntimeException.class, () -> refreshTokenCommand.execute(request));
    }
}
