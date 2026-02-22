package com.backend.offMarketLeiloes.application.features.scraper.command.startScraper.viewModels;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta do início de um job de scraper")
public class StartScraperResponse {
    @Schema(description = "ID do job criado", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID jobId;

    @Schema(description = "Site do scraper", example = "KLOCKNER_LEILOES")
    private String scraper;

    @Schema(description = "Status inicial do job", example = "RUNNING")
    private String status;
}
