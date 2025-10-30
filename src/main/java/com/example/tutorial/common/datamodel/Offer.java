package com.example.tutorial.common.datamodel;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Offer {

  private String offerId;
  private String campaignId;

  private Partner partner;
  private Merchant merchant;

  private String title;
  private String description;

  private Schedule schedule;
  private Eligibility eligibility;
  private Limits limits;
  private Reward reward;
  private Stacking stacking;

  private Status status;
  private Analytics analytics;
  private Technical technical;
  private Audit audit;
  private Compliance compliance;

  private RedemptionRules redemptionRules;
  private Settlement settlement;

    /* ============================
       Nested Models
    ============================ */

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Partner {
    private String partnerId;
    private String name;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Merchant {
    private String merchantId;
    private String name;
    private String mcc;
    private List<String> brands;
    private List<Location> locations;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Location {
    private String storeId;
    private String city;
    private String state;
    private String country;
    private Geo geo;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Geo {
    private double lat;
    private double lng;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Schedule {
    private OffsetDateTime start;
    private OffsetDateTime end;
    private String timezone;
    private List<Weekday> daysOfWeek;
    private List<LocalDate> blackoutDates;
  }

  public enum Weekday {
    MON, TUE, WED, THU, FRI, SAT, SUN
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Eligibility {
    private List<String> customerSegments;
    private boolean enrollmentRequired;
    private Money minSpend;
    private List<String> categories;
    private List<Channel> channels;
    private List<String> cardProducts;
    private List<String> merchantAllowlist;
    private List<String> merchantDenylist;
    private List<String> excludedMCCs;
  }

  public enum Channel {
    IN_STORE, MOBILE_ORDER, ONLINE
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Limits {
    private Money perTxnCap;
    private PerCustomer perCustomer;
    private PerOffer perOffer;
    private Velocity velocity;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class PerCustomer {
    private Integer count;
    private Money amount;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class PerOffer {
    private Integer count;
    private Money amount;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Velocity {
    private Integer dailyCount;
    private Money weeklyAmount;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Reward {
    private RewardType type;
    private Money amount;
    private Double percent;
    private String currency;
    private String notes;
  }

  public enum RewardType {
    AMOUNT, PERCENT
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Stacking {
    private boolean exclusive;
    private List<String> allowedWith;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Status {
    private OfferState state;
    private String reason;
    private OffsetDateTime updatedAt;
    private List<StatusChange> history;
  }

  public enum OfferState {
    DRAFT, APPROVED, ACTIVE, PAUSED
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class StatusChange {
    private OfferState state;
    private OffsetDateTime at;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Analytics {
    private Integer impressions;
    private Integer clicks;
    private Integer redemptions;
    private Double ctr;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Technical {
    private String idempotencyKey;
    private Integer dailyTpsHint;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Audit {
    private String createdBy;
    private OffsetDateTime createdAt;
    private String lastModifiedBy;
    private OffsetDateTime lastModifiedAt;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Compliance {
    private String termsUrl;
    private List<String> restrictedRegions;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class RedemptionRules {
    private List<Channel> channels;
    private CodeType codeType;
    private Boolean issuerFunding;
  }

  public enum CodeType {
    NONE, BARCODE, QRCODE, PROMO_CODE
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Settlement {
    private FundingModel fundingModel;
    private Integer reimbursementWindowDays;
  }

  public enum FundingModel {
    ISSUER, PARTNER
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Money {
    @JsonAlias({"value", "amount"})
    private BigDecimal value;
    private String currency;
  }
}
