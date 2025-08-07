package com.example.tutorial.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Base Data Transfer Object (DTO) class that can be extended by other DTOs.
 * It includes common fields such as id, createdAt, updatedAt, version, and data. example dto for Campaign:
 * <pre>
 *   {@code
 *    {
 *      "id": "12345",
 *      "created_at": "2023-10-01T12:00:00",
 *      "updated_at": "2023-10-01T12:00:00",
 *      "version": 1,
 *      "data": {
 *        "name": "Campaign Name",
 *        "description": "Campaign Description",
 *        "status": "ACTIVE",
 *        "start_date": "2023-10-01T00:00:00",
 *        "end_date": "2023-10-31T23:59:59",
 *        "budget": 2390.07,
 *        "offer_ids": [
 *          "offer::1"
 *         ]
 *      }
 *    }
 *   }
 * </pre>
 *
 * @param <T> the type of data contained in this DTO
 */

@Document
public class BaseDto<T> {

  @Id
  @JsonProperty("id")
  private String id;

  @JsonProperty("created_at")
  @NotNull(message = "Created date is required")
  private LocalDateTime createdAt;

  @JsonProperty("updated_at")
  private LocalDateTime updatedAt;

  @JsonProperty("version")
  @NotNull(message = "Version is required")
  private Integer version;

  /**
   * The `data` field is generic, allowing flexibility in the type of data it holds (e.g., Campaign, Offer, etc.).
   * <p>
   * For object-to-string conversion or vice versa, use `TypeReference` for deserialization:
   * <pre>
   *   {@code
   *      ObjectMapper objectMapper = new ObjectMapper();
   *      BaseDto<Campaign> campaignDto = objectMapper.readValue(jsonString, new TypeReference<BaseDto<Campaign>>() {});
   *   }
   * </pre>
   * <p>
   * Note: `TypeReference` is required instead of `Campaign.class` because the latter does not work with generics.
   */
  @JsonProperty("data")
  @NotNull(message = "Data is required")
  private T data;

  /**
   * Static factory method to create a new BaseDto instance with the provided data.
   *
   * @param data the data to be set in the DTO
   * @param <T> the type of data i.e. Campaign, Offer, etc.
   * @return a new BaseDto instance with the provided data and current timestamp
   */
  public static <T> BaseDto<T> build(T data) {
    BaseDto<T> dto = new BaseDto<>();
    dto.setData(data);
    dto.setCreatedAt(LocalDateTime.now());
    dto.setVersion(1);
    return dto;
  }

  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
  public Integer getVersion() {return version;}
  public void setVersion(Integer version) {this.version = version;}
  public T getData() {
    return data;
  }
  public void setData(T data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "BaseDto{" +
           "id='" + id + '\'' +
           ", createdAt='" + createdAt + '\'' +
           ", updatedAt='" + updatedAt + '\'' +
           ", version='" + version + '\'' +
           ", data=" + data +
           '}';
  }
}
