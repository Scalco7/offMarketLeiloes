package com.backend.offMarketLeiloes.application.features.properties.queries.listProperties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.backend.offMarketLeiloes.application.common.dto.PaginatedResponse;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels.ListPropertiesFilters;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels.PropertyList;

import java.util.UUID;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class ListPropertiesQueryTest {

    @Autowired
    private ListPropertiesQuery listPropertiesQuery;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM property");
        jdbcTemplate.execute("DELETE FROM property_address");

        insertProperty("House A", 500000.0, 400000.0, "SP", "São Paulo");
        insertProperty("House B", 300000.0, 250000.0, "RJ", "Rio de Janeiro");
        insertProperty("Apartment C", 800000.0, 750000.0, "SP", "Campinas");
        insertProperty("Apartment D", 1200000.0, 1100000.0, "MG", "Belo Horizonte");
    }

    private void insertProperty(String name, Double valuedPrice, Double currentPrice, String state, String city) {
        UUID addressId = UUID.randomUUID();
        jdbcTemplate.update(
                "INSERT INTO property_address (id, state, city, zip_code, country, street, number, neighborhood, created_at, updated_at) "
                        +
                        "VALUES (?, ?, ?, '00000', 'Brazil', 'Street', '1', 'Bairro', now(), now())",
                addressId, state, city);

        jdbcTemplate.update(
                "INSERT INTO property (id, name, description, valued_price, current_price, address_id, created_at, updated_at) "
                        +
                        "VALUES (random_uuid(), ?, 'Description', ?, ?, ?, now(), now())",
                name, valuedPrice, currentPrice, addressId);
    }

    @Test
    void shouldFilterByName() {
        ListPropertiesFilters filters = new ListPropertiesFilters();
        filters.setName("Apartment");

        PaginatedResponse<PropertyList> result = listPropertiesQuery.execute(filters);

        assertEquals(2, result.getContent().size());
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void shouldFilterByPriceRange() {
        ListPropertiesFilters filters = new ListPropertiesFilters();
        filters.setMinPrice(300000.0);
        filters.setMaxPrice(800000.0);

        PaginatedResponse<PropertyList> result = listPropertiesQuery.execute(filters);

        // Houses at 400k and 750k fit here. 250k is too low, 1.1M is too high.
        assertEquals(2, result.getContent().size());
    }

    @Test
    void shouldFilterByState() {
        ListPropertiesFilters filters = new ListPropertiesFilters();
        filters.setState("SP");

        PaginatedResponse<PropertyList> result = listPropertiesQuery.execute(filters);

        assertEquals(2, result.getContent().size());
    }

    @Test
    void shouldFilterByCity() {
        ListPropertiesFilters filters = new ListPropertiesFilters();
        filters.setCity("Rio");

        PaginatedResponse<PropertyList> result = listPropertiesQuery.execute(filters);

        assertEquals(1, result.getContent().size());
        assertEquals("House B", result.getContent().get(0).getName());
    }

    @Test
    void shouldReturnSecondPage() {
        ListPropertiesFilters filters = new ListPropertiesFilters();
        filters.setPage(1);
        filters.setPageSize(2);

        PaginatedResponse<PropertyList> result = listPropertiesQuery.execute(filters);

        assertEquals(2, result.getContent().size());
        assertEquals(4, result.getTotalElements());
        assertEquals(2, result.getTotalPages());
        assertEquals(1, result.getPage());
    }

    @Test
    void shouldCombineFilters() {
        ListPropertiesFilters filters = new ListPropertiesFilters();
        filters.setState("SP");
        filters.setMinPrice(500000.0);

        PaginatedResponse<PropertyList> result = listPropertiesQuery.execute(filters);

        assertEquals(1, result.getContent().size());
        assertEquals("Apartment C", result.getContent().get(0).getName());
    }

    @Test
    void shouldSortByPriceAscending() {
        ListPropertiesFilters filters = new ListPropertiesFilters();
        filters.setSortByPrice("asc");

        PaginatedResponse<PropertyList> result = listPropertiesQuery.execute(filters);

        assertEquals(4, result.getContent().size());
        assertEquals("House B", result.getContent().get(0).getName()); // 250k
        assertEquals("House A", result.getContent().get(1).getName()); // 400k
        assertEquals("Apartment C", result.getContent().get(2).getName()); // 750k
        assertEquals("Apartment D", result.getContent().get(3).getName()); // 1.1M
    }

    @Test
    void shouldSortByPriceDescending() {
        ListPropertiesFilters filters = new ListPropertiesFilters();
        filters.setSortByPrice("desc");

        PaginatedResponse<PropertyList> result = listPropertiesQuery.execute(filters);

        assertEquals(4, result.getContent().size());
        assertEquals("Apartment D", result.getContent().get(0).getName()); // 1.1M
        assertEquals("Apartment C", result.getContent().get(1).getName()); // 750k
        assertEquals("House A", result.getContent().get(2).getName()); // 400k
        assertEquals("House B", result.getContent().get(3).getName()); // 250k
    }
}
