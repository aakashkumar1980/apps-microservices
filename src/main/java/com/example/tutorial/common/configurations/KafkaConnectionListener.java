package com.example.tutorial.common.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.util.Properties;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterResult;

@Component
@PropertySource("classpath:application-kafka.properties")
public class KafkaConnectionListener {

  private static final Logger logger = LoggerFactory.getLogger(KafkaConnectionListener.class);

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${spring.kafka.properties.sasl.mechanism:}")
  private String saslMechanism;

  @Value("${spring.kafka.properties.security.protocol:}")
  private String securityProtocol;

  @Value("${spring.kafka.properties.sasl.jaas.config:}")
  private String saslJaasConfig;

  @EventListener(ApplicationReadyEvent.class)
  public void onApplicationReady() {
    Properties props = new Properties();
    props.put("bootstrap.servers", bootstrapServers);
    if (!saslMechanism.isEmpty()) {
      props.put("sasl.mechanism", saslMechanism);
    }
    if (!securityProtocol.isEmpty()) {
      props.put("security.protocol", securityProtocol);
    }
    if (!saslJaasConfig.isEmpty()) {
      props.put("sasl.jaas.config", saslJaasConfig);
    }

    try (AdminClient adminClient = AdminClient.create(props)) {
      DescribeClusterResult cluster = adminClient.describeCluster();
      String clusterId = cluster.clusterId().get();
      logger.info("Successfully connected to Kafka cluster. Cluster ID: {}", clusterId);
    } catch (Exception e) {
      logger.error("Failed to connect to Kafka cluster: {}", e.getMessage(), e);
    }
  }
}
