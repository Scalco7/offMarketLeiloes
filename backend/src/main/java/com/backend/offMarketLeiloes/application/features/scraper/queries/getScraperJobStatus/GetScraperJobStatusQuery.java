package com.backend.offMarketLeiloes.application.features.scraper.queries.getScraperJobStatus;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.backend.offMarketLeiloes.application.features.scraper.queries.getScraperJobStatus.viewModels.GetScraperJobStatusResponse;
import com.backend.offMarketLeiloes.domain.enums.EScraperJobStatus;
import com.backend.offMarketLeiloes.domain.enums.EScraperSites;

@Service
public class GetScraperJobStatusQuery {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public GetScraperJobStatusResponse execute(UUID id) {
        String sql = "SELECT id, scraper, status, start_time, end_time, error_message, created_at, updated_at " +
                "FROM scraper_job WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
            GetScraperJobStatusResponse response = new GetScraperJobStatusResponse();
            response.setId(UUID.fromString(rs.getString("id")));
            response.setScraper(EScraperSites.valueOf(rs.getString("scraper")));
            response.setStatus(EScraperJobStatus.valueOf(rs.getString("status")));
            response.setStartTime(
                    rs.getTimestamp("start_time") != null ? rs.getTimestamp("start_time").toLocalDateTime() : null);
            response.setEndTime(
                    rs.getTimestamp("end_time") != null ? rs.getTimestamp("end_time").toLocalDateTime() : null);
            response.setErrorMessage(rs.getString("error_message"));
            return response;
        });
    }
}
