package com.philosophizer.smoothie.KrogerClient;

import com.philosophizer.smoothie.connectivity.kroger.KrogerClient;
import com.philosophizer.smoothie.model.Kroger.KrogerItemDto;
import com.philosophizer.smoothie.model.KrogerItem;
import com.philosophizer.smoothie.model.MatchStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = KrogerClient.class)
public class KrogerClientTest {

    @Autowired
    private KrogerClient krogerClient;


    @Test
    public void getResultFromKroger() throws Exception{

        String searchTerm = "chicken breast";
        List<KrogerItemDto> results = krogerClient.getProductsForRawTerm(searchTerm);
        System.out.println(results.size());
        assertTrue(!results.isEmpty());

    }
    @Test
    void beanSchemaIsValidAndFactoriesSetTtl() {
        TableSchema<KrogerItem> schema = TableSchema.fromBean(KrogerItem.class); // throws on bad annotations
        KrogerItem miss = KrogerItem.noMatch("v1#dragonfruit");

        assertEquals(MatchStatus.NO_MATCH, miss.getStatus());
        assertFalse(miss.isExpired());
        assertFalse(schema.itemToMap(miss, true).containsKey("expired"));   // helper not persisted
    }
}
