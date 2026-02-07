package com.backend.offMarketLeiloes.web.controllers;

import com.backend.offMarketLeiloes.application.features.favorites.commands.addFavorite.AddFavoriteCommand;
import com.backend.offMarketLeiloes.application.features.favorites.commands.addFavorite.viewModels.AddFavoriteRequest;
import com.backend.offMarketLeiloes.application.features.favorites.commands.removeFavorite.RemoveFavoriteCommand;
import com.backend.offMarketLeiloes.application.features.favorites.commands.removeFavorite.viewModels.RemoveFavoriteRequest;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Adiciona um imóvel aos favoritos", description = "Adiciona a propriedade especificada à lista de favoritos do usuário logado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imóvel adicionado aos favoritos com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou imóvel já favoritado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado ou token inválido"),
            @ApiResponse(responseCode = "404", description = "Imóvel não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addFavorite(@RequestBody @Valid AddFavoriteRequest request) {
        addFavoriteCommand.execute(request);
    }

    @Operation(summary = "Remove um imóvel dos favoritos", description = "Remove a propriedade especificada da lista de favoritos do usuário logado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Imóvel removido dos favoritos com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou imóvel não está nos favoritos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado ou token inválido"),
            @ApiResponse(responseCode = "404", description = "Imóvel não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFavorite(@RequestBody @Valid RemoveFavoriteRequest request) {
        removeFavoriteCommand.execute(request);
    }
}
