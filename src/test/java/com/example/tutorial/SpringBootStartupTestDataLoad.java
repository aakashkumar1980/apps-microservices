package com.example.tutorial;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.common.dto.merchant.Merchant;
import com.example.tutorial.common.dto.offer.Offer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.example.tutorial.microservices.campaign.read.repository.CampaignQueryRepository;
import com.example.tutorial.microservices.offer.read.repository.OfferQueryRepository;

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

  private static final Logger log = LoggerFactory.getLogger(SpringBootStartupTestDataLoad.class);

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

  @Autowired
  private CampaignQueryRepository campaignRepository;

  @Autowired
  private OfferQueryRepository offerRepository;

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
    offerRepository.deleteAll();
    campaignRepository.deleteAll();

    // 3. Reset counters
    setCounterTo(campaignCounterKey, 0);
    setCounterTo(offerCounterKey, 0);

    // 4. Insert fresh docs
    campaignRepository.saveAll(campaigns);
    offerRepository.saveAll(offers);

    // 5. Increment counters to the number of documents inserted
    setCounterTo(campaignCounterKey, campaigns.size());
    setCounterTo(offerCounterKey, offers.size());

    log.info("Test data loaded successfully: {} campaigns, {} offers",
        campaigns.size(), offers.size());
  }

  /** PRIVATE METHODS **/
  private <T> List<BaseDto<T>> readJsonArray(String filename, TypeReference<List<BaseDto<T>>> typeRef) throws Exception {
    InputStream is = new ClassPathResource(filename).getInputStream();
    return objectMapper.readValue(is, typeRef);
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
