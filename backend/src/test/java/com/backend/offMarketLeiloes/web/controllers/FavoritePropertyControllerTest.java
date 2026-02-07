package com.backend.offMarketLeiloes.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.backend.offMarketLeiloes.application.features.favorites.commands.addFavorite.viewModels.AddFavoriteRequest;
import com.backend.offMarketLeiloes.application.features.favorites.commands.removeFavorite.viewModels.RemoveFavoriteRequest;
import com.backend.offMarketLeiloes.domain.entities.Account;
import com.backend.offMarketLeiloes.domain.entities.FavoriteProperty;
import com.backend.offMarketLeiloes.domain.entities.Property;
import com.backend.offMarketLeiloes.domain.repositories.AccountRepository;
import com.backend.offMarketLeiloes.domain.repositories.FavoritePropertyRepository;
import com.backend.offMarketLeiloes.domain.repositories.PropertyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
class FavoritePropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private FavoritePropertyRepository favoritePropertyRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Account testAccount;
    private Property testProperty;

    @BeforeEach
    void setUp() {
        favoritePropertyRepository.deleteAll();
        propertyRepository.deleteAll();
        accountRepository.deleteAll();

        testAccount = new Account();
        testAccount.setName("Auth Account");
        testAccount.setEmail("auth@example.com");
        testAccount.setPassword("password123");
        testAccount = accountRepository.saveAndFlush(testAccount);

        testProperty = new Property();
        testProperty.setName("Test Property");
        testProperty.setDescription("Description");
        testProperty.setValuedPrice(100000.0);
        testProperty.setCurrentPrice(90000.0);
        testProperty = propertyRepository.saveAndFlush(testProperty);

        // Mock Security Context for the commands executed by the controller
        var authentication = new UsernamePasswordAuthenticationToken(testAccount, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void shouldAddFavoriteViaApi() throws Exception {
        AddFavoriteRequest request = new AddFavoriteRequest();
        request.setPropertyId(testProperty.getId());

        mockMvc.perform(post("/favorites")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldRemoveFavoriteViaApi() throws Exception {
        FavoriteProperty favorite = new FavoriteProperty();
        favorite.setAccount(testAccount);
        favorite.setProperty(testProperty);
        favoritePropertyRepository.saveAndFlush(favorite);

        RemoveFavoriteRequest request = new RemoveFavoriteRequest();
        request.setPropertyId(testProperty.getId());

        mockMvc.perform(delete("/favorites")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }
}
