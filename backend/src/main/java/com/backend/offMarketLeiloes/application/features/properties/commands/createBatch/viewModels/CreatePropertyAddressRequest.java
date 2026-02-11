package com.backend.offMarketLeiloes.application.features.properties.commands.createBatch.viewModels;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePropertyAddressRequest {
    @Schema(description = "CEP do imóvel", example = "88010-000")
    private String zipCode;

    @Schema(description = "Cidade do imóvel", example = "Florianópolis")
    private String city;

    @Schema(description = "Estado do imóvel", example = "SC")
    @NotBlank(message = "O estado é obrigatório")
    private String state;

    @Schema(description = "País do imóvel", example = "Brasil")
    @NotBlank(message = "O país é obrigatório")
    private String country;

    @Schema(description = "Rua do imóvel", example = "Rua XV de Novembro")
    private String street;

    @Schema(description = "Número do imóvel", example = "123")
    private String number;

    @Schema(description = "Bairro do imóvel", example = "Centro")
    private String neighborhood;

    @Schema(description = "Complemento do imóvel", example = "Apto 101")
    private String complement;
}
