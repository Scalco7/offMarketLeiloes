package com.backend.offMarketLeiloes.application.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedRequestParams {
    @Min(value = 1, message = "A página deve ser maior ou igual a 1")
    @Schema(description = "Página atual (inicia em 1)", example = "1")
    private Integer page = 1;

    @Min(value = 1, message = "O tamanho da página deve ser pelo menos 1")
    @Max(value = 100, message = "O tamanho da página não pode exceder 100")
    @Schema(description = "Quantidade de itens por página", example = "10")
    private Integer pageSize = 10;
}
