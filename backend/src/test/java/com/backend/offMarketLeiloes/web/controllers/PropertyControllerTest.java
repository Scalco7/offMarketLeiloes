package com.backend.offMarketLeiloes.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
class PropertyControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @BeforeEach
        void setUp() {
                jdbcTemplate.execute("DELETE FROM property");
                jdbcTemplate.execute("DELETE FROM property_address");

                insertProperty("Luxury Mansion", 5000000.0, 4000000.0, "SP", "São Paulo");
                insertProperty("Beach House", 1000000.0, 900000.0, "RJ", "Angra");
        }

        private void insertProperty(String name, Double valuedPrice, Double currentPrice, String state, String city) {
                UUID addressId = UUID.randomUUID();
                jdbcTemplate.update(
                                "INSERT INTO property_address (id, state, city, zip_code, country, street, number, neighborhood, created_at, updated_at) "
                                                +
                                                "VALUES (?, ?, ?, '00000', 'Brazil', 'Street', '1', 'Neighborhood', now(), now())",
                                addressId, state, city);

                jdbcTemplate.update(
                                "INSERT INTO property (id, name, description, valued_price, current_price, address_id, auction_date_time, auctioneer_name, auction_link, status, type, created_at, updated_at) "
                                                +
                                                "VALUES (random_uuid(), ?, 'Description', ?, ?, ?, now(), 'Auctioneer', 'http://link.com', 'ACTIVE', 'HOUSE', now(), now())",
                                name, valuedPrice, currentPrice, addressId);
        }

        @Test
        void shouldReturnRealDataFromH2() throws Exception {
                mockMvc.perform(get("/properties"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content").isArray())
                                .andExpect(jsonPath("$.totalElements").value(2));
        }

        @Test
        void shouldFailWhenPageIsNegative() throws Exception {
                mockMvc.perform(get("/properties")
                                .param("page", "-1"))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.page").value("A página deve ser maior ou igual a 0"));
        }

        @Test
        void shouldFailWhenPriceIsNegative() throws Exception {
                mockMvc.perform(get("/properties")
                                .param("minPrice", "-100"))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.minPrice").value("O preço mínimo não pode ser negativo"));
        }

        @Test
        void shouldFilterByCityAndReturnCorrectElements() throws Exception {
                mockMvc.perform(get("/properties")
                                .param("city", "Angra"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content[0].name").value("Beach House"))
                                .andExpect(jsonPath("$.totalElements").value(1));
        }

        @Test
        void shouldReturnEmptyPageForNonExistentFilter() throws Exception {
                mockMvc.perform(get("/properties")
                                .param("state", "MG"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content").isEmpty())
                                .andExpect(jsonPath("$.totalElements").value(0));
        }
}
