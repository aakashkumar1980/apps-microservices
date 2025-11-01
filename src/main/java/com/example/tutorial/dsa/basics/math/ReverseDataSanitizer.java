package com.example.tutorial.dsa.basics.math;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Reverse Data
 * ----------------------------------
 * <p>This program demonstrates integer manipulation and overflow handling.
 * The goal is to reverse the digits of a given integer, such as 123 → 321 or -456 → -654.
 * If the reversed integer goes beyond the signed 32-bit integer range, it returns 0.
 *
 * <p><b>Real UseCase:</b> In a credit-card offer platform, transaction IDs or payloads
 * may sometimes arrive in reverse order due to formatting errors or system transformations.
 * This code shows how such numeric payloads can be sanitized by reversing them safely
 * while maintaining sign and avoiding integer overflow.
 *
 * <p>This example also loads a few sample offers from offer.json and pretends to sanitize
 * their numeric suffix (like offerId numbers) using reverse logic.
 */
@Component
public class ReverseDataSanitizer implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(ReverseDataSanitizer.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    offers.forEach(offer -> System.out.println("Loaded Offer: " + offer));

    System.out.print("\n--- Running Reverse Logic on Offer ---\n");
    offers.forEach(offer ->  System.out.println("MCC: "+offer.getMerchant().getMcc()
        +" | MCC(reverse): "+reverseNumeric(Integer.valueOf(offer.getMerchant().getMcc()))));
    offers.forEach(offer ->  System.out.println("OfferId: "+offer.getOfferId()
        +" | OfferId(reverse): "+reverseAlphaNumeric(offer.getOfferId())));
  }


  /**
   * Reverse the digits of a signed 32‑bit integer using a loop-based pop-and-push approach.
   * In summary, extract[Pop] the last digit (from right) and append[Push] it to the result (to left).
   *
   * <p>Loop mechanics (per-iteration semantics):
   * <ol>
   *   <li>Pop: {@code lastDigit = x % 10;} — extracts the last decimal digit using <b>modulus `%`</b> operation which returns the remainder.</li>
   *   <li>Push: {@code result = result * 10 + lastDigit;} — shift accumulated digits left and append.</li>
   *   <li>Advance: {@code x /= 10;} — remove the last digit. Integer <b>division `/`</b> truncates toward zero.</li>
   * </ol>
   *
   * <p>Loop invariant: before each iteration {@code result} holds the reversed digits processed so far,
   * and {@code x} holds the remaining prefix yet to process. Each iteration transfers one digit from
   * {@code x} to {@code result}.</p>
   *
   * <p>Per-iteration example for input {@code 123}:
   * <pre>
   * Initial: x = 123, result = 0
   * Iteration 1:
   *   [POP] lastDigit(x % 10)                  -> 123 % 10 = 3
   *   [RESULT] result(result * 10 + lastDigit) -> 0 * 10 + 3 = 3
   *   [ADVANCE] x(x /= 10)                     -> 123 / 10 = 12
   *
   * Now: x = 12, result = 3
   * Iteration 2:
   *   [POP] lastDigit(x % 10)                  -> 12 % 10 = 2
   *   [RESULT] result(result * 10 + lastDigit) -> 3 * 10 + 2 = 32
   *   [ADVANCE] x(x /= 10)                     -> 12 / 10 = 1
   *
   * Now: x = 1, result = 32
   * Iteration 3:
   *   [POP] lastDigit(x % 10)                  -> 1 % 10 = 1
   *   [RESULT] result(result * 10 + lastDigit) -> 32 * 10 + 1 = 321
   *   [ADVANCE] x(x /= 10) -> 1 / 10 = 0
   *
   * Final: result = 321
   * </pre>
   *
   * @param x input integer whose digits are to be reversed
   * @return the integer formed by reversing the decimal digits of {@code x}.
   */
  public static int reverseNumeric(int x) {
    long result = 0; // accumulator (use long to detect overflow)

    // Process digits until none remain. Example progression: x = 123 -> 12 -> 1 -> 0
    while (x != 0) {
      int lastDigit = x % 10;           // pop
      result = result * 10 + lastDigit; // push
      x /= 10;                          // advance
    }
    return (int) result;
  }


  /**
   * Core reverse string logic while preserving non-alphanumeric character positions.
   * LOGIC: Use two-pointer technique to swap alphanumeric characters from start and end.
   *
   * @param s input string
   * @return reversed string with non-alphanumeric characters in original positions
   */
  public static String reverseAlphaNumeric(String s) {
    // step 1: convert string to char array for in-place manipulation
    char[] arr = s.toCharArray();
    // step 2: loop with two pointers from start and end and basically swap the opposite end characters
    int i = 0, j = arr.length-1;
    while (i < j) {
      // fetch left and right character in a variables
      char leftCharacter = arr[i];
      char rightCharacter = arr[j];
      // next, assign the left character to the right character and similarly for right character
      arr[i] = rightCharacter;
      arr[j] = leftCharacter;
      // then move the left pointer to the right and right pointer to the left
      i++;
      j--;
    }
    return new String(arr);
  }
}


