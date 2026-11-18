package com.philosophizer.demeter.KrogerItemPersistence;

import com.philosophizer.demeter.configuration.aws.DynamoDbConfig;
import com.philosophizer.demeter.kroger.KrogerClient;
import com.philosophizer.demeter.model.Kroger.KrogerItemDto;
import com.philosophizer.demeter.model.Kroger.ScoredProduct;
import com.philosophizer.demeter.model.KrogerItem;
import com.philosophizer.demeter.model.MatchStatus;
import com.philosophizer.demeter.repository.KrogerItemRepository;
import com.philosophizer.demeter.utils.SearchTermNormalizer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {DynamoDbConfig.class, KrogerItemRepository.class, KrogerClient.class, SearchTermNormalizer.class})
//@EnabledIfEnvironmentVariable(named = "DEMETER_IT", matches = "true")
public class KrogerItemTest {

    @Autowired
    public KrogerItemRepository krogerItemRepository;

    @Autowired
    public KrogerClient krogerClient;

    @Autowired
    public SearchTermNormalizer normalizer;

    @Test
    public void savesAndReadsBothStatuses() {
        krogerItemRepository.save(KrogerItem.noMatch("v1#test#nomatch"));
        assertEquals(MatchStatus.NO_MATCH, krogerItemRepository.findByKey("v1#test#nomatch").orElseThrow().getStatus());
    }

    @Test
    public void expiredItemsAreTreatedAsMisses() {
        KrogerItem stale = KrogerItem.noMatch("v1#test#stale");
        stale.setTtl(Instant.now().minusSeconds(60).getEpochSecond());
        krogerItemRepository.save(stale);
        assertTrue(krogerItemRepository.findByKey("v1#test#stale").isEmpty());
    }

    @Test
    public void retrieveNewItemAndSave() {
        String rawTerm = "1 gallon 2% milk";
        Optional<ScoredProduct> bestResult = krogerClient.pickBest(rawTerm);
        KrogerItemDto bestResultDto = bestResult.get().product();
        krogerItemRepository.save(KrogerItem.from(bestResultDto, normalizer.tokenize(rawTerm), normalizer.standardize(rawTerm), bestResult.get().coverage()));
        assertNotNull(krogerItemRepository.findByKey(normalizer.tokenize(rawTerm)));

    }
}
