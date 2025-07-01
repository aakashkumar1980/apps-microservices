package com.example.tutorial.microservices_campaign_write.utils;

import org.springframework.data.couchbase.core.CouchbaseTemplate;

public class DBUtils {
  /**
   * Get a unique counter value from Couchbase.
   * @param couchbaseTemplate the CouchbaseTemplate
   * @param counterKey the key for the counter document
   * @return the incremented counter value
   */
  public static long getUniqueCounter(CouchbaseTemplate couchbaseTemplate, String counterKey) {
    return couchbaseTemplate.getCouchbaseClientFactory()
        .getCluster()
        .bucket(couchbaseTemplate.getBucketName())
        .defaultCollection()
        .binary()
        .increment(
            counterKey,
            com.couchbase.client.java.kv.IncrementOptions.incrementOptions().delta(1).initial(1)
        )
        .content();
  }
}