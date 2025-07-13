package com.example.tutorial;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.common.dto.merchant.Merchant;
import com.example.tutorial.common.dto.offer.Offer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.couchbase.core.CouchbaseTemplate;

import java.io.InputStream;
import java.util.List;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.kv.IncrementOptions;

/**
 * This test class is responsible for loading initial test data into Couchbase
 * when the Spring Boot application starts up in the test profile.
 *
 * It reads sample data from JSON files, removes existing documents,
 * resets counters, and inserts fresh documents.
 */
@SpringBootTest
@ActiveProfiles("test")
public class SpringBootStartupTestDataLoad {

    @Autowired
    private CouchbaseTemplate couchbaseTemplate;

    @Value("${campaign.counter.key}")
    private String campaignCounterKey;

    @Value("${offer.counter.key}")
    private String offerCounterKey;

    @Value("${merchant.counter.key}")
    private String merchantCounterKey;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * This method is executed after the Spring Boot application context is loaded.
     *
     * It loads sample data from JSON files, removes existing documents,
     * resets counters, and inserts fresh documents into Couchbase.
     */
    @Test
    public void loadTestData() throws Exception {
        // 1. Load sample data from resources as BaseDto lists
        List<BaseDto<Campaign>> campaigns = readJsonArray("sample-campaigns.json", new TypeReference<List<BaseDto<Campaign>>>() {});
        List<BaseDto<Offer>> offers = readJsonArray("sample-offers.json", new TypeReference<List<BaseDto<Offer>>>() {});
        List<BaseDto<Merchant>> merchants = readJsonArray("sample-merchants.json", new TypeReference<List<BaseDto<Merchant>>>() {});

        // 2. Remove all existing docs for each type (offers, merchants, campaigns)
        offers.forEach(dto -> removeIfExists(dto.getId()));
        merchants.forEach(dto -> removeIfExists(dto.getId()));
        campaigns.forEach(dto -> removeIfExists(dto.getId()));

        // 3. Reset counters
        resetCounter(campaignCounterKey);
        resetCounter(offerCounterKey);
        resetCounter(merchantCounterKey);

        // 4. Insert fresh docs
        campaigns.forEach(this::upsertRaw);
        offers.forEach(this::upsertRaw);
        merchants.forEach(this::upsertRaw);

        // 5. Increment counters to match number of inserted docs
        setCounterTo(campaignCounterKey, campaigns.size());
        setCounterTo(offerCounterKey, offers.size());
        setCounterTo(merchantCounterKey, merchants.size());
    }

    /** PRIVATE METHODS **/
    private <T> List<BaseDto<T>> readJsonArray(String filename, TypeReference<List<BaseDto<T>>> typeRef) throws Exception {
        InputStream is = new ClassPathResource(filename).getInputStream();
        return objectMapper.readValue(is, typeRef);
    }

    private void removeIfExists(String id) {
        try {
            couchbaseTemplate.getCouchbaseClientFactory().getBucket().defaultCollection().remove(id);
        } catch (Exception ignored) {
            // ignore if not exists
        }
    }

    private void resetCounter(String counterKey) {
        Bucket bucket = couchbaseTemplate.getCouchbaseClientFactory().getBucket();
        Collection collection = bucket.defaultCollection();
        try {
            collection.remove(counterKey);
        } catch (com.couchbase.client.core.error.DocumentNotFoundException ignored) {
            // ignore if not exists
        }
        collection.binary().increment(counterKey, IncrementOptions.incrementOptions().initial(0L).delta(1L));
    }

    private void upsertRaw(Object dto) {
        try {
            String id = (String) dto.getClass().getMethod("getId").invoke(dto);
            String json = objectMapper.writeValueAsString(dto);
            couchbaseTemplate.getCouchbaseClientFactory().getBucket().defaultCollection().upsert(id, objectMapper.readTree(json));
        } catch (Exception e) {
            throw new RuntimeException("Failed to upsert doc", e);
        }
    }

    private void setCounterTo(String counterKey, int value) {
        Bucket bucket = couchbaseTemplate.getCouchbaseClientFactory().getBucket();
        Collection collection = bucket.defaultCollection();
        // Remove and set to value
        try {
            collection.remove(counterKey);
        } catch (com.couchbase.client.core.error.DocumentNotFoundException ignored) {
            // ignore if not exists
        }
        if (value > 0) {
            collection.binary().increment(counterKey, IncrementOptions.incrementOptions().initial(value).delta(0L));
        } else {
            collection.binary().increment(counterKey, IncrementOptions.incrementOptions().initial(0L).delta(0L));
        }
    }
}
