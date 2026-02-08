package com.backend.offMarketLeiloes.application.features.favorites.queries.listFavorites.dto;

import com.backend.offMarketLeiloes.application.common.dto.PaginatedRequestParams;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Filtros para busca de imóveis favoritos")
public class ListFavoritePropertiesFilters extends PaginatedRequestParams {
    @Schema(description = "Busca pelo nome da propriedade", example = "Apartamento")
    private String name;

    @Schema(description = "Ordenação por preço (asc para menor preço, desc para maior preço)", example = "asc")
    private String sortByPrice;
}
