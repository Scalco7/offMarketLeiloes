package com.backend.offMarketLeiloes.application.features.properties.queries.listAvailableStates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.backend.offMarketLeiloes.application.features.properties.queries.listAvailableStates.viewModels.AvailableState;
import com.backend.offMarketLeiloes.domain.enums.EPropertyStatus;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class ListAvailableStatesQueryTest {

    @Autowired
    private ListAvailableStatesQuery listAvailableStatesQuery;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM favorite_property");
        jdbcTemplate.execute("DELETE FROM property");
        jdbcTemplate.execute("DELETE FROM property_address");

        insertProperty("Property SP 1", "SP", EPropertyStatus.ACTIVE);
        insertProperty("Property SP 2", "SP", EPropertyStatus.ACTIVE);
        insertProperty("Property RJ 1", "RJ", EPropertyStatus.ACTIVE);
        insertProperty("Property MG (Sold)", "MG", EPropertyStatus.SOLD);
    }

    private void insertProperty(String name, String state, EPropertyStatus status) {
        UUID addressId = UUID.randomUUID();
        jdbcTemplate.update(
                "INSERT INTO property_address (id, state, city, zip_code, country, street, number, neighborhood, created_at, updated_at) "
                        +
                        "VALUES (?, ?, 'City', '00000', 'Brazil', 'Street', '1', 'Bairro', now(), now())",
                addressId, state);

        jdbcTemplate.update(
                "INSERT INTO property (id, name, description, valued_price, current_price, auction_date_time, auctioneer_name, auction_link, status, type, address_id, created_at, updated_at) "
                        +
                        "VALUES (?, ?, 'Description', 100000.0, 90000.0, now(), 'Auctioneer', 'http://link.com', ?, 'HOUSE', ?, now(), now())",
                UUID.randomUUID(), name, status.name(), addressId);
    }

    @Test
    void shouldReturnPropertyCountPerState() {
        List<AvailableState> result = listAvailableStatesQuery.execute();

        assertEquals(2, result.size());

        AvailableState rj = result.stream().filter(s -> s.getState().equals("RJ")).findFirst().get();
        assertEquals(1, rj.getPropertyCount());

        AvailableState sp = result.stream().filter(s -> s.getState().equals("SP")).findFirst().get();
        assertEquals(2, sp.getPropertyCount());

        // MG should not be present because the property is SOLD
        assertTrue(result.stream().noneMatch(s -> s.getState().equals("MG")));
    }
}
