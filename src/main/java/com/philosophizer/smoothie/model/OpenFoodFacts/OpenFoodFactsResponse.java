package com.philosophizer.smoothie.model.OpenFoodFacts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenFoodFactsResponse(String code, OpenFoodFactsSearchResult result, OpenFoodFactsProduct product) {
    @Override
    //code is the same value as the upc
    public String code() {
        return code;
    }

    @Override
    public OpenFoodFactsSearchResult result() {
        return result;
    }

    @Override
    public OpenFoodFactsProduct product() {
        return product;
    }

    @Override
    public String toString() {
        return "OpenFoodFactsResponse{" +
                "code='" + code + '\'' +
                ", result=" + result +
                ", product=" + product +
                '}';
    }

    public boolean found() {
        return product != null && result != null && "product_found".equals(result.id());
    }

    public boolean isUsable(double minCompleteness) {
        return found()
                && product.completeness() != null
                && product.completeness() >= minCompleteness;
    }
}
