package com.backend.offMarketLeiloes.web.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.backend.offMarketLeiloes.application.common.dto.PaginatedResponse;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.ListPropertiesQuery;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.dto.ListPropertiesFilters;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels.PropertyList;
import com.backend.offMarketLeiloes.infrastructure.security.SecurityConfiguration;

@WebMvcTest(PropertyController.class)
@Import(SecurityConfiguration.class)
@AutoConfigureMockMvc(addFilters = false) // Disabling security for this specific controller test
class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ListPropertiesQuery listPropertiesQuery;

    @Test
    void shouldReturnPropertyList() throws Exception {
        PropertyList property = new PropertyList(UUID.randomUUID(), "Edifício Horizonte", "Excelente localização",
                2000000.0, 1500000.0);
        when(listPropertiesQuery.execute(any(ListPropertiesFilters.class)))
                .thenReturn(new PaginatedResponse<>(List.of(property), 0, 10, 1, 1));

        mockMvc.perform(get("/properties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Edifício Horizonte"))
                .andExpect(jsonPath("$.content[0].currentPrice").value(1500000.0))
                .andExpect(jsonPath("$.totalElements").value(1));
    }
}
