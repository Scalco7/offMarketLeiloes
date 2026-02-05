package com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Filtros para busca de imóveis")
public class ListPropertiesFilters {
    @Schema(description = "Nome do imóvel", example = "Apartamento")
    private String name;

    @Schema(description = "Preço mínimo", example = "100000.0")
    private Double minPrice;

    @Schema(description = "Preço máximo", example = "500000.0")
    private Double maxPrice;

    @Schema(description = "Estado (UF)", example = "SP")
    private String state;

    @Schema(description = "Cidade", example = "São Paulo")
    private String city;
}
