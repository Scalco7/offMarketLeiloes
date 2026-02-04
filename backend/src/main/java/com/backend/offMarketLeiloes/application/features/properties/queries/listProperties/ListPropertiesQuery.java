package com.backend.offMarketLeiloes.application.features.properties.queries.listProperties;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels.PropertyList;

@Service
public class ListPropertiesQuery {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<PropertyList> execute() {
        String sql = "SELECT id, name, description, valued_price as valuedPrice, current_price as currentPrice FROM property";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PropertyList.class));
    }
}
