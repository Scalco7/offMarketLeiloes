package com.backend.offMarketLeiloes.application.features.properties.queries.listProperties;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.backend.offMarketLeiloes.application.common.dto.PaginatedResponse;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels.ListPropertiesFilters;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels.PropertyList;
import com.backend.offMarketLeiloes.domain.enums.EPropertyStatus;
import com.backend.offMarketLeiloes.domain.enums.EPropertyType;
import com.backend.offMarketLeiloes.domain.entities.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class ListPropertiesQuery {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public PaginatedResponse<PropertyList> execute(ListPropertiesFilters filters) {
        StringBuilder filterSql = new StringBuilder(" WHERE 1=1");
        MapSqlParameterSource params = new MapSqlParameterSource();

        if (filters.getName() != null && !filters.getName().isBlank()) {
            filterSql.append(" AND p.name ILIKE :name");
            params.addValue("name", "%" + filters.getName() + "%");
        }

        if (filters.getMinPrice() != null) {
            filterSql.append(" AND p.current_price >= :minPrice");
            params.addValue("minPrice", filters.getMinPrice());
        }

        if (filters.getMaxPrice() != null) {
            filterSql.append(" AND p.current_price <= :maxPrice");
            params.addValue("maxPrice", filters.getMaxPrice());
        }

        if (filters.getState() != null && !filters.getState().isBlank()) {
            filterSql.append(" AND pa.state = :state");
            params.addValue("state", filters.getState());
        }

        if (filters.getCity() != null && !filters.getCity().isBlank()) {
            filterSql.append(" AND pa.city ILIKE :city");
            params.addValue("city", "%" + filters.getCity() + "%");
        }

        String countSql = "SELECT COUNT(*) FROM property p LEFT JOIN property_address pa ON p.address_id = pa.id"
                + filterSql;
        long totalElements = jdbcTemplate.queryForObject(countSql, params, Long.class);

        String orderBySql = "";
        if (filters.getSortByPrice() != null && !filters.getSortByPrice().isBlank()) {
            if (filters.getSortByPrice().equalsIgnoreCase("asc")) {
                orderBySql = " ORDER BY p.current_price ASC";
            } else if (filters.getSortByPrice().equalsIgnoreCase("desc")) {
                orderBySql = " ORDER BY p.current_price DESC";
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID currentAccountId = null;
        if (authentication != null && authentication.getPrincipal() instanceof Account) {
            currentAccountId = ((Account) authentication.getPrincipal()).getId();
        }

        String dataSql = "SELECT p.id, p.name, p.description, p.valued_price as valuedPrice, p.current_price as currentPrice, "
                +
                "p.auction_date_time as auctionDateTime, p.auctioneer_name as auctioneerName, p.auction_link as auctionLink, "
                +
                "p.image_link as imageLink, p.type, p.status, " +
                "pa.zip_code as zipCode, pa.city, pa.state, pa.street, pa.number, pa.neighborhood ";

        if (currentAccountId != null) {
            dataSql += ", CASE WHEN fp.id IS NOT NULL THEN true ELSE false END as isFavorite ";
            params.addValue("currentAccountId", currentAccountId);
        } else {
            dataSql += ", false as isFavorite ";
        }

        dataSql += "FROM property p " +
                "LEFT JOIN property_address pa ON p.address_id = pa.id ";

        if (currentAccountId != null) {
            dataSql += "LEFT JOIN favorite_property fp ON fp.property_id = p.id AND fp.account_id = :currentAccountId ";
        }

        dataSql += filterSql + orderBySql + " LIMIT :limit OFFSET :offset";

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
            property.setAuctionDateTime(
                    rs.getTimestamp("auctionDateTime") != null ? rs.getTimestamp("auctionDateTime").toLocalDateTime()
                            : null);
            property.setAuctioneerName(rs.getString("auctioneerName"));
            property.setAuctionLink(rs.getString("auctionLink"));
            property.setImageLink(rs.getString("imageLink"));

            String typeStr = rs.getString("type");
            if (typeStr != null)
                property.setType(EPropertyType.valueOf(typeStr));

            String statusStr = rs.getString("status");
            if (statusStr != null)
                property.setStatus(EPropertyStatus.valueOf(statusStr));

            PropertyList.PropertyAddressList address = new PropertyList.PropertyAddressList();
            address.setZipCode(rs.getString("zipCode"));
            address.setCity(rs.getString("city"));
            address.setState(rs.getString("state"));
            address.setStreet(rs.getString("street"));
            address.setNumber(rs.getString("number"));
            address.setNeighborhood(rs.getString("neighborhood"));
            property.setAddress(address);

            property.setIsFavorite(rs.getBoolean("isFavorite"));

            return property;
        });

        int totalPages = (int) Math.ceil((double) totalElements / limit);

        return new PaginatedResponse<>(content, filters.getPage(), limit, totalElements, totalPages);
    }
}
