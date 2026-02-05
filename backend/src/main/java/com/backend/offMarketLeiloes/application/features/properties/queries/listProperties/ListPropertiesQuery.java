package com.backend.offMarketLeiloes.application.features.properties.queries.listProperties;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.dto.ListPropertiesFilters;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels.PropertyList;

@Service
public class ListPropertiesQuery {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<PropertyList> execute(ListPropertiesFilters filters) {
        StringBuilder sql = new StringBuilder(
                "SELECT p.id, p.name, p.description, p.valued_price as valuedPrice, p.current_price as currentPrice " +
                        "FROM property p " +
                        "LEFT JOIN property_address pa ON p.address_id = pa.id WHERE 1=1");

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (filters.getName() != null && !filters.getName().isBlank()) {
            sql.append(" AND p.name ILIKE :name");
            params.addValue("name", "%" + filters.getName() + "%");
        }

        if (filters.getMinPrice() != null) {
            sql.append(" AND p.current_price >= :minPrice");
            params.addValue("minPrice", filters.getMinPrice());
        }

        if (filters.getMaxPrice() != null) {
            sql.append(" AND p.current_price <= :maxPrice");
            params.addValue("maxPrice", filters.getMaxPrice());
        }

        if (filters.getState() != null && !filters.getState().isBlank()) {
            sql.append(" AND pa.state = :state");
            params.addValue("state", filters.getState());
        }

        if (filters.getCity() != null && !filters.getCity().isBlank()) {
            sql.append(" AND pa.city ILIKE :city");
            params.addValue("city", "%" + filters.getCity() + "%");
        }

        return jdbcTemplate.query(sql.toString(), params, new BeanPropertyRowMapper<>(PropertyList.class));
    }
}
