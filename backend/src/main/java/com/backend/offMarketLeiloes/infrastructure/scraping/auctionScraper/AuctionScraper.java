package com.backend.offMarketLeiloes.infrastructure.scraping.auctionScraper;

import java.util.List;

import com.backend.offMarketLeiloes.application.features.properties.commands.createBatch.viewModels.CreatePropertyRequest;
import com.backend.offMarketLeiloes.domain.enums.EScraperSites;

public interface AuctionScraper {
    List<CreatePropertyRequest> scrape();

    boolean supports(EScraperSites site);
}
