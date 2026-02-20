package com.backend.offMarketLeiloes.application.features.scraper.events;

import java.util.UUID;

import org.springframework.context.ApplicationEvent;

import com.backend.offMarketLeiloes.domain.enums.EScraperSites;

import lombok.Getter;

@Getter
public class ScrapingCompletedEvent extends ApplicationEvent {
    private final UUID jobId;
    private final EScraperSites scraperSite;
    private final int resultCount;

    public ScrapingCompletedEvent(Object source, UUID jobId, EScraperSites scraperSite, int resultCount) {
        super(source);
        this.jobId = jobId;
        this.scraperSite = scraperSite;
        this.resultCount = resultCount;
    }
}
