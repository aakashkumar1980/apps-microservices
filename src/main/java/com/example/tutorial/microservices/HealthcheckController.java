package com.example.tutorial.microservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/healthcheck")
public class HealthcheckController {

  private static final Logger log = LoggerFactory.getLogger(HealthcheckController.class);

  @Value("${server.profile}")
  private String serverProfile;

  /**
   * Healthcheck endpoint to verify if the service is running.
   * @return ResponseEntity with a message indicating the service status.
   */
  @GetMapping
  public ResponseEntity<String> healthcheck() {
    return ResponseEntity.ok("Service is up and running in profile: " + serverProfile);
  }
}
