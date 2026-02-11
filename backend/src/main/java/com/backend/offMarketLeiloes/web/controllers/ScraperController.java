package com.backend.offMarketLeiloes.web.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.offMarketLeiloes.application.features.scraper.command.startScraper.StartScraperCommand;
import com.backend.offMarketLeiloes.application.features.scraper.command.startScraper.viewModels.StartScraperRequest;
import com.backend.offMarketLeiloes.application.features.scraper.command.startScraper.viewModels.StartScraperResponse;
import com.backend.offMarketLeiloes.application.features.scraper.queries.getScraperJobStatus.GetScraperJobStatusQuery;
import com.backend.offMarketLeiloes.application.features.scraper.queries.getScraperJobStatus.viewModels.GetScraperJobStatusResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/scraper")
@Tag(name = "Scraper", description = "Endpoints para scraping de sites de leilões.")
public class ScraperController {

    @Autowired
    private StartScraperCommand startScraperCommand;

    @Autowired
    private GetScraperJobStatusQuery getScraperJobStatusQuery;

    @Operation(summary = "Inicia o scraper", description = "Inicia o scraper para raspar os sites de leilões.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Scraper iniciado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/start")
    public StartScraperResponse startScraper(@RequestBody @Valid StartScraperRequest request) {
        return startScraperCommand.execute(request);
    }

    @Operation(summary = "Busca o status do scraper", description = "Retorna o status de um job de scraper pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Job não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/{id}/status")
    public GetScraperJobStatusResponse getStatus(@PathVariable UUID id) {
        return getScraperJobStatusQuery.execute(id);
    }
}
