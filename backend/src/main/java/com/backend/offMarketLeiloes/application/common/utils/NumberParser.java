package com.backend.offMarketLeiloes.application.common.utils;

public class NumberParser {
    public static Double parsePrice(String price) throws NumberFormatException {
        String cleaned = price.replaceAll("[^0-9,]", "").replace(",", ".");
        return Double.parseDouble(cleaned);
    }
}
