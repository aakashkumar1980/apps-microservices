package com.example.tutorial.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Base Data Transfer Object (DTO) class that can be extended by other DTOs.
 * It includes common fields such as id, createdAt, version, and data.
 *
 * @param <T> the type of data contained in this DTO
 */

@Document
public class BaseDto<T> {

  @Id
  @JsonProperty("id")
  private String id;

  @JsonProperty("created_at")
  @FutureOrPresent(message = "Created date must be in the future or present")
  @NotNull(message = "Created date is required")
  private LocalDateTime createdAt;

  @JsonProperty("updated_at")
  @FutureOrPresent(message = "Updated date must be in the future or present")
  private LocalDateTime updatedAt;

  @JsonProperty("data")
  @NotNull(message = "Data is required")
  private T data;

  /**
   * Static factory method to create a new BaseDto instance with the provided data.
   *
   * @param data the data to be set in the DTO
   * @param <T> the type of data
   * @return a new BaseDto instance with the provided data and current timestamp
   */
  public static <T> BaseDto<T> build(T data) {
    BaseDto<T> dto = new BaseDto<>();
    dto.setData(data);
    dto.setCreatedAt(LocalDateTime.now());
    return dto;
  }

  // Getters and Setters
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
           ", data=" + data +
           '}';
  }
}
