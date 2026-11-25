package com.philosophizer.smoothie.model.OpenFoodFacts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//you need this because when a search result is not present for OFF you wont be able to set this based on the response
//So it needs a methodology for setting the response independent of the actual JSON body you return from the API
@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenFoodFactsSearchResult(String id) {
    @Override
    public String id() {
        return id;
    }

}
