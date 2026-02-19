package com.backend.offMarketLeiloes.application.features.properties.queries.listAvailableStates.viewModels;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa um estado disponível e a quantidade de imóveis nele")
public class AvailableState {
    @Schema(description = "Sigla ou nome do estado", example = "SP")
    private String state;

    @Schema(description = "Quantidade de imóveis disponíveis no estado", example = "15")
    private Long propertyCount;
}
