package com.backend.offMarketLeiloes.domain.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.offMarketLeiloes.domain.entities.Account;
import com.backend.offMarketLeiloes.domain.entities.FavoriteProperty;
import com.backend.offMarketLeiloes.domain.entities.Property;

public interface FavoritePropertyRepository extends JpaRepository<FavoriteProperty, UUID> {
    List<FavoriteProperty> findByAccount(Account account);

    boolean existsByAccountAndProperty(Account account, Property property);

    void deleteByAccountAndProperty(Account account, Property property);
}
