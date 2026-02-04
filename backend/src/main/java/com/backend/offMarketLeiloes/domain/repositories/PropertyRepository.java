package com.backend.offMarketLeiloes.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.offMarketLeiloes.domain.entities.Property;

public interface PropertyRepository extends JpaRepository<Property, UUID> {

}
