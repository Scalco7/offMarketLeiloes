package com.backend.offMarketLeiloes.application.features.favorites.queries.listFavorites;

import com.backend.offMarketLeiloes.application.common.dto.PaginatedResponse;
import com.backend.offMarketLeiloes.application.features.favorites.queries.listFavorites.dto.ListFavoritePropertiesFilters;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels.PropertyList;
import com.backend.offMarketLeiloes.domain.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListFavoritePropertiesQuery {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public PaginatedResponse<PropertyList> execute(ListFavoritePropertiesFilters filters) {
        Account currentAccount = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        StringBuilder filterSql = new StringBuilder(" WHERE fp.account_id = :accountId");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("accountId", currentAccount.getId());

        if (filters.getName() != null && !filters.getName().isBlank()) {
            filterSql.append(" AND p.name ILIKE :name");
            params.addValue("name", "%" + filters.getName() + "%");
        }

        String countSql = "SELECT COUNT(*) FROM favorite_property fp " +
                "INNER JOIN property p ON fp.property_id = p.id" + filterSql;

        long totalElements = jdbcTemplate.queryForObject(countSql, params, Long.class);

        String orderBySql = " ORDER BY fp.created_at DESC";
        if (filters.getSortByPrice() != null && !filters.getSortByPrice().isBlank()) {
            if (filters.getSortByPrice().equalsIgnoreCase("asc")) {
                orderBySql = " ORDER BY p.current_price ASC";
            } else if (filters.getSortByPrice().equalsIgnoreCase("desc")) {
                orderBySql = " ORDER BY p.current_price DESC";
            }
        }

        String dataSql = "SELECT p.id, p.name, p.description, p.valued_price as valuedPrice, p.current_price as currentPrice, "
                +
                "p.auction_date_time as auctionDateTime, p.auctioneer_name as auctioneerName, p.auction_link as auctionLink, "
                +
                "p.image_link as imageLink, p.type, p.status, " +
                "ROUND(CAST(((p.valued_price - p.current_price) / NULLIF(p.valued_price, 0)) * 100 AS numeric), 2) as discount, "
                +
                "pa.zip_code as zipCode, pa.city, pa.state, pa.street, pa.number, pa.neighborhood " +
                "FROM favorite_property fp " +
                "INNER JOIN property p ON fp.property_id = p.id " +
                "LEFT JOIN property_address pa ON p.address_id = pa.id" + filterSql +
                orderBySql +
                " LIMIT :limit OFFSET :offset";

        int limit = filters.getPageSize();
        int offset = (filters.getPage() - 1) * filters.getPageSize();
        params.addValue("limit", limit);
        params.addValue("offset", offset);

        List<PropertyList> content = jdbcTemplate.query(dataSql, params, (rs, rowNum) -> {
            PropertyList property = new PropertyList();
            property.setId(UUID.fromString(rs.getString("id")));
            property.setName(rs.getString("name"));
            property.setDescription(rs.getString("description"));
            property.setValuedPrice(rs.getDouble("valuedPrice"));
            property.setCurrentPrice(rs.getDouble("currentPrice"));
            property.setDiscount(rs.getDouble("discount"));
            property.setAuctionDateTime(
                    rs.getTimestamp("auctionDateTime") != null ? rs.getTimestamp("auctionDateTime").toLocalDateTime()
                            : null);
            property.setAuctioneerName(rs.getString("auctioneerName"));
            property.setAuctionLink(rs.getString("auctionLink"));
            property.setImageLink(rs.getString("imageLink"));
            property.setIsFavorite(true);

            String typeStr = rs.getString("type");
            if (typeStr != null) {
                try {
                    property.setType(com.backend.offMarketLeiloes.domain.enums.EPropertyType.valueOf(typeStr));
                } catch (IllegalArgumentException e) {
                }
            }

            String statusStr = rs.getString("status");
            if (statusStr != null) {
                try {
                    property.setStatus(com.backend.offMarketLeiloes.domain.enums.EPropertyStatus.valueOf(statusStr));
                } catch (IllegalArgumentException e) {
                }
            }

            PropertyList.PropertyAddressList address = new PropertyList.PropertyAddressList();
            address.setZipCode(rs.getString("zipCode"));
            address.setCity(rs.getString("city"));
            address.setState(rs.getString("state"));
            address.setStreet(rs.getString("street"));
            address.setNumber(rs.getString("number"));
            address.setNeighborhood(rs.getString("neighborhood"));
            property.setAddress(address);

            return property;
        });

        int totalPages = (int) Math.ceil((double) totalElements / limit);

        return new PaginatedResponse<>(content, filters.getPage(), limit, totalElements, totalPages);
    }
}
