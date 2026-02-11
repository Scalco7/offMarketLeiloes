package com.backend.offMarketLeiloes.application.features.properties.commands.createBatch;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.offMarketLeiloes.application.features.properties.commands.createBatch.viewModels.CreatePropertyRequest;
import com.backend.offMarketLeiloes.domain.entities.Property;
import com.backend.offMarketLeiloes.domain.entities.PropertyAddress;
import com.backend.offMarketLeiloes.domain.enums.EPropertyStatus;
import com.backend.offMarketLeiloes.domain.enums.EPropertyType;
import com.backend.offMarketLeiloes.domain.repositories.PropertyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreatePropertiesBatchCommand {

    private final PropertyRepository propertyRepository;

    @Transactional
    public void execute(List<CreatePropertyRequest> requests) throws Exception {
        if (requests == null || requests.isEmpty()) {
            return;
        }

        List<Property> properties = requests.stream()
                .collect(Collectors.toMap(
                        r -> r.getName() + "|" + r.getCurrentPrice() + "|" + r.getDescription(),
                        r -> r,
                        (existing, replacement) -> existing))
                .values().stream()
                .filter(request -> !propertyRepository.existsByNameAndCurrentPriceAndDescription(
                        request.getName(),
                        request.getCurrentPrice(),
                        request.getDescription()))
                .map(this::mapToEntity)
                .collect(Collectors.toList());

        if (!properties.isEmpty()) {
            propertyRepository.saveAll(properties);
        }
    }

    private Property mapToEntity(CreatePropertyRequest request) {
        Property property = new Property();
        property.setName(request.getName());
        property.setDescription(request.getDescription());
        property.setValuedPrice(request.getValuedPrice());
        property.setCurrentPrice(request.getCurrentPrice());
        property.setAuctionDateTime(request.getAuctionDateTime());
        property.setAuctioneerName(request.getAuctioneerName());
        property.setAuctionLink(request.getAuctionLink());
        property.setImageLink(request.getImageLink());
        property.setType(EPropertyType.valueOf(request.getType()));
        property.setStatus(EPropertyStatus.valueOf(request.getStatus()));

        if (request.getAddress() != null) {
            PropertyAddress address = new PropertyAddress();
            address.setZipCode(request.getAddress().getZipCode());
            address.setCity(request.getAddress().getCity());
            address.setState(request.getAddress().getState());
            address.setCountry(request.getAddress().getCountry());
            address.setStreet(request.getAddress().getStreet());
            address.setNumber(request.getAddress().getNumber());
            address.setNeighborhood(request.getAddress().getNeighborhood());
            address.setComplement(request.getAddress().getComplement());

            property.setAddress(address);
            address.setProperty(property);
        }

        return property;
    }
}
