package com.backend.offMarketLeiloes.application.features.scraper.command.startScraper.viewModels;

import com.backend.offMarketLeiloes.application.common.validation.EnumValue;
import com.backend.offMarketLeiloes.domain.enums.EScraperSites;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StartScraperRequest {
    @Schema(description = "Site que deve ser escaneado", example = "KLOCKNER_LEILOES")
    @NotBlank(message = "O site do scraper é obrigatório")
    @EnumValue(enumClass = EScraperSites.class)
    private String scraper;
}
