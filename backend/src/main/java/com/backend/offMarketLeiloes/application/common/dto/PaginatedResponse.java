package com.backend.offMarketLeiloes.application.common.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Resposta paginada genérica")
public class PaginatedResponse<T> {
    @Schema(description = "Lista de elementos da página atual")
    private List<T> content;

    @Schema(description = "Página atual")
    private int page;

    @Schema(description = "Tamanho da página")
    private int pageSize;

    @Schema(description = "Total de elementos em todas as páginas")
    private long totalElements;

    @Schema(description = "Total de páginas")
    private int totalPages;
}
