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

    private Account testUser;

    @BeforeEach
    void setUp() {
        refreshTokenRepository.deleteAll();
        accountRepository.deleteAll();

        testUser = new Account();
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");
        testUser = accountRepository.saveAndFlush(testUser);
    }

    @Test
    void shouldGenerateAccessToken() {
        String token = tokenService.generateToken(testUser);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void shouldValidateToken() {
        String token = tokenService.generateToken(testUser);
        String subject = tokenService.validateToken(token);
        assertEquals(testUser.getEmail(), subject);
    }

    @Test
    void shouldCreateRefreshToken() {
        String refreshToken = tokenService.createRefreshToken(testUser);
        assertNotNull(refreshToken);

        assertTrue(refreshTokenRepository.findByToken(refreshToken).isPresent());
    }

    @Test
    void shouldGenerateAuthenticationResponse() {
        AuthenticationResponse response = tokenService.generateAuthenticationResponse(testUser);

        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());

        String subject = tokenService.validateToken(response.getAccessToken());
        assertEquals(testUser.getEmail(), subject);

        assertTrue(refreshTokenRepository.findByToken(response.getRefreshToken()).isPresent());
    }
}
