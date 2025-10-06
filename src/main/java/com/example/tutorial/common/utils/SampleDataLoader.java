package com.example.tutorial.common.utils;

import com.example.tutorial.common.datamodel.Offer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Supplier;

public final class SampleDataLoader {

  private static final ObjectMapper MAPPER = new ObjectMapper()
      .registerModule(new JavaTimeModule())
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  public static final Supplier<List<Offer>> OFFERS_DTO =
      () -> {
        try {
          String json = Files.readString(Paths.get("src/main/resources/sample_data/offer.json"));
          return (List<Offer>) MAPPER.readValue(json, new TypeReference<List<Offer>>() {
          });
        } catch (Exception e) {
          e.printStackTrace();
        }
        return List.of();
      };
}
