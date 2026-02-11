package com.backend.offMarketLeiloes.application.features.scraper.command.startScraper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.backend.offMarketLeiloes.application.features.scraper.command.startScraper.viewModels.StartScraperRequest;
import com.backend.offMarketLeiloes.application.features.scraper.command.startScraper.viewModels.StartScraperResponse;
import com.backend.offMarketLeiloes.application.features.scraper.service.ScraperOrchestrator;
import com.backend.offMarketLeiloes.domain.entities.ScraperJob;
import com.backend.offMarketLeiloes.domain.enums.EScraperJobStatus;
import com.backend.offMarketLeiloes.domain.enums.EScraperSites;
import com.backend.offMarketLeiloes.domain.repositories.ScraperJobRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StartScraperCommand {
    private final ScraperJobRepository scraperJobRepository;
    private final ScraperOrchestrator scraperOrchestrator;

    public StartScraperResponse execute(StartScraperRequest request) {
        ScraperJob job = new ScraperJob();
        EScraperSites siteEnum = EScraperSites.valueOf(request.getScraper());
        job.setScraper(siteEnum);
        job.setStatus(EScraperJobStatus.RUNNING);
        job.setStartTime(LocalDateTime.now());

        job = scraperJobRepository.save(job);

        scraperOrchestrator.runScraping(job.getId(), siteEnum);

        return new StartScraperResponse(job.getId(), job.getScraper().name(), job.getStatus().name());
    }
}
