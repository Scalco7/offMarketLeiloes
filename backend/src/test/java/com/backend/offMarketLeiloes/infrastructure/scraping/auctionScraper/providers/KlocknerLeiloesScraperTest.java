package com.backend.offMarketLeiloes.infrastructure.scraping.auctionScraper.providers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.backend.offMarketLeiloes.application.features.properties.commands.createBatch.viewModels.CreatePropertyRequest;
import com.backend.offMarketLeiloes.domain.enums.EScraperSites;

@SpringBootTest
@ActiveProfiles("test")
class KlocknerLeiloesScraperTest {

    @org.junit.jupiter.api.Disabled("Teste pesado que acessa dados reais")
    @Test
    void shouldScrapeProperties() {
        KlocknerLeiloesScraper scraper = new KlocknerLeiloesScraper();

        List<CreatePropertyRequest> result = scraper.scrape();

        assertNotNull(result);
    }

    @Test
    void shouldSupportKlocknerLeiloes() {
        KlocknerLeiloesScraper scraper = new KlocknerLeiloesScraper();
        assertTrue(scraper.supports(EScraperSites.KLOCKNER_LEILOES));
    }
}
