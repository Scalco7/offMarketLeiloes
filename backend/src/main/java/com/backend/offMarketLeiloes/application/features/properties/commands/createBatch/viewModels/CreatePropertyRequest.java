package com.backend.offMarketLeiloes.application.features.properties.commands.createBatch.viewModels;

import java.time.LocalDateTime;

import com.backend.offMarketLeiloes.application.common.validation.EnumValue;
import com.backend.offMarketLeiloes.domain.enums.EPropertyStatus;
import com.backend.offMarketLeiloes.domain.enums.EPropertyType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePropertyRequest {
    @Schema(description = "Nome do imóvel", example = "Apartamento de Luxo")
    @NotBlank(message = "O nome é obrigatório")
    private String name;

    @Schema(description = "Descrição do imóvel", example = "Apartamento com 3 quartos e vista para o mar")
    @NotBlank(message = "A descrição é obrigatória")
    private String description;

    @Schema(description = "Preço avaliado do imóvel", example = "500000.0")
    @NotNull(message = "O preço avaliado é obrigatório")
    private Double valuedPrice;

    @Schema(description = "Preço atual (lance mínimo) do imóvel", example = "350000.0")
    @NotNull(message = "O preço atual é obrigatório")
    private Double currentPrice;

    @Schema(description = "Data e hora do leilão", example = "2024-12-31T14:30:00")
    @NotNull(message = "A data do leilão é obrigatória")
    private LocalDateTime auctionDateTime;

    @Schema(description = "Nome do leiloeiro", example = "Leiloeiro Oficial")
    @NotBlank(message = "O nome do leiloeiro é obrigatório")
    private String auctioneerName;

    @Schema(description = "Link para o site do leiloeiro", example = "https://leiloeiro.com.br/imovel/123")
    @NotBlank(message = "O link do leilão é obrigatório")
    private String auctionLink;

    @Schema(description = "Link para a imagem do imóvel", example = "https://leiloeiro.com.br/foto1.jpg")
    private String imageLink;

    @Schema(description = "Tipo do imóvel", example = "APARTMENT")
    @NotBlank(message = "O tipo do imóvel é obrigatório")
    @EnumValue(enumClass = EPropertyType.class)
    private String type;

    @Schema(description = "Status do leilão do imóvel", example = "OPEN")
    @NotBlank(message = "O status do imóvel é obrigatório")
    @EnumValue(enumClass = EPropertyStatus.class)
    private String status;

    @Schema(description = "Endereço do imóvel")
    @Valid
    @NotNull(message = "O endereço é obrigatório")
    private CreatePropertyAddressRequest address;
}
