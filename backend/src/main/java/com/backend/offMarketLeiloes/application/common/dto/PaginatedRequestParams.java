package com.backend.offMarketLeiloes.application.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedRequestParams {
    @Schema(description = "Página atual (inicia em 0)", example = "0")
    private Integer page = 0;

    @Schema(description = "Quantidade de itens por página", example = "10")
    private Integer pageSize = 10;
}
