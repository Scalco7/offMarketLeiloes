package com.backend.offMarketLeiloes.application.features.properties.queries.listProperties;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.backend.offMarketLeiloes.application.common.dto.PaginatedResponse;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels.ListPropertiesFilters;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels.PropertyList;

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

        String dataSql = "SELECT p.id, p.name, p.description, p.valued_price as valuedPrice, p.current_price as currentPrice "
                +
                "FROM property p " +
                "LEFT JOIN property_address pa ON p.address_id = pa.id" + filterSql +
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
