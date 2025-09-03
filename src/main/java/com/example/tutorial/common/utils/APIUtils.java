package com.example.tutorial.common.utils;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.exceptions.ApplicationTechnicalException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Utility class providing blocking REST API calls using Vert.x WebClient.
 * <p>
 * This class serves as a simple replacement for Spring's APIUtils with RestTemplate.
 * It offers blocking facades that return {@code Optional} or {@code List} results,
 * suitable for use from worker threads or off the event loop.
 * <ul>
 *   <li>HTTP 200: Response is parsed as JSON using Jackson.</li>
 *   <li>HTTP 204/404: Returns {@code Optional.empty()} or {@code List.of()}.</li>
 *   <li>Other status codes: Throws {@link ApplicationTechnicalException}.</li>
 * </ul>
 * <p>
 *
 * Internally, asynchronous Vert.x operations are bridged to blocking calls using {@code CompletableFuture}:
 * <ol>
 *   <li>Create a {@code CompletableFuture}.</li>
 *   <li>Start the async HTTP operation.</li>
 *   <li>Complete or completeExceptionally the future in the async callback.</li>
 *   <li>Block and wait for the result or timeout using {@code future.get(timeout)}.</li>
 * </ol>
 */
public final class APIUtils {

  private static final Logger log = LoggerFactory.getLogger(APIUtils.class);

  private final WebClient webClient;
  private final ObjectMapper objectMapper;
  private final long timeoutMs;

  public APIUtils(Vertx vertx) {
    this(vertx, defaultMapper(), Duration.ofSeconds(5).toMillis());
  }
  public APIUtils(Vertx vertx, ObjectMapper objectMapper, long timeoutMs) {
    this.webClient = WebClient.create(vertx);
    this.objectMapper = objectMapper == null ? defaultMapper() : objectMapper;
    this.timeoutMs = timeoutMs > 0 ? timeoutMs : Duration.ofSeconds(5).toMillis();
  }

  /**
   * Fetch a single DTO by ID from a REST API endpoint.
   *
   * @param apiUrl base URL of the API (e.g. "http://localhost:8080/api/resource")
   * @param id ID of the object to fetch (will be URL-encoded and appended to apiUrl)
   * @param dtoTypeReference TypeReference for deserializing the response (e.g. new TypeReference<BaseDto<MyDto>>() {})
   * @return Optional containing the DTO if found, or Optional.empty() if not found (204/404)
   * @param <T> Type of the DTO's data field
   * @throws ApplicationTechnicalException on HTTP errors or parsing issues
   */
  public <T> Optional<BaseDto<T>> fetchDtoById(
      String apiUrl,
      String id,
      TypeReference<BaseDto<T>> dtoTypeReference
  ) {
    final String url = apiUrl+"/"+id;
    log.info("Fetching data from REST API: {}", url);

    // Create CompletableFuture to hold the result
    CompletableFuture<Optional<BaseDto<T>>> future = new CompletableFuture<>();
    // Start async HTTP GET
    webClient.getAbs(url).send(ar -> {
      if (ar.failed()) {
        future.completeExceptionally(
            new ApplicationTechnicalException("Error fetching data from API: " + url, ar.cause())
        );
        return;
      }

      // Process HTTP response
      HttpResponse<Buffer> res = ar.result();
      int status = res.statusCode();
      String body = res.bodyAsString();
      log.debug("REST API Response from {}: HTTP {} body={}", url, status, body);
      try {
        if (status == HttpResponseStatus.OK.code()) {
          BaseDto<T> value = objectMapper.readValue(body, dtoTypeReference);
          // Successfully fetched and parsed object
          future.complete(Optional.ofNullable(value));
        } else if (status == HttpResponseStatus.NO_CONTENT.code() || status == HttpResponseStatus.NOT_FOUND.code()) {
          // treat as empty result (matches RestTemplate NotFound handling)
          future.complete(Optional.empty());

        } else {
          future.completeExceptionally(new ApplicationTechnicalException(
              "HTTP " + status + " from " + url + ": " + body));
        }
      } catch (Exception e) {
        future.completeExceptionally(new ApplicationTechnicalException("Error parsing object's value", e));
      }
    });

    try {
      return future.get(timeoutMs, TimeUnit.MILLISECONDS);
    } catch (Exception e) {
      throw new ApplicationTechnicalException("fetchDtoById failed for " + url, e);
    }
  }

  /**
   * Fetch a list of DTOs from a REST API endpoint.
   *
   * @param apiUrl base URL of the API (e.g. "http://localhost:8080/api/resources")
   * @param typeReference TypeReference for deserializing the response (e.g. new TypeReference<List<BaseDto<MyDto>>>() {})
   * @return List of DTOs, or empty list if none found (204/404)
   * @param <T> Type of the DTO's data field
   * @throws ApplicationTechnicalException on HTTP errors or parsing issues
   */
  public <T> List<BaseDto<T>> fetchDtoList(
      String apiUrl,
      TypeReference<List<BaseDto<T>>> typeReference
  ) {
    log.info("Fetching data from REST API: {}", apiUrl);

    // Create CompletableFuture to hold the result
    CompletableFuture<List<BaseDto<T>>> cf = new CompletableFuture<>();
    // Start async HTTP GET
    webClient.getAbs(apiUrl).send(ar -> {
      if (ar.failed()) {
        cf.completeExceptionally(
            new ApplicationTechnicalException("Error fetching data from API: " + apiUrl, ar.cause())
        );
        return;
      }

      // Process HTTP response
      HttpResponse<Buffer> res = ar.result();
      int status = res.statusCode();
      String body = res.bodyAsString();
      log.debug("REST API Response from {}: HTTP {} body={}", apiUrl, status, body);
      try {
        if (status == HttpResponseStatus.OK.code()) {
          List<BaseDto<T>> list = objectMapper.readValue(body, typeReference);
          // Successfully fetched and parsed list
          cf.complete(list);
        } else if (status == HttpResponseStatus.NO_CONTENT.code() || status == HttpResponseStatus.NOT_FOUND.code()) {
          // treat as empty result (matches RestTemplate NotFound handling)
          cf.complete(List.of());

        } else {
          cf.completeExceptionally(new ApplicationTechnicalException(
              "HTTP " + status + " from " + apiUrl + ": " + body)
          );
        }
      } catch (Exception e) {
        cf.completeExceptionally(new ApplicationTechnicalException("Error parsing object's value", e));
      }
    });

    try {
      return cf.get(timeoutMs, TimeUnit.MILLISECONDS);
    } catch (Exception e) {
      throw new ApplicationTechnicalException("fetchDtoList failed for " + apiUrl, e);
    }
  }


  // --- Private helpers --- //
  private static ObjectMapper defaultMapper() {
    return new ObjectMapper()
        .findAndRegisterModules()
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

}
