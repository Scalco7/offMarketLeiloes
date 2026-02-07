package com.backend.offMarketLeiloes.infrastructure.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.backend.offMarketLeiloes.application.features.authentication.AuthenticationResponse;
import com.backend.offMarketLeiloes.domain.entities.Account;
import com.backend.offMarketLeiloes.domain.repositories.AccountRepository;
import com.backend.offMarketLeiloes.domain.repositories.RefreshTokenRepository;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private Account testAccount;

    @BeforeEach
    void setUp() {
        refreshTokenRepository.deleteAll();
        accountRepository.deleteAll();

        testAccount = new Account();
        testAccount.setName("Test Account");
        testAccount.setEmail("test@example.com");
        testAccount.setPassword("password");
        testAccount = accountRepository.saveAndFlush(testAccount);
    }

    @Test
    void shouldGenerateAccessToken() {
        String token = tokenService.generateToken(testAccount);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void shouldValidateToken() {
        String token = tokenService.generateToken(testAccount);
        String subject = tokenService.validateToken(token);
        assertEquals(testAccount.getEmail(), subject);
    }

    @Test
    void shouldCreateRefreshToken() {
        String refreshToken = tokenService.createRefreshToken(testAccount);
        assertNotNull(refreshToken);

        assertTrue(refreshTokenRepository.findByToken(refreshToken).isPresent());
    }

    @Test
    void shouldGenerateAuthenticationResponse() {
        AuthenticationResponse response = tokenService.generateAuthenticationResponse(testAccount);

        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());

        String subject = tokenService.validateToken(response.getAccessToken());
        assertEquals(testAccount.getEmail(), subject);

        assertTrue(refreshTokenRepository.findByToken(response.getRefreshToken()).isPresent());
    }
}
