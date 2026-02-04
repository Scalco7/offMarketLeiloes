package com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyList {
    private UUID id;
    private String name;
    private String description;
    private Double valuedPrice;
    private Double currentPrice;
}
