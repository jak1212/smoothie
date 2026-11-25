package com.philosophizer.smoothie.model;

import com.philosophizer.smoothie.model.OpenFoodFacts.OpenFoodFactsProduct;
import com.philosophizer.smoothie.utils.UpcUtils;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbIgnore;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@DynamoDbBean
public class OpenFoodFactsItem {

    private String upc;
    private MatchStatus status;
    private String productName, allergens, ecoscoreGrade, foodGroups, imageFrontSmallUrl, ingredientsText, nutriscoreGrade;
    private Double completeness;
    private List<String> additiveTags, novaGroupsTags;
    private Integer novaGroup;
    private long ttl;
    private static final long TTL_MATCH_SECONDS = Duration.ofDays(30).toSeconds();
    private static final long NO_MATCH_TTL_SECONDS = Duration.ofDays(30).toSeconds();
    private long cachedAt;

    public OpenFoodFactsItem() {}

    @DynamoDbPartitionKey
    public String getUpc() {return upc;}

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    public String getEcoscoreGrade() {
        return ecoscoreGrade;
    }

    public void setEcoscoreGrade(String ecoscoreGrade) {
        this.ecoscoreGrade = ecoscoreGrade;
    }

    public String getFoodGroups() {
        return foodGroups;
    }

    public void setFoodGroups(String foodGroups) {
        this.foodGroups = foodGroups;
    }

    public String getImageFrontSmallUrl() {
        return imageFrontSmallUrl;
    }

    public void setImageFrontSmallUrl(String imageFrontSmallUrl) {
        this.imageFrontSmallUrl = imageFrontSmallUrl;
    }

    public String getIngredientsText() {
        return ingredientsText;
    }

    public void setIngredientsText(String ingredientsText) {
        this.ingredientsText = ingredientsText;
    }

    public String getNutriscoreGrade() {
        return nutriscoreGrade;
    }

    public void setNutriscoreGrade(String nutriscoreGrade) {
        this.nutriscoreGrade = nutriscoreGrade;
    }

    public Double getCompleteness() {
        return completeness;
    }

    public void setCompleteness(Double completeness) {
        this.completeness = completeness;
    }

    public List<String> getAdditiveTags() {
        return additiveTags;
    }

    public void setAdditiveTags(List<String> additiveTags) {
        this.additiveTags = additiveTags;
    }

    public List<String> getNovaGroupsTags() {
        return novaGroupsTags;
    }

    public void setNovaGroupsTags(List<String> novaGroupsTags) {
        this.novaGroupsTags = novaGroupsTags;
    }

    public Integer getNovaGroup() {
        return novaGroup;
    }

    public void setNovaGroup(Integer novaGroup) {
        this.novaGroup = novaGroup;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public long getCachedAt() {
        return cachedAt;
    }

    public void setCachedAt(long cachedAt) {
        this.cachedAt = cachedAt;
    }



    public static OpenFoodFactsItem from(String upc, OpenFoodFactsProduct product) {
        OpenFoodFactsItem item = new OpenFoodFactsItem();
        item.setUpc(UpcUtils.normalize(upc));
        item.setAllergens(product.allergens());
        item.setAdditiveTags(product.additivesTags());
        item.setCompleteness(product.completeness());
        item.setFoodGroups(product.foodGroups());
        item.setEcoscoreGrade(product.ecoscoreGrade());
        item.setImageFrontSmallUrl(product.imageFrontSmallUrl());
        item.setIngredientsText(product.ingredientsText());
        item.setStatus(MatchStatus.MATCH);
        item.setTtl(Instant.now().plusSeconds(TTL_MATCH_SECONDS).getEpochSecond());
        item.setCachedAt(Instant.now().getEpochSecond());
        item.setProductName(product.productName());
        item.setNutriscoreGrade(product.nutriscoreGrade());
        item.setNovaGroup(product.novaGroup());
        item.setNovaGroupsTags(product.novaGroupsTags());
        return item;
    }

    public static OpenFoodFactsItem noMatch(String upc) {
        OpenFoodFactsItem item = new OpenFoodFactsItem();
        item.setUpc(UpcUtils.normalize(upc));
        item.setTtl(Instant.now().plusSeconds(NO_MATCH_TTL_SECONDS).getEpochSecond());
        item.setCachedAt(Instant.now().getEpochSecond());
        item.setStatus(MatchStatus.NO_MATCH);
        return item;
    }

    @DynamoDbIgnore
    public boolean isExpired(){
        return ttl < Instant.now().getEpochSecond();
    }

    @Override
    public String toString() {
        return "OpenFoodFactsItem{" +
                "upc='" + upc + '\'' +
                ", matchStatus=" + status +
                ", productName='" + productName + '\'' +
                ", allergens='" + allergens + '\'' +
                ", ecoscoreGrade='" + ecoscoreGrade + '\'' +
                ", foodGroups='" + foodGroups + '\'' +
                ", imageFrontSmallUrl='" + imageFrontSmallUrl + '\'' +
                ", ingredientsText='" + ingredientsText + '\'' +
                ", nutriscoreGrade='" + nutriscoreGrade + '\'' +
                ", completeness=" + completeness +
                ", additiveTags=" + additiveTags +
                ", novaGroupsTags=" + novaGroupsTags +
                ", novaGroup=" + novaGroup +
                ", ttl=" + ttl +
                ", cachedAt=" + cachedAt +
                '}';
    }
}
