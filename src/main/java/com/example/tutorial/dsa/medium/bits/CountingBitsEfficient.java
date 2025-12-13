package com.example.tutorial.dsa.medium.bits;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * CountingBitsEfficient
 * ----------------------------------
 * <p>This program counts set bits (1s) in binary representation.
 * The core problem solved here is Counting Bits (LeetCode #338).
 *
 * <p><b>Problem Statement:</b>
 * Given an integer n, return an array ans of length n + 1 such that
 * ans[i] is the number of 1's in the binary representation of i.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Feature flag combinations analysis</li>
 *   <li>Permission bitmask calculations</li>
 *   <li>Subset selection counting</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Apple, Facebook, Google
 *
 * @see <a href="https://leetcode.com/problems/counting-bits/">LeetCode 338 - Counting Bits</a>
 */
@Component
public class CountingBitsEfficient implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(CountingBitsEfficient.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== CountingBitsEfficient: Counting 1-Bits Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demo 1: Count bits for 0 to n
    System.out.println("--- Demo 1: Count Bits 0 to n (LeetCode #338) ---\n");
    int n = 10;
    int[] result = countBits(n);
    System.out.println("  Bits counts for 0 to " + n + ":");
    for (int i = 0; i <= n; i++) {
      System.out.printf("    %2d = %s → %d ones%n",
          i, padBinary(i, 4), result[i]);
    }

    // Demo 2: Different approaches
    System.out.println("\n--- Demo 2: Different Counting Approaches ---\n");
    int testNum = 13;  // 1101 in binary
    System.out.println("  Number: " + testNum + " (" + Integer.toBinaryString(testNum) + ")");
    System.out.println("  Brian Kernighan's: " + countSetBitsKernighan(testNum));
    System.out.println("  Lookup table: " + countSetBitsLookup(testNum));
    System.out.println("  Built-in: " + Integer.bitCount(testNum));

    // Demo 3: Hamming Weight
    System.out.println("\n--- Demo 3: Hamming Weight (LeetCode #191) ---\n");
    int[] testNums = {11, 128, 255, -3};
    for (int num : testNums) {
      System.out.printf("  %d (%s) → %d ones%n",
          num, Integer.toBinaryString(num), hammingWeight(num));
    }
  }

  /**
   * Counts bits for 0 to n using dynamic programming.
   *
   * <p><b>LOGIC (DP with Bit Pattern):</b>
   * <ol>
   *   <li>ans[i] = ans[i >> 1] + (i & 1)</li>
   *   <li>i >> 1: Right shift removes last bit (ans already computed)</li>
   *   <li>i & 1: Adds 1 if last bit is set</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * i=0: 0 → ans[0]=0
   * i=1: 1 → ans[0] + 1 = 1
   * i=2: 10 → ans[1] + 0 = 1
   * i=3: 11 → ans[1] + 1 = 2
   * i=4: 100 → ans[2] + 0 = 1
   * i=5: 101 → ans[2] + 1 = 2
   * </pre>
   *
   * <p><b>Time Complexity: O(n)</b>
   * <br>Single pass, constant time per number.
   * <br><i>Like building a family tree - each number's bit count
   * is its parent's (i >> 1) plus whether it's odd.</i>
   *
   * <p><b>Space Complexity: O(n)</b>
   * <br>Output array.
   *
   * @param n the upper limit
   * @return array of bit counts for 0 to n
   */
  public static int[] countBits(int n) {
    int[] ans = new int[n + 1];

    for (int i = 1; i <= n; i++) {
      // ans[i >> 1] already computed, add 1 if odd.
      ans[i] = ans[i >> 1] + (i & 1);
    }

    return ans;
  }

  /**
   * Brian Kernighan's algorithm - count by clearing lowest set bit.
   *
   * <p><b>LOGIC:</b> n & (n-1) clears the rightmost set bit.
   * Count how many times we can do this until n becomes 0.
   *
   * <p><b>Time Complexity: O(k)</b> where k = number of set bits.
   */
  public static int countSetBitsKernighan(int n) {
    int count = 0;
    while (n != 0) {
      n = n & (n - 1);  // Clear lowest set bit.
      count++;
    }
    return count;
  }

  /**
   * Lookup table approach - precompute for bytes.
   *
   * <p><b>LOGIC:</b> Precompute bit counts for 0-255.
   * Split 32-bit integer into 4 bytes and sum lookups.
   */
  private static final int[] BYTE_BIT_COUNT = new int[256];

  static {
    for (int i = 0; i < 256; i++) {
      BYTE_BIT_COUNT[i] = BYTE_BIT_COUNT[i >> 1] + (i & 1);
    }
  }

  public static int countSetBitsLookup(int n) {
    return BYTE_BIT_COUNT[n & 0xff]
        + BYTE_BIT_COUNT[(n >> 8) & 0xff]
        + BYTE_BIT_COUNT[(n >> 16) & 0xff]
        + BYTE_BIT_COUNT[(n >> 24) & 0xff];
  }

  /**
   * Hamming Weight - count set bits (LeetCode #191).
   *
   * <p><b>Note:</b> Handles negative numbers correctly by treating as unsigned.
   */
  public static int hammingWeight(int n) {
    int count = 0;
    while (n != 0) {
      count += (n & 1);
      n = n >>> 1;  // Unsigned right shift (important for negatives).
    }
    return count;
  }

  private static String padBinary(int n, int width) {
    String binary = Integer.toBinaryString(n);
    while (binary.length() < width) {
      binary = "0" + binary;
    }
    return binary;
  }
}
