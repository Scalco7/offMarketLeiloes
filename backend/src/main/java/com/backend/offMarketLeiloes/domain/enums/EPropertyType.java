package com.backend.offMarketLeiloes.domain.enums;

public enum EPropertyType {
    APARTMENT,
    HOUSE,
    COMMERCIAL,
    LAND;

    public static EPropertyType parsePropertyType(String typeStr) throws IllegalArgumentException {
        switch (typeStr.toLowerCase()) {
            case "apartamento":
            case "apartamentos":
                return EPropertyType.APARTMENT;
            case "comercial":
            case "sala":
            case "predio":
                return EPropertyType.COMMERCIAL;
            case "terreno":
            case "lote":
                return EPropertyType.LAND;
            case "casas":
            case "casa":
                return EPropertyType.HOUSE;
            default:
                throw new IllegalArgumentException("Tipo de imóvel inválido: " + typeStr);
        }
    }
}
