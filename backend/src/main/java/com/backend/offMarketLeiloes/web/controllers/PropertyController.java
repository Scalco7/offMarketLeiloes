package com.backend.offMarketLeiloes.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.ListPropertiesQuery;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.dto.ListPropertiesFilters;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels.PropertyList;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/properties")
@Tag(name = "Imóveis", description = "Rotas para gerenciamento e visualização de imóveis.")
public class PropertyController {
    @Autowired
    private ListPropertiesQuery listPropertiesQuery;

    @Operation(summary = "Lista todos os imóveis disponíveis", description = "Retorna uma lista de imóveis cadastrados no sistema com informações básicas, permitindo filtragem.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de imóveis retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping
    public List<PropertyList> listProperties(
            @Parameter(description = "Filtros de busca") @ModelAttribute ListPropertiesFilters filters) {
        return listPropertiesQuery.execute(filters);
    }
}
