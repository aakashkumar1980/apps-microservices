package com.example.tutorial.common.configurations;

import com.couchbase.client.java.env.ClusterEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.convert.CouchbaseCustomConversions;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@PropertySources(
    @PropertySource("classpath:application-db.properties")
)
public class CouchbaseConfiguration extends AbstractCouchbaseConfiguration {

  @Override
  protected void configureEnvironment(ClusterEnvironment.Builder builder) {
    builder.securityConfig().enableTls(true);
  }

  @Value("${spring.couchbase.connection-string}")
  private String connectionString;

  @Value("${spring.couchbase.username}")
  private String username;

  @Value("${spring.couchbase.password}")
  private String password;

  @Value("${spring.couchbase.bucket.name}")
  private String bucketName;

  @Override
  public String getConnectionString() {
    return connectionString;
  }

  @Override
  public String getUserName() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getBucketName() {
    return bucketName;
  }

  @Bean(name = "couchbaseCustomConversionsNew")
  public CouchbaseCustomConversions couchbaseCustomConversionsNew() {
    return new CouchbaseCustomConversions(java.util.Collections.emptyList());
  }
}
