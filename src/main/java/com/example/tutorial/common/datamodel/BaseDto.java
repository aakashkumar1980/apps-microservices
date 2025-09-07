package com.example.tutorial.common.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.ClassUtils;

import java.time.LocalDateTime;

/**
 * Base Data Transfer Object (DTO) class that can be embedded by other DTOs.
 * It includes common fields such as id, createdAt, updatedAt, version, and data. example datamodel for Campaign:
 * <pre>
 *   {@code
 *    {
 *      "id": "campaign::1",
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
 *        "linked_offers": [
 *          "offer::1"
 *         ]
 *      }
 *    }
 *   }
 * </pre>
 *
 * @param <T> the type of data contained in this DTO
 */

public class BaseDto<T> {

  /**
   * Unique identifier for the DTO, typically in the format of "type::id" (e.g., "campaign::1").
   * This field is annotated with @Id to indicate that it is the primary key in the database.
   * <pre>
   *   The id is generated in the format of "type::id" where type is the name of the DTO and the id is a unique identifier,
   *   obtained from the DB.
   *   DBUtils.getUniqueCounter(...).
   * </pre>
   */
  @JsonProperty("id")
  private String id = "dto:1";

  @JsonProperty("version")
  private Integer version;

  @JsonProperty("created_at")
  private LocalDateTime createdAt;

  @JsonProperty("created_by")
  private String createdBy;

  @JsonProperty("updated_at")
  private LocalDateTime updatedAt;

  @JsonProperty("updated_by")
  private String updatedBy;

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
    dto.setCreatedBy(String.format(ClassUtils.getSimpleName(data) + " Microservice"));
    dto.setVersion(1);
    return dto;
  }

  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public Integer getVersion() {return version;}
  public void setVersion(Integer version) {this.version = version;}
  public LocalDateTime getCreatedAt() {return createdAt;}
  public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
  public String getCreatedBy() {return createdBy;}
  public void setCreatedBy(String createdBy) {this.createdBy = createdBy;}
  public LocalDateTime getUpdatedAt() {return updatedAt;}
  public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}
  public String getUpdatedBy() {return updatedBy;}
  public void setUpdatedBy(String updatedBy) {this.updatedBy = updatedBy;}
  public T getData() {return data;}
  public void setData(T data) {this.data = data;}

  @Override
  public String toString() {
    return "BaseDto{" +
           "id='" + id + '\'' +
            ", version=" + version +
           ", createdAt='" + createdAt + '\'' +
          ", createdBy='" + createdBy + '\'' +
           ", updatedAt='" + updatedAt + '\'' +
          ", updatedBy='" + updatedBy + '\'' +
           ", data=" + data +
           '}';
  }
}
