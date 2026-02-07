package com.backend.offMarketLeiloes.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.offMarketLeiloes.application.common.dto.PaginatedResponse;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.ListPropertiesQuery;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels.ListPropertiesFilters;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels.PropertyList;

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

    @Operation(summary = "Consulta de imóveis com filtros", description = "Retorna uma página de imóveis baseada nos filtros fornecidos. Esta rota é pública.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pesquisa realizada com sucesso", content = @Content(schema = @Schema(implementation = PaginatedResponse.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros de busca ou paginação inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping
    public PaginatedResponse<PropertyList> listProperties(
            @Parameter(description = "Filtros de busca (ID, nome, cidade, etc.) e parâmetros de paginação (page, size)") @Valid @ModelAttribute ListPropertiesFilters filters) {
        return listPropertiesQuery.execute(filters);
    }
}
