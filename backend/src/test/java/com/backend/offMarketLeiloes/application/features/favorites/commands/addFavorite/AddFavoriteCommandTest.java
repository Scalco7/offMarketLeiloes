package com.backend.offMarketLeiloes.application.features.favorites.commands.addFavorite;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.backend.offMarketLeiloes.application.features.favorites.commands.addFavorite.viewModels.AddFavoriteRequest;
import com.backend.offMarketLeiloes.domain.entities.Account;
import com.backend.offMarketLeiloes.domain.entities.Property;
import com.backend.offMarketLeiloes.domain.enums.EPropertyStatus;
import com.backend.offMarketLeiloes.domain.enums.EPropertyType;
import com.backend.offMarketLeiloes.domain.repositories.AccountRepository;
import com.backend.offMarketLeiloes.domain.repositories.FavoritePropertyRepository;
import com.backend.offMarketLeiloes.domain.repositories.PropertyRepository;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AddFavoriteCommandTest {

    @Autowired
    private AddFavoriteCommand addFavoriteCommand;

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
        testProperty.setAuctionDateTime(java.time.LocalDateTime.now().plusDays(1));
        testProperty.setAuctioneerName("Auctioneer");
        testProperty.setAuctionLink("http://link.com");
        testProperty.setStatus(EPropertyStatus.ACTIVE);
        testProperty.setType(EPropertyType.HOUSE);
        testProperty = propertyRepository.saveAndFlush(testProperty);

        // Mock Security Context
        var authentication = new UsernamePasswordAuthenticationToken(testAccount, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void shouldAddFavoriteSuccessfully() {
        AddFavoriteRequest request = new AddFavoriteRequest();
        request.setPropertyId(testProperty.getId());

        addFavoriteCommand.execute(request);

        assertTrue(favoritePropertyRepository.existsByAccountAndProperty(testAccount, testProperty));
    }

    @Test
    void shouldFailWhenAlreadyFavorited() {
        AddFavoriteRequest request = new AddFavoriteRequest();
        request.setPropertyId(testProperty.getId());

        addFavoriteCommand.execute(request);

        assertThrows(RuntimeException.class, () -> addFavoriteCommand.execute(request));
    }

    @Test
    void shouldFailWhenPropertyDoesNotExist() {
        AddFavoriteRequest request = new AddFavoriteRequest();
        request.setPropertyId(java.util.UUID.randomUUID());

        assertThrows(RuntimeException.class, () -> addFavoriteCommand.execute(request));
    }
}
