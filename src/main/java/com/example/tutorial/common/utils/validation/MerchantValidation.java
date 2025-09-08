package com.example.tutorial.common.utils.validation;

import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.List;
import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MerchantValidation {
  private static final Logger log = LoggerFactory.getLogger(MerchantValidation.class);
  public MerchantValidation() {}

  /**
   * Asynchronously validates the merchantId.
   *
   * @param merchantId the merchant ID to validate
   * @return a Future with a list of error messages; empty if valid
   */
  public Future<List<String>> validateMerchantIdAsync(String merchantId) {
    return Future.succeededFuture(validateMerchantId(merchantId));
  }

  // -- PRIVATE METHODS -- //
  private List<String> validateMerchantId(String merchantId) {
    try {Thread.sleep(1000);} catch (InterruptedException e) {throw new RuntimeException(e);}
    log.info("Validating merchantId: {}", merchantId);

    List<String> errors = new ArrayList<>();
    if (StringUtils.isBlank(merchantId)) {
      errors.add("merchantId is required");
      return errors;
    }

    // Example format rule: alphanumeric + dashes, length 3..64 (adjust to your rules)
    if (!merchantId.matches("[A-Za-z0-9\\-]{3,64}")) {
      errors.add("merchantId must be 3–64 chars (alphanumeric or '-').");
    }
    return errors;
  }
}
