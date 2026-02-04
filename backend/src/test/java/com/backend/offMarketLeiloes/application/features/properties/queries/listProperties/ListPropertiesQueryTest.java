package com.backend.offMarketLeiloes.application.features.properties.queries.listProperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels.PropertyList;

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
        jdbcTemplate.execute(
                "INSERT INTO property (id, name, description, valued_price, current_price, created_at, updated_at) " +
                        "VALUES (random_uuid(), 'Apartamento Luxo', 'Vista para o mar', 500000.0, 450000.0, now(), now())");
    }

    @Test
    void shouldReturnPropertiesFromDatabase() {
        // Act
        List<PropertyList> result = listPropertiesQuery.execute();

        // Assert
        assertEquals(1, result.size());
        PropertyList property = result.get(0);
        assertEquals("Apartamento Luxo", property.getName());
        assertEquals(500000.0, property.getValuedPrice());
        assertEquals(450000.0, property.getCurrentPrice());
    }

    @Test
    void shouldReturnEmptyListWhenNoPropertiesExist() {
        // Arrange
        jdbcTemplate.execute("DELETE FROM property");

        // Act
        List<PropertyList> result = listPropertiesQuery.execute();

        // Assert
        assertFalse(result.isEmpty() == false);
        assertEquals(0, result.size());
    }
}
