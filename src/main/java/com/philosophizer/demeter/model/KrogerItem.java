package com.philosophizer.demeter.model;

import com.philosophizer.demeter.model.Kroger.KrogerItemDto;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbIgnore;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.Duration;
import java.time.Instant;

@DynamoDbBean
public class KrogerItem {

    private String searchKey; //cache token from SearchTermNormalizer.tokenize()
    private MatchStatus status;
    private String productId, upc, description, brand;
    private String matchedQuery; //standardized raw term from SearchTermNormalizer.standardize();
    private double matchScore;
    private long ttl;
    private static final long MATCH_TTL_SECONDS = Duration.ofDays(30).toSeconds();
    private static final long NO_MATCH_TTL_SECONDS = Duration.ofDays(30).toSeconds();
    private long cachedAt;

    public KrogerItem() {}



    @DynamoDbPartitionKey
    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMatchedQuery() {
        return matchedQuery;
    }

    public void setMatchedQuery(String matchedQuery) {
        this.matchedQuery = matchedQuery;
    }

    public double getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(double matchScore) {
        this.matchScore = matchScore;
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

    public static KrogerItem from(KrogerItemDto dto, String searchKey, String matchedQuery, double matchScore) {
        KrogerItem krogerItem = new KrogerItem();
        krogerItem.setSearchKey(searchKey);
        krogerItem.setMatchedQuery(matchedQuery);
        krogerItem.setMatchScore(matchScore);
        krogerItem.setProductId(dto.productId());
        krogerItem.setBrand(dto.brand());
        krogerItem.setUpc(dto.upc());
        krogerItem.setDescription(dto.description());
        krogerItem.setTtl(Instant.now().plusSeconds(MATCH_TTL_SECONDS).getEpochSecond());
        krogerItem.setCachedAt(Instant.now().getEpochSecond());
        return krogerItem;
    }

    public static KrogerItem noMatch(String searchKey) {
        KrogerItem item = new KrogerItem();
        item.setSearchKey(searchKey);
        item.setStatus(MatchStatus.NO_MATCH);
        item.setTtl(Instant.now().plusSeconds(NO_MATCH_TTL_SECONDS).getEpochSecond());
        item.setCachedAt(Instant.now().getEpochSecond());
        return item;
    }

    @DynamoDbIgnore
    public boolean isExpired() {
        return ttl < Instant.now().getEpochSecond();
    }

    @DynamoDbIgnore
    public boolean isMatch() {
        return status == MatchStatus.MATCH;
    }

    @Override
    public String toString() {
        return "KrogerItem{" +
                "searchKey='" + searchKey + '\'' +
                ", status=" + status +
                ", productId='" + productId + '\'' +
                ", upc='" + upc + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", matchedQuery='" + matchedQuery + '\'' +
                ", matchScore=" + matchScore +
                ", ttl=" + ttl +
                ", cachedAt=" + cachedAt +
                '}';
    }
}
