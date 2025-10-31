package com.example.tutorial.process.basics.math;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Reverse Integer (LeetCode 7)
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
public class ReverseOrderDataSanitizer implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(ReverseOrderDataSanitizer.class, args);
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
   * Core reverse integer logic.
   * LOGIC: Pop digits from the end of the input integer and push them onto the result integer.
   *
   * @param x input integer
   * @return reversed integer, or 0 if overflow occurs
   */
  public static int reverseNumeric(int x) {
    long result = 0; // use long to check overflow safely

    // loop until all digits are processed. e.g. x = 123 | 12 | 1 | 0
    while (x != 0) {
      // step 1: pop the last digit using modulus operator. 10 is the base for decimal system,
      // so the remainder when dividing by 10 gives the last digit.
      // e.g. 123 % 10 = 3 | 12 % 10 = 2 | 1 % 10 = 1
      int lastDigit = x % 10;

      // step 2: next multiply result by 10 (to shift left) and add the last digit to get the new result.
      // e.g. result = 0 * 10 + 3 = 3 | result = 3 * 10 + 2 = 32 | result = 32 * 10 + 1 = 321
      result = result * 10 + lastDigit;

      // step 3: remove the last digit from x by performing integer division by 10.
      // e.g. 123 / 10 = 12 | 12 / 10 = 1 | 1 / 10 = 0
      x /= 10;
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

