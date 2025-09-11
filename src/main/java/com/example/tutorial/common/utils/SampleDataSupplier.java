package com.example.tutorial.common.utils;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.datamodel.customer.Customer;
import com.example.tutorial.common.datamodel.merchant.Merchant;
import com.example.tutorial.common.datamodel.offer.Offer;
import com.example.tutorial.common.datamodel.redemption.Redemption;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Simple Suppliers that load JSON arrays of BaseDto<T> from hardcoded resource paths
 * and also expose flattened List<T> via BaseDto::getData.
 *
 * Expected files (JSON arrays of BaseDto<T>):
 *  - src/main/resources/sample_data/offers.json
 *  - src/main/resources/sample_data/campaigns.json
 *  - src/main/resources/sample_data/merchants.json
 *  - src/main/resources/sample_data/customers.json
 *  - src/main/resources/sample_data/redemptions.json
 *
 * Usage:
 *   List<BaseDto<Offer>> offerDtos = SampleDataSupplier.OFFERS_DTO.get();
 *   List<Offer>          offers    = SampleDataSupplier.OFFERS.get(); // flattened
 */
public final class SampleDataSupplier {

  private static final ObjectMapper MAPPER = new ObjectMapper()
      .registerModule(new JavaTimeModule())
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  private SampleDataSupplier() {}

  // ====== DTO suppliers (read BaseDto<T>[] from JSON) ======
  public static final Supplier<List<BaseDto<Offer>>> OFFERS_DTO =
      () -> readBaseDtoArrayFile("src/main/resources/sample_data/offers.json", Offer.class);

  public static final Supplier<List<BaseDto<Campaign>>> CAMPAIGNS_DTO =
      () -> readBaseDtoArrayFile("src/main/resources/sample_data/campaigns.json", Campaign.class);

  public static final Supplier<List<BaseDto<Merchant>>> MERCHANTS_DTO =
      () -> readBaseDtoArrayFile("src/main/resources/sample_data/merchants.json", Merchant.class);

  public static final Supplier<List<BaseDto<Customer>>> CUSTOMERS_DTO =
      () -> readBaseDtoArrayFile("src/main/resources/sample_data/customers.json", Customer.class);

  public static final Supplier<List<BaseDto<Redemption>>> REDEMPTIONS_DTO =
      () -> readBaseDtoArrayFile("src/main/resources/sample_data/redemptions.json", Redemption.class);

  // ====== Flattened suppliers (map BaseDto<T>::getData → List<T>) ======
  public static final Supplier<List<Offer>> OFFERS =
      () -> flatten(OFFERS_DTO.get());

  public static final Supplier<List<Campaign>> CAMPAIGNS =
      () -> flatten(CAMPAIGNS_DTO.get());

  public static final Supplier<List<Merchant>> MERCHANTS =
      () -> flatten(MERCHANTS_DTO.get());

  public static final Supplier<List<Customer>> CUSTOMERS =
      () -> flatten(CUSTOMERS_DTO.get());

  public static final Supplier<List<Redemption>> REDEMPTIONS =
      () -> flatten(REDEMPTIONS_DTO.get());

  // ====== Helpers ======
  private static <T> List<BaseDto<T>> readBaseDtoArrayFile(String path, Class<T> elementType) {
    try {
      String json = Files.readString(Paths.get(path));
      JavaType dtoType   = MAPPER.getTypeFactory()
          .constructParametricType(BaseDto.class, elementType);
      JavaType arrayType = MAPPER.getTypeFactory()
          .constructArrayType(dtoType);

      @SuppressWarnings("unchecked")
      BaseDto<T>[] arr = (BaseDto<T>[]) MAPPER.readValue(json, arrayType);
      return Stream.of(arr).collect(Collectors.toList());
    } catch (Exception e) {
      throw new IllegalStateException("Failed to read BaseDto<" + elementType.getSimpleName() + "> array from " + path, e);
    }
  }

  private static <T> List<T> flatten(List<BaseDto<T>> dtos) {
    return dtos.stream()
        .map(BaseDto::getData)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }
}
