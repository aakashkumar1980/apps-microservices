package com.example.tutorial.service;

public interface KafkaHealthService {
    boolean checkKafkaConnection();
    void resetKafkaProducerFactory();
}