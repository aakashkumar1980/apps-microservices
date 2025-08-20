package com.example.tutorial.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Component;

/**
 * Utility class for database operations related to Couchbase.
 * Provides methods to interact with Couchbase for common tasks.
 */
@Component
public class DBUtils {
  private static final Logger log = LoggerFactory.getLogger(DBUtils.class);

  /**
   * Get a unique counter value from Couchbase.
   * <pre>
   * For storing any object in the database, we need a unique identifier which should me the ID of the object.
   * The counter is incremented atomically in Couchbase to ensure uniqueness and the ID values can be
   * of examples "campaign::1", "campaign::2", etc.
   * </pre>
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