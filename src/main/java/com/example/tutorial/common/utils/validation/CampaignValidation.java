package com.example.tutorial.common.utils.validation;

import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.List;
import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CampaignValidation {
  private static final Logger log = LoggerFactory.getLogger(CampaignValidation.class);

  public CampaignValidation() {}

  /**
   * Asynchronously validates the campaignId.
   *
   * @param campaignId the campaign ID to validate
   * @return a Future with a list of error messages; empty if valid
   */
  public Future<List<String>> validateCampaignIdAsync(String campaignId) {
    return Future.succeededFuture(validateCampaignId(campaignId));
  }

  // -- PRIVATE METHODS -- //
  private List<String> validateCampaignId(String campaignId) {
    try {Thread.sleep(1000);} catch (InterruptedException e) {throw new RuntimeException(e);}
    log.info("Validating campaignId: {}", campaignId);

    List<String> errors = new ArrayList<>();
    if (StringUtils.isBlank(campaignId)) {
      errors.add("campaignId is required");
      return errors;
    }

    // Example rule: uppercase letters + digits + underscore, 3..64 (change to your domain)
    if (!campaignId.matches("[A-Z0-9_]{3,64}")) {
      errors.add("campaignId must be 3–64 chars (A–Z, 0–9, underscore).");
    }
    return errors;
  }


}
