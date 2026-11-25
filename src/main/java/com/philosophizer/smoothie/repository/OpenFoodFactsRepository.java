package com.philosophizer.smoothie.repository;

import com.philosophizer.smoothie.connectivity.OpenFoodFacts.OpenFoodFactsClient;
import com.philosophizer.smoothie.model.OpenFoodFacts.OpenFoodFactsProduct;
import com.philosophizer.smoothie.model.OpenFoodFactsItem;
import com.philosophizer.smoothie.utils.UpcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.Optional;

@Repository
public class OpenFoodFactsRepository {

    private final DynamoDbTable<OpenFoodFactsItem> offTable;

    public OpenFoodFactsRepository(DynamoDbEnhancedClient client, @Value("${smoothie.dynamodb.open-food-facts-item-table}") String tableName)
    {
        this.offTable = client.table(tableName, TableSchema.fromBean(OpenFoodFactsItem.class));
    }

    public Optional<OpenFoodFactsItem> findByUpc(String upc){
        OpenFoodFactsItem item = offTable.getItem(Key.builder().partitionValue(UpcUtils.normalize(upc)).build());
        return Optional.ofNullable(item).filter(i -> !i.isExpired());
    }

    public void save(OpenFoodFactsItem item) {
        offTable.putItem(item);
    }


}
