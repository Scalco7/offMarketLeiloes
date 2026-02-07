package com.backend.offMarketLeiloes.application.features.favorites.commands.removeFavorite.viewModels;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "Dados para remover um imóvel dos favoritos")
public class RemoveFavoriteRequest {
    @NotNull(message = "O ID da propriedade é obrigatório")
    @Schema(description = "ID do imóvel a ser removido dos favoritos", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID propertyId;
}
