package com.backend.offMarketLeiloes.application.features.favorites.commands.addFavorite;

import com.backend.offMarketLeiloes.application.features.favorites.commands.addFavorite.viewModels.AddFavoriteRequest;
import com.backend.offMarketLeiloes.domain.entities.Account;
import com.backend.offMarketLeiloes.domain.entities.FavoriteProperty;
import com.backend.offMarketLeiloes.domain.entities.Property;
import com.backend.offMarketLeiloes.domain.repositories.FavoritePropertyRepository;
import com.backend.offMarketLeiloes.domain.repositories.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddFavoriteCommand {

    private final FavoritePropertyRepository favoritePropertyRepository;
    private final PropertyRepository propertyRepository;

    @Transactional
    public void execute(AddFavoriteRequest request) {
        Account currentAccount = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Imóvel não encontrado"));

        if (favoritePropertyRepository.existsByAccountAndProperty(currentAccount, property)) {
            throw new RuntimeException("Este imóvel já está nos seus favoritos");
        }

        FavoriteProperty favoriteProperty = new FavoriteProperty();
        favoriteProperty.setAccount(currentAccount);
        favoriteProperty.setProperty(property);

        favoritePropertyRepository.save(favoriteProperty);
    }
}
