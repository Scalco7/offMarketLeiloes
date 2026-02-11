package com.backend.offMarketLeiloes.infrastructure.scraping.auctionScraper;

import java.util.List;

import com.backend.offMarketLeiloes.application.features.properties.commands.createBatch.viewModels.CreatePropertyRequest;

public interface AuctionScraper {
    List<CreatePropertyRequest> scrape();

    boolean supports(String siteName);
}
