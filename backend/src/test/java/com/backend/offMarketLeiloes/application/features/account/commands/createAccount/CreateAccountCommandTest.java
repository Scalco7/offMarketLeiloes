package com.backend.offMarketLeiloes.application.features.account.commands.createAccount;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.backend.offMarketLeiloes.application.features.account.commands.createAccount.viewModels.CreateAccountRequest;
import com.backend.offMarketLeiloes.application.features.authentication.AuthenticationResponse;
import com.backend.offMarketLeiloes.domain.repositories.AccountRepository;
import com.backend.offMarketLeiloes.domain.repositories.RefreshTokenRepository;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CreateAccountCommandTest {

    @Autowired
    private CreateAccountCommand createAccountCommand;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    void setUp() {
        refreshTokenRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void shouldCreateAccountAndReturnTokens() {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setName("New User");
        request.setEmail("new@example.com");
        request.setPassword("password123");

        AuthenticationResponse response = createAccountCommand.execute(request);

        assertNotNull(response);
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());

        assertTrue(accountRepository.findByEmail("new@example.com").isPresent());
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setName("User 1");
        request.setEmail("duplicate@example.com");
        request.setPassword("password123");

        createAccountCommand.execute(request);

        assertThrows(RuntimeException.class, () -> createAccountCommand.execute(request));
    }
}
