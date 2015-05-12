package com.application.yaroslav.searchprogm.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Yaroslav on 02.05.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodSearchResults {

    private List<Food> results;

    public List<Food> getResults() {
        return results;
    }

    public void setResults(List<Food> results) {
        this.results = results;
    }
}
