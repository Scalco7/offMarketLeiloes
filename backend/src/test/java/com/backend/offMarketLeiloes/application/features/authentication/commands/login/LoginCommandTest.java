package com.backend.offMarketLeiloes.application.features.authentication.commands.login;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.backend.offMarketLeiloes.application.features.authentication.AuthenticationResponse;
import com.backend.offMarketLeiloes.application.features.authentication.commands.login.viewModels.LoginRequest;
import com.backend.offMarketLeiloes.domain.entities.Account;
import com.backend.offMarketLeiloes.domain.repositories.AccountRepository;
import com.backend.offMarketLeiloes.domain.repositories.RefreshTokenRepository;
import com.backend.offMarketLeiloes.infrastructure.authentication.PasswordHashService;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class LoginCommandTest {

    @Autowired
    private LoginCommand loginCommand;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private PasswordHashService passwordHashService;

    @BeforeEach
    void setUp() {
        refreshTokenRepository.deleteAll();
        accountRepository.deleteAll();

        Account user = new Account();
        user.setName("Login User");
        user.setEmail("login@example.com");
        user.setPassword(passwordHashService.gerarHashSenha("correct_password"));
        accountRepository.save(user);
    }

    @Test
    void shouldLoginSuccessfully() {
        LoginRequest request = new LoginRequest();
        request.setEmail("login@example.com");
        request.setPassword("correct_password");

        AuthenticationResponse response = loginCommand.execute(request);

        assertNotNull(response);
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());
    }

    @Test
    void shouldFailWithWrongPassword() {
        LoginRequest request = new LoginRequest();
        request.setEmail("login@example.com");
        request.setPassword("wrong_password");

        assertThrows(RuntimeException.class, () -> loginCommand.execute(request));
    }

    @Test
    void shouldFailWithNonExistentUser() {
        LoginRequest request = new LoginRequest();
        request.setEmail("nonexistent@example.com");
        request.setPassword("password");

        assertThrows(RuntimeException.class, () -> loginCommand.execute(request));
    }
}
