package com.philosophizer.smoothie.OpenFoodFacts;

import com.philosophizer.smoothie.configuration.aws.DynamoDbConfig;
import com.philosophizer.smoothie.connectivity.OpenFoodFacts.OpenFoodFactsClient;
import com.philosophizer.smoothie.model.MatchStatus;
import com.philosophizer.smoothie.model.OpenFoodFacts.OpenFoodFactsProduct;
import com.philosophizer.smoothie.model.OpenFoodFacts.OpenFoodFactsResponse;
import com.philosophizer.smoothie.model.OpenFoodFactsItem;
import com.philosophizer.smoothie.repository.OpenFoodFactsRepository;
import com.philosophizer.smoothie.utils.SearchTermNormalizer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {OpenFoodFactsRepository.class, OpenFoodFactsClient.class, DynamoDbConfig.class, SearchTermNormalizer.class})
public class OpenFoodFactsRepositoryTest {

    @Autowired
    public OpenFoodFactsClient openFoodFactsClient;

    @Autowired
    public OpenFoodFactsRepository openFoodFactsRepository;

    @Autowired
    public SearchTermNormalizer normalizer;

    @Test
    public void saveAndRetrieveFromDynamoDb(){
        String upc = "049000042566";   // deliberately 12 digits
        OpenFoodFactsResponse response = openFoodFactsClient.getItemByUpc(upc).orElseThrow();
        openFoodFactsRepository.save(OpenFoodFactsItem.from(upc, response.product()));
        OpenFoodFactsItem testObject = openFoodFactsRepository.findByUpc("0049000042566").get();
        assertNotNull(testObject);
        assertEquals("0049000042566", testObject.getUpc());
    }

    @Test
    void beanSchemaIsValidAndFactoriesSetTtl() {
        TableSchema<OpenFoodFactsItem> schema = TableSchema.fromBean(OpenFoodFactsItem.class); // throws on bad annotations
        OpenFoodFactsItem miss = OpenFoodFactsItem.noMatch("54321");

        assertEquals(MatchStatus.NO_MATCH, miss.getStatus());
        assertFalse(miss.isExpired());
        assertFalse(schema.itemToMap(miss, true).containsKey("expired"));   // helper not persisted
    }


}
