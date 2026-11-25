package com.philosophizer.smoothie.OpenFoodFacts;


import com.philosophizer.smoothie.connectivity.OpenFoodFacts.OpenFoodFactsClient;
import com.philosophizer.smoothie.model.OpenFoodFacts.OpenFoodFactsProduct;
import com.philosophizer.smoothie.model.OpenFoodFacts.OpenFoodFactsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = OpenFoodFactsClient.class)
public class OpenFoodFactsClientTest {
    @Autowired
    public OpenFoodFactsClient openFoodFactsClient;

    @Test
    public void getItemTest() {
        String upc = "049000042566";
        OpenFoodFactsResponse searchResult = openFoodFactsClient.getItemByUpc(upc)
                .orElseThrow(() -> new AssertionError("expected OFF to know this UPC"));
        OpenFoodFactsProduct product = searchResult.product();
        assertNotNull(searchResult);
        assertEquals("Coca-Cola Zero Sugar", product.productName());
        assertEquals(4, product.novaGroup());
        assertTrue(product.additivesTags().contains("en:e950"));
        assertEquals("", product.allergens());
        assertEquals(0.775, product.completeness());
        assertEquals("https://images.openfoodfacts.net/images/products/004/900/004/2566/front_en.108.200.jpg", product.imageFrontSmallUrl());
        assertTrue(product.ingredientsText().contains("potassium benzoate"));

    }

    @Test
    public void incompleteProductFilter() {
        String invalidUpc = "5432";
        Optional<OpenFoodFactsResponse> response = openFoodFactsClient.getItemByUpc(invalidUpc);
        System.out.println(response.toString());
        assertTrue(response.isEmpty());

    }
}
