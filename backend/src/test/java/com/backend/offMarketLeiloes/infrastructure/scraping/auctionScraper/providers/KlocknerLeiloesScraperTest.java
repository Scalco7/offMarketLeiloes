package com.backend.offMarketLeiloes.infrastructure.scraping.auctionScraper.providers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.backend.offMarketLeiloes.application.features.properties.commands.createBatch.viewModels.CreatePropertyRequest;

@SpringBootTest
@ActiveProfiles("test")
class KlocknerLeiloesScraperTest {

    @Test
    void shouldScrapeProperties() {
        KlocknerLeiloesScraper scraper = new KlocknerLeiloesScraper();

        List<CreatePropertyRequest> result = scraper.scrape();

        assertNotNull(result);
    }

    @Test
    void shouldSupportKlocknerLeiloes() {
        KlocknerLeiloesScraper scraper = new KlocknerLeiloesScraper();
        assertTrue(scraper.supports("KlocknerLeiloes"));
        assertTrue(scraper.supports("klocknerleiloes"));
    }
}
