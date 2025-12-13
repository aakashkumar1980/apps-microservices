package com.example.tutorial.dsa.medium.bits;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * PowerOfTwoChecker
 * ----------------------------------
 * <p>This program checks if a number is a power of two using bit manipulation.
 * The core problem solved here is Power of Two (LeetCode #231).
 *
 * <p><b>Problem Statement:</b>
 * Given an integer n, return true if it is a power of two. Otherwise, return false.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Buffer size validation for data structures</li>
 *   <li>Memory allocation checks</li>
 *   <li>Binary partitioning schemes</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Apple, Google
 *
 * @see <a href="https://leetcode.com/problems/power-of-two/">LeetCode 231 - Power of Two</a>
 */
@Component
public class PowerOfTwoChecker implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(PowerOfTwoChecker.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== PowerOfTwoChecker: Power of Two Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demo 1: Power of Two
    System.out.println("--- Demo 1: Power of Two (LeetCode #231) ---\n");
    int[] testNums = {1, 2, 4, 8, 16, 3, 5, 6, 7, 0, -2147483648};
    for (int n : testNums) {
      String binary = n >= 0 ? padBinary(n, 8) : Integer.toBinaryString(n);
      System.out.printf("  %11d (%s) → %s%n",
          n, binary, isPowerOfTwo(n) ? "Power of 2" : "Not power of 2");
    }

    // Demo 2: Power of Four
    System.out.println("\n--- Demo 2: Power of Four (LeetCode #342) ---\n");
    int[] fourTests = {1, 2, 4, 8, 16, 32, 64, 256};
    for (int n : fourTests) {
      System.out.printf("  %3d (%s) → %s%n",
          n, padBinary(n, 9),
          isPowerOfFour(n) ? "Power of 4" : "Not power of 4");
    }

    // Demo 3: Power of Three
    System.out.println("\n--- Demo 3: Power of Three (LeetCode #326) ---\n");
    int[] threeTests = {1, 3, 9, 27, 81, 12, 45};
    for (int n : threeTests) {
      System.out.printf("  %3d → %s%n",
          n, isPowerOfThree(n) ? "Power of 3" : "Not power of 3");
    }

    // Demo 4: Bit Manipulation Tricks
    System.out.println("\n--- Demo 4: Useful Bit Tricks ---\n");
    demoBitTricks();
  }

  /**
   * Checks if n is a power of two.
   *
   * <p><b>LOGIC (n & (n-1) Trick):</b>
   * <ol>
   *   <li>Powers of 2 have exactly one bit set: 1, 10, 100, 1000...</li>
   *   <li>n-1 flips all bits after the set bit: 1000 - 1 = 0111</li>
   *   <li>n & (n-1) = 0 only if there's exactly one bit set</li>
   *   <li>Must also check n > 0 (0 and negatives are not powers of 2)</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * n=8  (1000):  8 & 7  = 1000 & 0111 = 0000 → Power of 2!
   * n=6  (0110):  6 & 5  = 0110 & 0101 = 0100 → Not power of 2
   * </pre>
   *
   * <p><b>Time Complexity: O(1)</b>
   * <br>Single bitwise operation.
   *
   * <p><b>Space Complexity: O(1)</b>
   *
   * @param n the number to check
   * @return true if n is a power of two
   */
  public static boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
  }

  /**
   * Alternative: Check if exactly one bit is set using n & -n.
   *
   * <p><b>LOGIC:</b> n & -n isolates the rightmost set bit.
   * If result equals n, there's only one bit set.
   */
  public static boolean isPowerOfTwoAlt(int n) {
    return n > 0 && (n & -n) == n;
  }

  /**
   * Checks if n is a power of four.
   *
   * <p><b>LOGIC:</b>
   * <ol>
   *   <li>Must be power of 2 (single bit set)</li>
   *   <li>That bit must be at even position (0, 2, 4...)</li>
   *   <li>Use mask 0x55555555 (bits at even positions)</li>
   * </ol>
   */
  public static boolean isPowerOfFour(int n) {
    // 0x55555555 = 01010101... (bits at positions 0,2,4,6...)
    return n > 0 && (n & (n - 1)) == 0 && (n & 0x55555555) != 0;
  }

  /**
   * Checks if n is a power of three.
   *
   * <p><b>LOGIC:</b> No simple bit trick for base-3.
   * Use the fact that 3^19 = 1162261467 is largest power of 3 fitting in int.
   * If n divides 3^19 evenly, n is a power of 3.
   */
  public static boolean isPowerOfThree(int n) {
    // 3^19 = 1162261467 (largest power of 3 in 32-bit int)
    return n > 0 && 1162261467 % n == 0;
  }

  private void demoBitTricks() {
    int n = 12;  // 1100 in binary
    System.out.println("  n = " + n + " (" + Integer.toBinaryString(n) + ")");

    // Rightmost set bit
    int rightmost = n & -n;
    System.out.println("  Rightmost set bit (n & -n): " + rightmost);

    // Clear rightmost set bit
    int cleared = n & (n - 1);
    System.out.println("  Clear rightmost bit (n & (n-1)): " + cleared
        + " (" + Integer.toBinaryString(cleared) + ")");

    // Set rightmost 0 bit
    int setZero = n | (n + 1);
    System.out.println("  Set rightmost 0 (n | (n+1)): " + setZero
        + " (" + Integer.toBinaryString(setZero) + ")");

    // Turn off all bits except rightmost
    System.out.println("  Isolate rightmost 1: " + (n & -n));

    // Check if power of 2
    System.out.println("  Is power of 2: " + (n > 0 && (n & (n - 1)) == 0));
  }

  private static String padBinary(int n, int width) {
    String binary = Integer.toBinaryString(n);
    while (binary.length() < width) {
      binary = "0" + binary;
    }
    return binary;
  }
}
