package com.backend.offMarketLeiloes.application.features.scraper.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.backend.offMarketLeiloes.application.features.properties.commands.createBatch.CreatePropertiesBatchCommand;
import com.backend.offMarketLeiloes.application.features.properties.commands.createBatch.viewModels.CreatePropertyRequest;
import com.backend.offMarketLeiloes.application.features.scraper.events.ScrapingCompletedEvent;
import com.backend.offMarketLeiloes.domain.enums.EScraperJobStatus;
import com.backend.offMarketLeiloes.domain.enums.EScraperSites;
import com.backend.offMarketLeiloes.domain.repositories.ScraperJobRepository;
import com.backend.offMarketLeiloes.infrastructure.scraping.auctionScraper.AuctionScraper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScraperOrchestrator {
    private final List<AuctionScraper> scrapers;
    private final ScraperJobRepository scraperJobRepository;
    private final CreatePropertiesBatchCommand createPropertiesBatchCommand;
    private final ApplicationEventPublisher eventPublisher;

    @Async("threadPoolTaskExecutor")
    public void runScraping(UUID jobId, EScraperSites scraperEnum) {
        try {
            AuctionScraper scraper = scrapers.stream()
                    .filter(s -> s.supports(scraperEnum))
                    .findFirst()
                    .orElseThrow();

            List<CreatePropertyRequest> results = scraper.scrape();

            createPropertiesBatchCommand.execute(results);

            updateJobStatus(jobId, EScraperJobStatus.COMPLETED);

            eventPublisher.publishEvent(new ScrapingCompletedEvent(this, jobId, scraperEnum, results.size()));

        } catch (Exception e) {

            updateJobStatus(jobId, EScraperJobStatus.FAILED, e.getMessage());
            System.out.println("\n\n================== Erro ao executar o scraping: ==================");
            System.out.println(e.getMessage());
            System.out.println("==================");
        }
    }

    private void updateJobStatus(UUID id, EScraperJobStatus status) {
        scraperJobRepository.findById(id).ifPresent(job -> {
            job.setStatus(status);
            job.setEndTime(LocalDateTime.now());
            scraperJobRepository.save(job);
        });
    }

    private void updateJobStatus(UUID id, EScraperJobStatus status, String errorMessage) {
        scraperJobRepository.findById(id).ifPresent(job -> {
            job.setStatus(status);
            job.setEndTime(LocalDateTime.now());
            job.setErrorMessage(errorMessage);
            scraperJobRepository.save(job);
        });
    }
}
