package com.philosophizer.smoothie.repository;

import com.philosophizer.smoothie.model.KrogerItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.Optional;

@Repository
public class KrogerItemRepository {

    private final DynamoDbTable<KrogerItem> table;

    public KrogerItemRepository(DynamoDbEnhancedClient client, @Value("${smoothie.dynamodb.kroger-item-table}") String tableName) {
        this.table = client.table(tableName, TableSchema.fromBean(KrogerItem.class));
    }

    public Optional<KrogerItem> findByKey(String searchKey) {
        KrogerItem item = table.getItem(Key.builder().partitionValue(searchKey).build());
        return Optional.ofNullable(item).filter(i -> !i.isExpired());
    }

    public void save(KrogerItem item) {
        table.putItem(item);
    }
}
