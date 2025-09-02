package com.example.dto;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document
public class OfferEnrollment {


    @Id
    @NotBlank
    @JsonProperty("enrollment_id")
    @Field("enrollment_id")
    private String enrollmentId;
    

    @NotBlank
    @JsonProperty("customer_id")
    @Field("customer_id")
    private String customerId;
    

    @NotBlank
    @JsonProperty("offer_id")
    @Field("offer_id")
    private String offerId;
    

    @NotNull
    @JsonProperty("enrolled_at")
    @Field("enrolled_at")
    private LocalDateTime enrolledAt;
    

    public String getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(String enrollmentId) { this.enrollmentId = enrollmentId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getOfferId() { return offerId; }
    public void setOfferId(String offerId) { this.offerId = offerId; }
    public LocalDateTime getEnrolledAt() { return enrolledAt; }
    public void setEnrolledAt(LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; }

    @Override
    public String toString() {
        return "OfferEnrollment{"  + ", ".join(["enrollmentId=" + enrollmentId, "customerId=" + customerId, "offerId=" + offerId, "enrolledAt=" + enrolledAt]) + "}";
    }
}