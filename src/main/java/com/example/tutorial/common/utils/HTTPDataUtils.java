package com.example.tutorial.common.utils;

import com.example.tutorial.common.exceptions.ApplicationTechnicalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public final class HTTPDataUtils {
  private static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";
  private static final ObjectMapper MAPPER = new ObjectMapper()
      .findAndRegisterModules()
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

  private HTTPDataUtils() {}

  /**
   * Parses the JSON body of the request into an instance of the specified class.
   * @param ctx the routing context
   * @param type the class to parse the body into
   * @return an instance of the specified class
   * @param <T> the type of the class
   * @throws IllegalArgumentException if the body is missing or cannot be parsed
   */
  public static <T> T requestBodyToObject(RoutingContext ctx, Class<T> type) {
    try {
      String raw = ctx.body().asString();
      if (raw == null || raw.isEmpty()) {
        throw new ApplicationTechnicalException("Request body is missing" );
      }
      return MAPPER.readValue(raw, type);
    } catch (JsonProcessingException e) {
      throw new ApplicationTechnicalException("Error processing JSON", e);
    }
  }

  /**
   * It creates a standard JSON response for successful requests (HTTP 200).
   * @param ctx the routing context
   * @param data the data to include in the response
   */
  public static void responseOk(RoutingContext ctx, Object data) {
    JsonObject payload = envelope(ctx, 200, "OK", data, null);
    ctx.response()
        .setStatusCode(200)
        .putHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON)
        .end(payload.encode());
  }

  /**
   * It creates a standard JSON response for resource creation (HTTP 201).
   *
   * @param ctx the routing context
   * @param data the data to include in the response
   * @param location the location of the created resource
   */
  public static void responseCreated(RoutingContext ctx, Object data, String location) {
    JsonObject payload = envelope(ctx, 201, "Created", data, null);
    ctx.response()
        .setStatusCode(201)
        .putHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON)
        .putHeader(HttpHeaders.LOCATION, location)
        .end(payload.encode());
  }

  /**
   * It creates a standard JSON response for bad requests (HTTP 400).
   *
   * @param ctx the routing context
   * @param message the error message
   * @param errors a list of specific errors
   */
  public static void responseBadRequest(RoutingContext ctx, String message, List<String> errors) {
    if (message == null || message.isEmpty()) message = "Bad Request";
    JsonObject payload = envelope(ctx, 400, message, null, errors);
    ctx.response()
        .setStatusCode(400)
        .putHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON)
        .end(payload.encode());
  }

  // -- Private helpers -- //
  private static JsonObject envelope(RoutingContext ctx, Integer status, String message, Object data, List<?> errors) {
    JsonObject json = new JsonObject()
        .put("timestamp", Instant.now().toString());
    if (ctx != null) {
      json.put("path", ctx.normalizedPath());
      json.put("method", ctx.request().method().name());
    }
    if (status != null)  json.put("status", status);
    if (message != null) json.put("message", message);
    if (errors != null)  json.put("errors", errors);
    if (data != null)    json.put("data", toJsonObject(data));
    return json;
  }

  private static JsonObject toJsonObject(Object value) {
    if (value == null) return new JsonObject();
    if (value instanceof JsonObject) return (JsonObject) value;
    try {
      return new JsonObject(MAPPER.writeValueAsString(value));
    } catch (Exception e) {
      return new JsonObject().put("value", Objects.toString(value));
    }
  }
}
