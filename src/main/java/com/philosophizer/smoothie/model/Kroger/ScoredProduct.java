package com.philosophizer.smoothie.model.Kroger;

public record ScoredProduct(KrogerItemDto product, double coverage, int extraTokens) {

    @Override
    public KrogerItemDto product() {
        return product;
    }

    @Override
    public double coverage() {
        return coverage;
    }

    @Override
    public int extraTokens() {
        return extraTokens;
    }

    @Override
    public String toString() {
        return "ScoredProduct{" +
                "product=" + product +
                ", coverage=" + coverage +
                ", extraTokens=" + extraTokens +
                '}';
    }


}
