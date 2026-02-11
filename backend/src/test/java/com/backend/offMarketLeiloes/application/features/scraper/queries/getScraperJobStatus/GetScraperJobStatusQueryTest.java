package com.backend.offMarketLeiloes.application.features.scraper.queries.getScraperJobStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.backend.offMarketLeiloes.application.features.scraper.queries.getScraperJobStatus.viewModels.GetScraperJobStatusResponse;
import com.backend.offMarketLeiloes.domain.enums.EScraperJobStatus;
import com.backend.offMarketLeiloes.domain.enums.EScraperSites;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class GetScraperJobStatusQueryTest {

    @Autowired
    private GetScraperJobStatusQuery getScraperJobStatusQuery;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM scraper_job");
    }

    @Test
    void shouldReturnJobStatus() {
        UUID jobId = UUID.randomUUID();
        jdbcTemplate.update(
                "INSERT INTO scraper_job (id, scraper, status, start_time, created_at, updated_at) VALUES (?, ?, ?, now(), now(), now())",
                jobId, EScraperSites.KLOCKNER_LEILOES.name(), EScraperJobStatus.RUNNING.name());

        GetScraperJobStatusResponse result = getScraperJobStatusQuery.execute(jobId);

        assertNotNull(result);
        assertEquals(jobId, result.getId());
        assertEquals(EScraperSites.KLOCKNER_LEILOES, result.getScraper());
        assertEquals(EScraperJobStatus.RUNNING, result.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenJobNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        assertThrows(EmptyResultDataAccessException.class, () -> {
            getScraperJobStatusQuery.execute(nonExistentId);
        });
    }
}
