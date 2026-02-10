package com.backend.offMarketLeiloes.application.features.properties.commands.createBatch.viewModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePropertyAddressRequest {
    private String zipCode;
    private String city;
    private String state;
    private String country;
    private String street;
    private String number;
    private String neighborhood;
    private String complement;
}
