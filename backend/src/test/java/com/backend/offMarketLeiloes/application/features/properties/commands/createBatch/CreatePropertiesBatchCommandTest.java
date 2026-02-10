package com.backend.offMarketLeiloes.application.features.properties.commands.createBatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.backend.offMarketLeiloes.application.features.properties.commands.createBatch.viewModels.CreatePropertyAddressRequest;
import com.backend.offMarketLeiloes.application.features.properties.commands.createBatch.viewModels.CreatePropertyRequest;
import com.backend.offMarketLeiloes.domain.entities.Property;
import com.backend.offMarketLeiloes.domain.enums.EPropertyStatus;
import com.backend.offMarketLeiloes.domain.enums.EPropertyType;
import com.backend.offMarketLeiloes.domain.repositories.PropertyRepository;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CreatePropertiesBatchCommandTest {

    @Autowired
    private CreatePropertiesBatchCommand createPropertiesBatchCommand;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM property");
        jdbcTemplate.execute("DELETE FROM property_address");
    }

    @Test
    void shouldCreatePropertiesInBatch() {
        CreatePropertyAddressRequest addressRequest1 = CreatePropertyAddressRequest.builder()
                .zipCode("12345678")
                .city("São Paulo")
                .state("SP")
                .country("Brasil")
                .street("Rua Teste 1")
                .number("123")
                .neighborhood("Bairro Teste 1")
                .build();

        CreatePropertyAddressRequest addressRequest2 = CreatePropertyAddressRequest.builder()
                .zipCode("87654321")
                .city("Rio de Janeiro")
                .state("RJ")
                .country("Brasil")
                .street("Rua Teste 2")
                .number("456")
                .neighborhood("Bairro Teste 2")
                .build();

        CreatePropertyRequest propertyRequest1 = CreatePropertyRequest.builder()
                .name("Property 1")
                .description("Description 1")
                .valuedPrice(100000.0)
                .currentPrice(80000.0)
                .auctionDateTime(LocalDateTime.now().plusDays(1))
                .auctioneerName("Auctioneer 1")
                .auctionLink("http://link1.com")
                .type(EPropertyType.HOUSE)
                .status(EPropertyStatus.ACTIVE)
                .address(addressRequest1)
                .build();

        CreatePropertyRequest propertyRequest2 = CreatePropertyRequest.builder()
                .name("Property 2")
                .description("Description 2")
                .valuedPrice(200000.0)
                .currentPrice(150000.0)
                .auctionDateTime(LocalDateTime.now().plusDays(2))
                .auctioneerName("Auctioneer 2")
                .auctionLink("http://link2.com")
                .type(EPropertyType.APARTMENT)
                .status(EPropertyStatus.ACTIVE)
                .address(addressRequest2)
                .build();

        createPropertiesBatchCommand.execute(List.of(propertyRequest1, propertyRequest2));

        List<Property> savedProperties = propertyRepository.findAll();

        assertEquals(2, savedProperties.size());

        Property p1 = savedProperties.stream().filter(p -> p.getName().equals("Property 1")).findFirst().orElse(null);
        assertNotNull(p1);
        assertNotNull(p1.getAddress());
        assertEquals("São Paulo", p1.getAddress().getCity());
        assertEquals(p1, p1.getAddress().getProperty());

        Property p2 = savedProperties.stream().filter(p -> p.getName().equals("Property 2")).findFirst().orElse(null);
        assertNotNull(p2);
        assertNotNull(p2.getAddress());
        assertEquals("São Paulo", p2.getAddress().getCity());
        assertEquals(p2, p2.getAddress().getProperty());
    }

    @Test
    void shouldDoNothingWhenListIsEmpty() {
        createPropertiesBatchCommand.execute(List.of());
        List<Property> savedProperties = propertyRepository.findAll();
        assertEquals(0, savedProperties.size());
    }
}
