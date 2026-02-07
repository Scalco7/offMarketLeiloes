package com.backend.offMarketLeiloes.infrastructure.authentication;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PasswordHashServiceTest {

    @Autowired
    private PasswordHashService passwordHashService;

    @Test
    void shouldGenerateHash() {
        String password = "password123";
        String hash = passwordHashService.generateHashPassword(password);

        assertNotNull(hash);
        assertTrue(hash.startsWith("$2a$") || hash.startsWith("$2b$")); // BCrypt format
    }

    @Test
    void shouldVerifyPassword() {
        String password = "password123";
        String hash = passwordHashService.generateHashPassword(password);

        boolean matches = passwordHashService.verifyPassword(password, hash);

        assertTrue(matches);
    }
}
