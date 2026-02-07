package com.backend.offMarketLeiloes.application.features.favorites.queries.listFavorites;

import com.backend.offMarketLeiloes.application.common.dto.PaginatedResponse;
import com.backend.offMarketLeiloes.application.features.favorites.queries.listFavorites.dto.ListFavoritePropertiesFilters;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels.PropertyList;
import com.backend.offMarketLeiloes.domain.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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

        String dataSql = "SELECT p.id, p.name, p.description, p.valued_price as valuedPrice, p.current_price as currentPrice "
                +
                "FROM favorite_property fp " +
                "INNER JOIN property p ON fp.property_id = p.id" + filterSql +
                " ORDER BY fp.created_at DESC" +
                " LIMIT :limit OFFSET :offset";

        int limit = filters.getPageSize();
        int offset = filters.getPage() * filters.getPageSize();
        params.addValue("limit", limit);
        params.addValue("offset", offset);

        List<PropertyList> content = jdbcTemplate.query(dataSql, params,
                new BeanPropertyRowMapper<>(PropertyList.class));

        int totalPages = (int) Math.ceil((double) totalElements / limit);

        return new PaginatedResponse<>(content, filters.getPage(), limit, totalElements, totalPages);
    }
}
