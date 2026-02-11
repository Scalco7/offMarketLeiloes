package com.backend.offMarketLeiloes.application.features.scraper.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.offMarketLeiloes.application.features.properties.commands.createBatch.CreatePropertiesBatchCommand;
import com.backend.offMarketLeiloes.domain.entities.ScraperJob;
import com.backend.offMarketLeiloes.domain.enums.EScraperJobStatus;
import com.backend.offMarketLeiloes.domain.enums.EScraperSites;
import com.backend.offMarketLeiloes.domain.repositories.ScraperJobRepository;
import com.backend.offMarketLeiloes.infrastructure.scraping.auctionScraper.AuctionScraper;

@ExtendWith(MockitoExtension.class)
class ScraperOrchestratorTest {

    @Mock
    private AuctionScraper scraper;

    @Mock
    private ScraperJobRepository scraperJobRepository;

    @Mock
    private CreatePropertiesBatchCommand createPropertiesBatchCommand;

    private ScraperOrchestrator scraperOrchestrator;

    @BeforeEach
    void setUp() {
        scraperOrchestrator = new ScraperOrchestrator(List.of(scraper), scraperJobRepository,
                createPropertiesBatchCommand);
    }

    @Test
    void shouldRunScrapingSuccessfully() throws Exception {
        UUID jobId = UUID.randomUUID();
        EScraperSites site = EScraperSites.KLOCKNER_LEILOES;
        ScraperJob job = new ScraperJob();
        job.setId(jobId);

        when(scraper.supports(site)).thenReturn(true);
        when(scraper.scrape()).thenReturn(List.of());
        when(scraperJobRepository.findById(jobId)).thenReturn(Optional.of(job));

        scraperOrchestrator.runScraping(jobId, site);

        verify(scraper).scrape();
        verify(createPropertiesBatchCommand).execute(any());
        verify(scraperJobRepository).save(any(ScraperJob.class));
        assert job.getStatus() == EScraperJobStatus.COMPLETED;
    }

    @Test
    void shouldHandleScrapingFailure() throws Exception {
        UUID jobId = UUID.randomUUID();
        EScraperSites site = EScraperSites.KLOCKNER_LEILOES;
        ScraperJob job = new ScraperJob();
        job.setId(jobId);

        when(scraper.supports(site)).thenReturn(true);
        when(scraper.scrape()).thenThrow(new RuntimeException("Scrape error"));
        when(scraperJobRepository.findById(jobId)).thenReturn(Optional.of(job));

        scraperOrchestrator.runScraping(jobId, site);

        verify(scraperJobRepository).save(any(ScraperJob.class));
        assert job.getStatus() == EScraperJobStatus.FAILED;
        assert job.getErrorMessage().equals("Scrape error");
    }
}
