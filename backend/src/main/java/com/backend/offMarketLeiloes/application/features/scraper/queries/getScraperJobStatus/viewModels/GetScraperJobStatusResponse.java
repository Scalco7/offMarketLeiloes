package com.backend.offMarketLeiloes.application.features.scraper.queries.getScraperJobStatus.viewModels;

import java.time.LocalDateTime;
import java.util.UUID;

import com.backend.offMarketLeiloes.domain.enums.EScraperJobStatus;
import com.backend.offMarketLeiloes.domain.enums.EScraperSites;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Resposta detalhada com o status de um job de scraper")
public class GetScraperJobStatusResponse {
    @Schema(description = "ID do job", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Site do scraper", example = "KLOCKNER_LEILOES")
    private EScraperSites scraper;

    @Schema(description = "Status do job", example = "RUNNING")
    private EScraperJobStatus status;

    @Schema(description = "Data e hora de início", example = "2024-03-20T10:00:00")
    private LocalDateTime startTime;

    @Schema(description = "Data e hora de término", example = "2024-03-20T10:05:00")
    private LocalDateTime endTime;

    @Schema(description = "Mensagem de erro, se houver", example = "Erro ao conectar com o site")
    private String errorMessage;
}
