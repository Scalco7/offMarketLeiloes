package com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels;

import java.time.LocalDateTime;
import java.util.UUID;

import com.backend.offMarketLeiloes.domain.enums.EPropertyStatus;
import com.backend.offMarketLeiloes.domain.enums.EPropertyType;

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

    @Schema(description = "Data e hora do leilão", example = "2024-12-31T10:00:00")
    private LocalDateTime auctionDateTime;

    @Schema(description = "Nome do leiloeiro", example = "Leilões Brasil")
    private String auctioneerName;

    @Schema(description = "Link para o site do leiloeiro", example = "https://leiloes.com/123")
    private String auctionLink;

    @Schema(description = "Link da imagem de capa do imóvel", example = "https://images.com/property.jpg")
    private String imageLink;

    @Schema(description = "Tipo do imóvel", example = "HOUSE")
    private EPropertyType type;

    @Schema(description = "Status do imóvel", example = "ACTIVE")
    private EPropertyStatus status;

    @Schema(description = "Indica se o imóvel é favorito do usuário autenticado", example = "true")
    private Boolean isFavorite;

    @Schema(description = "Porcentagem de desconto entre o valor de avaliação e o valor atual", example = "40.0")
    private Double discount;

    @Schema(description = "Endereço do imóvel")
    private PropertyAddressList address;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PropertyAddressList {
        private String zipCode;
        private String city;
        private String state;
        private String street;
        private String number;
        private String neighborhood;
    }
}
