package com.example.tutorial.dsa.basics.math;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ReverseDataSanitizer
 * ----------------------------------
 * <p>This program demonstrates digit/integer reversal.
 * The core problem solved here is reversing an integer (LeetCode #7).
 *
 * <p><b>Problem Statement:</b>
 * Given a signed 32-bit integer x, return x with its digits reversed.
 * If reversing x causes the value to go outside the signed 32-bit integer range, return 0.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Data masking - obfuscate offer IDs or transaction amounts</li>
 *   <li>ID generation - create reversed IDs for internal tracking</li>
 *   <li>Validation checksums - reverse and compare for palindrome checks</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: 123 → Output: 321</li>
 *   <li>Input: -123 → Output: -321</li>
 *   <li>Input: 120 → Output: 21</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Facebook
 *
 * @see <a href="https://leetcode.com/problems/reverse-integer/">LeetCode 7 - Reverse Integer</a>
 */
@Component
public class ReverseDataSanitizer implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(ReverseDataSanitizer.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== ReverseDataSanitizer: Integer Reversal Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate reversing integers
    System.out.println("--- Reversing Transaction IDs (simulated) ---\n");
    int[] testCases = {123, -456, 1200, 2147483647, -2147483648, 0};

    for (int num : testCases) {
      int reversed = reverse(num);
      System.out.printf("Original: %12d → Reversed: %12d%n", num, reversed);
    }
  }

  /**
   * Reverses the digits of an integer.
   *
   * <p><b>LOGIC:</b>
   * <ol>
   *   <li>Extract the last digit using modulo (x % 10)</li>
   *   <li>Add it to the result after shifting result left (result * 10)</li>
   *   <li>Remove the last digit from x (x / 10)</li>
   *   <li>Check for overflow before each operation</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * Input: x = 123
   *
   * Step 1: digit = 123 % 10 = 3, result = 0*10 + 3 = 3,   x = 123/10 = 12
   * Step 2: digit = 12 % 10 = 2,  result = 3*10 + 2 = 32,  x = 12/10 = 1
   * Step 3: digit = 1 % 10 = 1,   result = 32*10 + 1 = 321, x = 1/10 = 0
   *
   * Return 321
   * </pre>
   *
   * <p><b>Time Complexity: O(log n)</b>
   * <br>We process each digit once. Number of digits = log10(n).
   * <br><i>Like reading digits of a number - more digits = more work.</i>
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>We only use a few variables.
   * <br><i>Like using your fingers to count - no extra paper needed.</i>
   *
   * @param x the integer to reverse
   * @return the reversed integer, or 0 if overflow occurs
   */
  public static int reverse(int x) {
    int result = 0;

    while (x != 0) {
      // Extract the last digit.
      int digit = x % 10;

      // Check for overflow BEFORE multiplying.
      // If result > MAX/10, then result*10 will overflow.
      // If result == MAX/10 and digit > 7, then result*10 + digit will overflow.
      if (result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && digit > 7)) {
        return 0;
      }
      if (result < Integer.MIN_VALUE / 10 || (result == Integer.MIN_VALUE / 10 && digit < -8)) {
        return 0;
      }

      // Build the reversed number.
      result = result * 10 + digit;

      // Remove the last digit from x.
      x = x / 10;
    }

    return result;
  }
}
