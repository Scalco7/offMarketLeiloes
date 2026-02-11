package com.backend.offMarketLeiloes.domain.enums;

public enum EPropertyStatus {
    ACTIVE,
    SOLD,
    CANCELLED;

    public static EPropertyStatus parsePropertyStatus(String statusStr) throws IllegalArgumentException {
        switch (statusStr.toLowerCase()) {
            case "vendido":
            case "arrematado":
            case "sold":
                return EPropertyStatus.SOLD;
            case "cancelado":
            case "sustado":
            case "negativo":
            case "cancelled":
                return EPropertyStatus.CANCELLED;
            case "ativo":
            case "aberto para lances":
                return EPropertyStatus.ACTIVE;
            default:
                throw new IllegalArgumentException("Tipo de imóvel inválido: " + statusStr);
        }
    }
}
