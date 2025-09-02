package com.example.tutorial.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.util.backoff.FixedBackOff;

import java.time.Duration;

@Configuration
public class KafkaErrorHandlerConfig {

    @Bean
    public DefaultErrorHandler kafkaErrorHandler() {
        long[] fibonacciDelays = {60000, 120000, 180000, 300000, 600000}; // 1m, 2m, 3m, 5m, 10m
        DefaultErrorHandler errorHandler = new DefaultErrorHandler((ConsumerRecord<?, ?> record, Exception ex) -> {
            System.err.println("Error processing Kafka message: " + ex.getMessage());
        });

        for (long delay : fibonacciDelays) {
            errorHandler.addRetryableExceptions(Exception.class);
            errorHandler.setBackOff(new FixedBackOff(delay, 1));
        }

        return errorHandler;
    }

    @Bean
    public KafkaListenerErrorHandler globalKafkaListenerErrorHandler() {
        return (message, exception) -> {
            System.err.println("Global Kafka Error Handler: " + exception.getMessage());
            return null;
        };
    }
}