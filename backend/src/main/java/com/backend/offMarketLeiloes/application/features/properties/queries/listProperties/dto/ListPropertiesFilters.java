package com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.dto;

import com.backend.offMarketLeiloes.application.common.dto.PaginatedRequestParams;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Filtros para busca de imóveis")
public class ListPropertiesFilters extends PaginatedRequestParams {
    @Schema(description = "Nome do imóvel", example = "Apartamento")
    private String name;

    @Min(value = 0, message = "O preço mínimo não pode ser negativo")
    @Schema(description = "Preço mínimo", example = "100000.0")
    private Double minPrice;

    @Min(value = 0, message = "O preço máximo não pode ser negativo")
    @Schema(description = "Preço máximo", example = "500000.0")
    private Double maxPrice;

    @Schema(description = "Estado (UF)", example = "SP")
    private String state;

    @Schema(description = "Cidade", example = "São Paulo")
    private String city;
}
