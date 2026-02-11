package com.backend.offMarketLeiloes.domain.enums;

public enum EScraperSites {
    KLOCKNER_LEILOES("Klockner Leiloes");

    private String name;

    EScraperSites(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
