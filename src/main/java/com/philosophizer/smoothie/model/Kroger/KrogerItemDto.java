package com.philosophizer.smoothie.model.Kroger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KrogerItemDto(String productId, String upc, String description, String brand) {

    @Override
    public String productId() {
        return productId;
    }

    @Override
    public String upc() {
        return upc;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public String brand() {
        return brand;
    }

    @Override
    public String toString() {
        return "KrogerItemDto{" +
                "productId='" + productId + '\'' +
                ", upc='" + upc + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }


}
