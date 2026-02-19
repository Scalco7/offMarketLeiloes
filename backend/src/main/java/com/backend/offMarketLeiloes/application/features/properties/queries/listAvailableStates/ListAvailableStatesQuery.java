package com.backend.offMarketLeiloes.application.features.properties.queries.listAvailableStates;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.backend.offMarketLeiloes.application.features.properties.queries.listAvailableStates.viewModels.AvailableState;
import com.backend.offMarketLeiloes.domain.enums.EPropertyStatus;

@Service
public class ListAvailableStatesQuery {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<AvailableState> execute() {
        String sql = "SELECT pa.state, COUNT(p.id) as propertyCount " +
                "FROM property p " +
                "JOIN property_address pa ON p.address_id = pa.id " +
                "WHERE p.status = :status " +
                "AND pa.state IS NOT NULL " +
                "GROUP BY pa.state " +
                "ORDER BY pa.state ASC";

        return jdbcTemplate.query(sql, java.util.Map.of("status", EPropertyStatus.ACTIVE.name()), (rs, rowNum) -> {
            AvailableState state = new AvailableState();
            state.setState(rs.getString("state"));
            state.setPropertyCount(rs.getLong("propertyCount"));
            return state;
        });
    }
}
