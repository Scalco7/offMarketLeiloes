package com.backend.offMarketLeiloes.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.ListPropertiesQuery;
import com.backend.offMarketLeiloes.application.features.properties.queries.listProperties.viewModels.PropertyList;

@RestController
@RequestMapping("/properties")
public class PropertyController {
    @Autowired
    private ListPropertiesQuery listPropertiesQuery;

    @GetMapping
    public List<PropertyList> listProperties() {
        return listPropertiesQuery.execute();
    }
}
