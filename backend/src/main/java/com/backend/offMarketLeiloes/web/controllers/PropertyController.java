package com.backend.offMarketLeiloes.web.controllers;

import com.backend.offMarketLeiloes.web.advice.StandardError;
import com.backend.offMarketLeiloes.web.advice.ValidationError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.offMarketLeiloes.application.common.dto.PaginatedResponse;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.ListPropertiesQuery;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels.ListPropertiesFilters;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels.PropertyList;
import com.backend.offMarketLeiloes.application.features.properties.queries.listAvailableStates.ListAvailableStatesQuery;
import com.backend.offMarketLeiloes.application.features.properties.queries.listAvailableStates.viewModels.AvailableState;
import com.backend.offMarketLeiloes.application.features.properties.commands.createBatch.CreatePropertiesBatchCommand;
import com.backend.offMarketLeiloes.application.features.properties.commands.createBatch.viewModels.CreatePropertyRequest;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/properties")
@Tag(name = "Imóveis", description = "Endpoints para visualização e busca de propriedades (Imóveis).")
public class PropertyController {

    @Autowired
    private ListPropertiesQuery listPropertiesQuery;

    @Autowired
    private ListAvailableStatesQuery listAvailableStatesQuery;

    @Autowired
    private CreatePropertiesBatchCommand createPropertiesBatchCommand;

    @Operation(summary = "Retorna os estados disponíveis e a quantidade de imóveis", description = "Retorna uma lista de estados que possuem imóveis ativos e a contagem de imóveis em cada um.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso", content = @Content(schema = @Schema(implementation = AvailableState.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping("/states")
    public List<AvailableState> listAvailableStates() {
        return listAvailableStatesQuery.execute();
    }

    @Operation(summary = "Consulta de imóveis com filtros", description = "Retorna uma página de imóveis baseada nos filtros fornecidos. Esta rota é pública.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pesquisa realizada com sucesso", content = @Content(schema = @Schema(implementation = PaginatedResponse.class))),
            @ApiResponse(responseCode = "422", description = "Parâmetros de busca ou paginação inválidos", content = @Content(schema = @Schema(implementation = ValidationError.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping
    public PaginatedResponse<PropertyList> listProperties(
            @Parameter(description = "Filtros de busca (ID, nome, cidade, etc.) e parâmetros de paginação (page, size)") @Valid @ModelAttribute ListPropertiesFilters filters) {
        return listPropertiesQuery.execute(filters);
    }

    @Operation(summary = "Criação de imóveis em lote", description = "Recebe uma lista de imóveis e os cria no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imóveis criados com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos fornecidos", content = @Content(schema = @Schema(implementation = ValidationError.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping("/batch")
    public void createPropertiesBatch(@RequestBody @Valid List<CreatePropertyRequest> requests) {
        try {
            createPropertiesBatchCommand.execute(requests);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
