package com.backend.offMarketLeiloes.web.controllers;

import com.backend.offMarketLeiloes.web.advice.StandardError;
import com.backend.offMarketLeiloes.web.advice.ValidationError;

import com.backend.offMarketLeiloes.application.features.favorites.commands.addFavorite.AddFavoriteCommand;
import com.backend.offMarketLeiloes.application.features.favorites.commands.addFavorite.viewModels.AddFavoriteRequest;
import com.backend.offMarketLeiloes.application.features.favorites.commands.removeFavorite.RemoveFavoriteCommand;
import com.backend.offMarketLeiloes.application.features.favorites.commands.removeFavorite.viewModels.RemoveFavoriteRequest;
import com.backend.offMarketLeiloes.application.features.favorites.queries.listFavorites.ListFavoritePropertiesQuery;
import com.backend.offMarketLeiloes.application.features.favorites.queries.listFavorites.dto.ListFavoritePropertiesFilters;
import com.backend.offMarketLeiloes.application.common.dto.PaginatedResponse;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels.PropertyList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
@Tag(name = "Favoritos", description = "Endpoints para gerenciamento de imóveis favoritos.")
public class FavoritePropertyController {

    private final AddFavoriteCommand addFavoriteCommand;
    private final RemoveFavoriteCommand removeFavoriteCommand;
    private final ListFavoritePropertiesQuery listFavoritePropertiesQuery;

    @Operation(summary = "Lista os imóveis favoritos do usuário", description = "Retorna uma página de imóveis favoritados pelo usuário logado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de favoritos retornada com sucesso", content = @Content(schema = @Schema(implementation = PaginatedResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autorizado ou token inválido", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping
    public PaginatedResponse<PropertyList> listFavorites(
            @Parameter(description = "Filtros de busca (nome) e parâmetros de paginação (page, size)") @Valid @ModelAttribute ListFavoritePropertiesFilters filters) {
        return listFavoritePropertiesQuery.execute(filters);
    }

    @Operation(summary = "Adiciona um imóvel aos favoritos", description = "Adiciona a propriedade especificada à lista de favoritos do usuário logado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imóvel adicionado aos favoritos com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos ou imóvel já favoritado", content = @Content(schema = @Schema(implementation = ValidationError.class))),
            @ApiResponse(responseCode = "401", description = "Não autorizado ou token inválido", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "404", description = "Imóvel não encontrado", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping
    public void addFavorite(@RequestBody @Valid AddFavoriteRequest request) {
        addFavoriteCommand.execute(request);
    }

    @Operation(summary = "Remove um imóvel dos favoritos", description = "Remove a propriedade especificada da lista de favoritos do usuário logado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Imóvel removido dos favoritos com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou imóvel não está nos favoritos", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "401", description = "Não autorizado ou token inválido", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "404", description = "Imóvel não encontrado", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFavorite(@RequestBody @Valid RemoveFavoriteRequest request) {
        removeFavoriteCommand.execute(request);
    }
}
