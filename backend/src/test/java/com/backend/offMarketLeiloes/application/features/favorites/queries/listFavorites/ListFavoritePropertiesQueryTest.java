package com.backend.offMarketLeiloes.application.features.favorites.queries.listFavorites;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.backend.offMarketLeiloes.application.common.dto.PaginatedResponse;
import com.backend.offMarketLeiloes.application.features.favorites.queries.listFavorites.dto.ListFavoritePropertiesFilters;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels.PropertyList;
import com.backend.offMarketLeiloes.domain.entities.Account;
import com.backend.offMarketLeiloes.domain.repositories.AccountRepository;

import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ListFavoritePropertiesQueryTest {

    @Autowired
    private ListFavoritePropertiesQuery listFavoritePropertiesQuery;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AccountRepository accountRepository;

    private Account testAccount;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM favorite_property");
        jdbcTemplate.execute("DELETE FROM property");
        jdbcTemplate.execute("DELETE FROM account");

        testAccount = new Account();
        testAccount.setName("Test User");
        testAccount.setEmail("test@example.com");
        testAccount.setPassword("password");
        testAccount = accountRepository.saveAndFlush(testAccount);

        // Mock Security Context
        var authentication = new UsernamePasswordAuthenticationToken(testAccount, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UUID propA = insertProperty("House A");
        insertProperty("House B");
        UUID propC = insertProperty("Apartment C");

        favoriteProperty(testAccount.getId(), propA);
        favoriteProperty(testAccount.getId(), propC);
    }

    private UUID insertProperty(String name) {
        UUID id = UUID.randomUUID();
        jdbcTemplate.update(
                "INSERT INTO property (id, name, description, valued_price, current_price, created_at, updated_at) " +
                        "VALUES (?, ?, 'Description', 100000.0, 90000.0, now(), now())",
                id, name);
        return id;
    }

    private void favoriteProperty(UUID accountId, UUID propertyId) {
        jdbcTemplate.update(
                "INSERT INTO favorite_property (id, account_id, property_id, created_at) " +
                        "VALUES (random_uuid(), ?, ?, now())",
                accountId, propertyId);
    }

    @Test
    void shouldListUserFavorites() {
        ListFavoritePropertiesFilters filters = new ListFavoritePropertiesFilters();

        PaginatedResponse<PropertyList> result = listFavoritePropertiesQuery.execute(filters);

        assertEquals(2, result.getContent().size());
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void shouldFilterFavoritesByName() {
        ListFavoritePropertiesFilters filters = new ListFavoritePropertiesFilters();
        filters.setName("Apartment");

        PaginatedResponse<PropertyList> result = listFavoritePropertiesQuery.execute(filters);

        assertEquals(1, result.getContent().size());
        assertEquals("Apartment C", result.getContent().get(0).getName());
    }

    @Test
    void shouldReturnEmptyWhenNoFavoritesMatch() {
        ListFavoritePropertiesFilters filters = new ListFavoritePropertiesFilters();
        filters.setName("NonExistent");

        PaginatedResponse<PropertyList> result = listFavoritePropertiesQuery.execute(filters);

        assertEquals(0, result.getContent().size());
    }

    @Test
    void shouldHandlePagination() {
        ListFavoritePropertiesFilters filters = new ListFavoritePropertiesFilters();
        filters.setPage(0);
        filters.setPageSize(1);

        PaginatedResponse<PropertyList> result = listFavoritePropertiesQuery.execute(filters);

        assertEquals(1, result.getContent().size());
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getTotalPages());
    }
}
