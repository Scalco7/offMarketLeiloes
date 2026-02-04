package com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representação simplificada de um imóvel para listagem.")
public class PropertyList {
    @Schema(description = "Identificador único do imóvel", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Nome do imóvel", example = "Apartamento de Luxo no Jardins")
    private String name;

    @Schema(description = "Descrição detalhada do imóvel", example = "Excelente apartamento com 3 quartos e vista para o parque.")
    private String description;

    @Schema(description = "Preço de avaliação do imóvel", example = "1500000.00")
    private Double valuedPrice;

    @Schema(description = "Preço atual de mercado ou lance inicial", example = "900000.00")
    private Double currentPrice;
}
