package com.backend.offMarketLeiloes.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.backend.offMarketLeiloes.application.features.scraper.command.startScraper.viewModels.StartScraperRequest;
import com.backend.offMarketLeiloes.domain.enums.EScraperJobStatus;
import com.backend.offMarketLeiloes.domain.enums.EScraperSites;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
class ScraperControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @org.springframework.test.context.bean.override.mockito.MockitoBean
        private com.backend.offMarketLeiloes.application.features.scraper.command.startScraper.StartScraperCommand startScraperCommand;

        @org.springframework.test.context.bean.override.mockito.MockitoBean
        private com.backend.offMarketLeiloes.application.features.scraper.queries.getScraperJobStatus.GetScraperJobStatusQuery getScraperJobStatusQuery;

        private final ObjectMapper objectMapper = new ObjectMapper();

        @BeforeEach
        void setUp() {
                jdbcTemplate.execute("DELETE FROM scraper_job");
        }

        @Test
        void shouldStartScraper() throws Exception {
                UUID jobId = UUID.randomUUID();
                StartScraperRequest request = new StartScraperRequest();
                request.setScraper(EScraperSites.KLOCKNER_LEILOES.name());

                com.backend.offMarketLeiloes.application.features.scraper.command.startScraper.viewModels.StartScraperResponse response = new com.backend.offMarketLeiloes.application.features.scraper.command.startScraper.viewModels.StartScraperResponse(
                                jobId, EScraperSites.KLOCKNER_LEILOES.name(), EScraperJobStatus.RUNNING.name());

                org.mockito.Mockito.when(startScraperCommand.execute(org.mockito.ArgumentMatchers.any()))
                                .thenReturn(response);

                mockMvc.perform(post("/scraper/start")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.jobId").value(jobId.toString()))
                                .andExpect(jsonPath("$.scraper").value(EScraperSites.KLOCKNER_LEILOES.name()))
                                .andExpect(jsonPath("$.status").value(EScraperJobStatus.RUNNING.name()));
        }

        @Test
        void shouldGetScraperStatus() throws Exception {
                UUID jobId = UUID.randomUUID();
                com.backend.offMarketLeiloes.application.features.scraper.queries.getScraperJobStatus.viewModels.GetScraperJobStatusResponse response = com.backend.offMarketLeiloes.application.features.scraper.queries.getScraperJobStatus.viewModels.GetScraperJobStatusResponse
                                .builder()
                                .id(jobId)
                                .scraper(EScraperSites.KLOCKNER_LEILOES)
                                .status(EScraperJobStatus.RUNNING)
                                .build();

                org.mockito.Mockito.when(getScraperJobStatusQuery.execute(jobId)).thenReturn(response);

                mockMvc.perform(get("/scraper/" + jobId + "/status"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(jobId.toString()))
                                .andExpect(jsonPath("$.status").value(EScraperJobStatus.RUNNING.name()));
        }

        @Test
        void shouldReturnErrorWhenJobNotFound() throws Exception {
                UUID nonExistentId = UUID.randomUUID();
                org.mockito.Mockito.when(getScraperJobStatusQuery.execute(nonExistentId))
                                .thenThrow(new RuntimeException("Job not found"));

                mockMvc.perform(get("/scraper/" + nonExistentId + "/status"))
                                .andExpect(status().isInternalServerError())
                                .andExpect(jsonPath("$.message")
                                                .value("Ocorreu um erro inesperado. Tente novamente mais tarde."));
        }
}
