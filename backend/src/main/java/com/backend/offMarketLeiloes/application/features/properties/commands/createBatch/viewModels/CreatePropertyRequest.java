package com.backend.offMarketLeiloes.application.features.properties.commands.createBatch.viewModels;

import java.time.LocalDateTime;

import com.backend.offMarketLeiloes.domain.enums.EPropertyStatus;
import com.backend.offMarketLeiloes.domain.enums.EPropertyType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePropertyRequest {
    private String name;
    private String description;
    private Double valuedPrice;
    private Double currentPrice;
    private LocalDateTime auctionDateTime;
    private String auctioneerName;
    private String auctionLink;
    private String imageLink;
    private EPropertyType type;
    private EPropertyStatus status;
    private CreatePropertyAddressRequest address;
}
