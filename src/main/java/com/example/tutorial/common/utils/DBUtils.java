package com.example.tutorial.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Component;

@Component
public class DBUtils {
  private static final Logger log = LoggerFactory.getLogger(DBUtils.class);

  /**
   * Get a unique counter value from Couchbase.
   * TODO: Implement @Retry as this is a service call
   *
   * @param couchbaseTemplate the CouchbaseTemplate
   * @param counterKey the key for the counter document
   * @return the incremented counter value
   */
  public long getUniqueCounter(CouchbaseTemplate couchbaseTemplate, String counterKey) {
    log.debug("Incrementing counter for key: {}", counterKey);
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