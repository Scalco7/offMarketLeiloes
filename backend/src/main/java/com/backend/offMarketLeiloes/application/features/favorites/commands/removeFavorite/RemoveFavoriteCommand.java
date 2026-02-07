package com.backend.offMarketLeiloes.application.features.favorites.commands.removeFavorite;

import com.backend.offMarketLeiloes.application.features.favorites.commands.removeFavorite.viewModels.RemoveFavoriteRequest;
import com.backend.offMarketLeiloes.domain.entities.Account;
import com.backend.offMarketLeiloes.domain.entities.Property;
import com.backend.offMarketLeiloes.domain.repositories.FavoritePropertyRepository;
import com.backend.offMarketLeiloes.domain.repositories.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoveFavoriteCommand {

    private final FavoritePropertyRepository favoritePropertyRepository;
    private final PropertyRepository propertyRepository;

    @Transactional
    public void execute(RemoveFavoriteRequest request) {
        Account currentAccount = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Imóvel não encontrado"));

        if (!favoritePropertyRepository.existsByAccountAndProperty(currentAccount, property)) {
            throw new RuntimeException("Este imóvel não está nos seus favoritos");
        }

        favoritePropertyRepository.deleteByAccountAndProperty(currentAccount, property);
    }
}
