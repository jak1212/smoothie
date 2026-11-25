package com.philosophizer.smoothie.model.OpenFoodFacts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

//wrapper class for fields I am interested in from the OpenFoodFacts get API
@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenFoodFactsProduct(@JsonProperty("product_name") String productName,
                                   String allergens,
                                   Double completeness,
                                   @JsonProperty("additives_tags") List<String> additivesTags,
                                   @JsonProperty("ecoscore_grade") String ecoscoreGrade,
                                   @JsonProperty("food_groups") String foodGroups,
                                   @JsonProperty("image_front_small_url") String imageFrontSmallUrl,
                                   @JsonProperty("ingredients_text") String ingredientsText,
                                   @JsonProperty("nova_group") Integer novaGroup,
                                   @JsonProperty("nova_groups_tags") List<String> novaGroupsTags,
                                   @JsonProperty("nutriscore_grade") String nutriscoreGrade
                                   ) {
    @Override
    public String productName() {
        return productName;
    }

    @Override
    public String allergens() {
        return allergens;
    }

    @Override
    public Double completeness() {
        return completeness;
    }

    @Override
    public List<String> additivesTags() {
        return additivesTags;
    }

    @Override
    public String ecoscoreGrade() {
        return ecoscoreGrade;
    }

    @Override
    public String foodGroups() {
        return foodGroups;
    }

    @Override
    public String imageFrontSmallUrl() {
        return imageFrontSmallUrl;
    }

    @Override
    public String ingredientsText() {
        return ingredientsText;
    }

    @Override
    public Integer novaGroup() {
        return novaGroup;
    }

    @Override
    public List<String> novaGroupsTags() {
        return novaGroupsTags;
    }

    @Override
    public String nutriscoreGrade() {
        return nutriscoreGrade;
    }





    @Override
    public String toString() {
        return "OpenFoodFactsProduct{" +
                "productName='" + productName + '\'' +
                ", allergens='" + allergens + '\'' +
                ", completeness=" + completeness +
                ", additivesTags=" + additivesTags +
                ", ecoscoreGrade='" + ecoscoreGrade + '\'' +
                ", foodGroups='" + foodGroups + '\'' +
                ", imageFrontSmallUrl='" + imageFrontSmallUrl + '\'' +
                ", ingredientsText='" + ingredientsText + '\'' +
                ", novaGroup=" + novaGroup +
                ", novaGroupsTags=" + novaGroupsTags +
                ", nutriscoreGrade='" + nutriscoreGrade + '\'' +
                '}';
    }
}
