package com.example.tutorial.dsa.basics.math;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SymmetricDataValidator
 * ----------------------------------
 * <p>This program demonstrates palindrome checking for integers.
 * The core problem solved here is determining if a number is a palindrome (LeetCode #9).
 *
 * <p><b>Problem Statement:</b>
 * Given an integer x, return true if x is a palindrome, and false otherwise.
 * A palindrome reads the same forwards and backwards.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Transaction ID validation - check if IDs follow symmetric patterns</li>
 *   <li>Data integrity checks - validate checksums with palindrome properties</li>
 *   <li>Special offer codes - identify "special" symmetric offer IDs</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: 121 → Output: true</li>
 *   <li>Input: -121 → Output: false (reads as 121- backwards)</li>
 *   <li>Input: 10 → Output: false</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Facebook, Bloomberg
 *
 * @see <a href="https://leetcode.com/problems/palindrome-number/">LeetCode 9 - Palindrome Number</a>
 */
@Component
public class SymmetricDataValidator implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(SymmetricDataValidator.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== SymmetricDataValidator: Palindrome Check Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate palindrome checking
    System.out.println("--- Checking Symmetric Transaction IDs ---\n");
    int[] testCases = {121, -121, 12321, 10, 1234321, 123, 0, 1};

    for (int num : testCases) {
      boolean isPalin = isPalindrome(num);
      String status = isPalin ? "✓ Palindrome" : "✗ Not palindrome";
      System.out.printf("%8d → %s%n", num, status);
    }
  }

  /**
   * Checks if an integer is a palindrome without converting to string.
   *
   * <p><b>LOGIC:</b>
   * <ol>
   *   <li>Negative numbers are not palindromes</li>
   *   <li>Reverse only the second half of the number</li>
   *   <li>Compare first half with reversed second half</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * Input: x = 12321
   *
   * Step 1: x = 12321, reversed = 0
   * Step 2: x = 1232,  reversed = 1  (extracted 1)
   * Step 3: x = 123,   reversed = 12 (extracted 2)
   *
   * Now x (123) <= reversed (12) is false, continue...
   * Step 4: x = 12,    reversed = 123 (extracted 3)
   *
   * Now x (12) <= reversed (123), stop!
   * Check: x == reversed/10? → 12 == 12? → true (odd length, ignore middle digit)
   * </pre>
   *
   * <p><b>Time Complexity: O(log n)</b>
   * <br>We process half the digits.
   * <br><i>Like checking if a word is a palindrome from both ends.</i>
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>We only use a few variables.
   * <br><i>No extra storage needed - just comparing numbers.</i>
   *
   * @param x the integer to check
   * @return true if x is a palindrome, false otherwise
   */
  public static boolean isPalindrome(int x) {
    // Negative numbers are not palindromes.
    // Numbers ending in 0 (except 0 itself) are not palindromes.
    if (x < 0 || (x % 10 == 0 && x != 0)) {
      return false;
    }

    int reversed = 0;

    // Reverse only half of the number.
    // We stop when the reversed half is >= the remaining half.
    while (x > reversed) {
      // Extract last digit and add to reversed.
      reversed = reversed * 10 + x % 10;
      // Remove last digit from x.
      x = x / 10;
    }

    // For even length: x == reversed (e.g., 1221 → x=12, reversed=12)
    // For odd length: x == reversed/10 (e.g., 12321 → x=12, reversed=123)
    return x == reversed || x == reversed / 10;
  }
}
