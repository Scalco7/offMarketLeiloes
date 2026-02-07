package com.backend.offMarketLeiloes.application.features.favorites.commands.removeFavorite;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.backend.offMarketLeiloes.application.features.favorites.commands.removeFavorite.viewModels.RemoveFavoriteRequest;
import com.backend.offMarketLeiloes.domain.entities.Account;
import com.backend.offMarketLeiloes.domain.entities.FavoriteProperty;
import com.backend.offMarketLeiloes.domain.entities.Property;
import com.backend.offMarketLeiloes.domain.repositories.AccountRepository;
import com.backend.offMarketLeiloes.domain.repositories.FavoritePropertyRepository;
import com.backend.offMarketLeiloes.domain.repositories.PropertyRepository;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RemoveFavoriteCommandTest {

    @Autowired
    private RemoveFavoriteCommand removeFavoriteCommand;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private FavoritePropertyRepository favoritePropertyRepository;

    private Account testAccount;
    private Property testProperty;

    @BeforeEach
    void setUp() {
        favoritePropertyRepository.deleteAll();
        propertyRepository.deleteAll();
        accountRepository.deleteAll();

        testAccount = new Account();
        testAccount.setName("Test Account");
        testAccount.setEmail("test@example.com");
        testAccount.setPassword("password");
        testAccount = accountRepository.saveAndFlush(testAccount);

        testProperty = new Property();
        testProperty.setName("Test Property");
        testProperty.setDescription("Description");
        testProperty.setValuedPrice(100000.0);
        testProperty.setCurrentPrice(90000.0);
        testProperty = propertyRepository.saveAndFlush(testProperty);

        FavoriteProperty favorite = new FavoriteProperty();
        favorite.setAccount(testAccount);
        favorite.setProperty(testProperty);
        favoritePropertyRepository.saveAndFlush(favorite);

        // Mock Security Context
        var authentication = new UsernamePasswordAuthenticationToken(testAccount, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void shouldRemoveFavoriteSuccessfully() {
        RemoveFavoriteRequest request = new RemoveFavoriteRequest();
        request.setPropertyId(testProperty.getId());

        removeFavoriteCommand.execute(request);

        assertFalse(favoritePropertyRepository.existsByAccountAndProperty(testAccount, testProperty));
    }

    @Test
    void shouldFailWhenNotFavorited() {
        favoritePropertyRepository.deleteAll();

        RemoveFavoriteRequest request = new RemoveFavoriteRequest();
        request.setPropertyId(testProperty.getId());

        assertThrows(RuntimeException.class, () -> removeFavoriteCommand.execute(request));
    }

    @Test
    void shouldFailWhenPropertyDoesNotExist() {
        RemoveFavoriteRequest request = new RemoveFavoriteRequest();
        request.setPropertyId(java.util.UUID.randomUUID());

        assertThrows(RuntimeException.class, () -> removeFavoriteCommand.execute(request));
    }
}
