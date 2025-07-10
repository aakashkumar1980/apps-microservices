package com.example.tutorial.common.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:application-rediscache.properties")
public class RedisConnectionReadyEvent {

  private static final Logger logger = LoggerFactory.getLogger(RedisConnectionReadyEvent.class);

  @Autowired
  private RedisConnectionFactory redisConnectionFactory;

  @EventListener(ApplicationReadyEvent.class)
  public void onApplicationReady() {
    try (RedisConnection connection = redisConnectionFactory.getConnection()) {
      String pong = connection.ping();
      if ("PONG".equalsIgnoreCase(pong)) {
        logger.info("Successfully connected to Redis cluster.");
      } else {
        logger.warn("Connected to Redis, but ping response was: {}", pong);
      }
    } catch (Exception e) {
      logger.error("Failed to connect to Redis cluster.", e);
    }
  }
}
