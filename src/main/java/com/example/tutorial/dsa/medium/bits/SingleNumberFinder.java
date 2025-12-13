package com.example.tutorial.dsa.medium.bits;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * SingleNumberFinder
 * ----------------------------------
 * <p>This program finds numbers that appear only once using XOR.
 * The core problem solved here is Single Number (LeetCode #136).
 *
 * <p><b>Problem Statement:</b>
 * Given a non-empty array of integers where every element appears twice
 * except for one, find that single one in O(n) time and O(1) space.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Find unique transaction IDs in duplicate logs</li>
 *   <li>Identify orphan records in paired data</li>
 *   <li>Data integrity checks</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Google, Facebook, Apple
 *
 * @see <a href="https://leetcode.com/problems/single-number/">LeetCode 136 - Single Number</a>
 */
@Component
public class SingleNumberFinder implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(SingleNumberFinder.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== SingleNumberFinder: XOR Magic Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demo 1: Basic Single Number
    System.out.println("--- Demo 1: Find Single Number (LeetCode #136) ---\n");
    int[] nums1 = {4, 1, 2, 1, 2};
    System.out.println("  Array: " + Arrays.toString(nums1));
    System.out.println("  Single number: " + singleNumber(nums1) + "\n");

    int[] nums2 = {2, 2, 1};
    System.out.println("  Array: " + Arrays.toString(nums2));
    System.out.println("  Single number: " + singleNumber(nums2) + "\n");

    // Demo 2: XOR Properties
    System.out.println("--- Demo 2: XOR Properties ---\n");
    demoXorProperties();

    // Demo 3: Single Number II (each appears 3 times)
    System.out.println("\n--- Demo 3: Single Number II (appears once, others 3x) ---\n");
    int[] nums3 = {2, 2, 3, 2};
    System.out.println("  Array: " + Arrays.toString(nums3));
    System.out.println("  Single number: " + singleNumberII(nums3) + "\n");

    // Demo 4: Single Number III (two single numbers)
    System.out.println("--- Demo 4: Single Number III (two singles) ---\n");
    int[] nums4 = {1, 2, 1, 3, 2, 5};
    System.out.println("  Array: " + Arrays.toString(nums4));
    int[] result = singleNumberIII(nums4);
    System.out.println("  Two single numbers: " + Arrays.toString(result));
  }

  /**
   * Finds the single number using XOR.
   *
   * <p><b>LOGIC (XOR Magic):</b>
   * <ol>
   *   <li>XOR properties: a ^ a = 0, a ^ 0 = a, XOR is commutative</li>
   *   <li>XOR all numbers together</li>
   *   <li>Pairs cancel out (a ^ a = 0)</li>
   *   <li>Only the single number remains</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * Array: [4, 1, 2, 1, 2]
   *
   * XOR all: 4 ^ 1 ^ 2 ^ 1 ^ 2
   *        = 4 ^ (1 ^ 1) ^ (2 ^ 2)
   *        = 4 ^ 0 ^ 0
   *        = 4
   * </pre>
   *
   * <p><b>Time Complexity: O(n)</b>
   * <br>Single pass through array.
   * <br><i>Like having pairs of socks - when you match them all,
   * the one left over is the single one.</i>
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>Only one variable for XOR result.
   *
   * @param nums array where every element appears twice except one
   * @return the single number
   */
  public static int singleNumber(int[] nums) {
    int result = 0;
    for (int num : nums) {
      result ^= num;
    }
    return result;
  }

  /**
   * Demonstrates XOR properties.
   */
  private void demoXorProperties() {
    System.out.println("  XOR Properties:");
    System.out.println("    a ^ a = 0 : 5 ^ 5 = " + (5 ^ 5));
    System.out.println("    a ^ 0 = a : 5 ^ 0 = " + (5 ^ 0));
    System.out.println("    a ^ b = b ^ a : 3 ^ 5 = " + (3 ^ 5) + ", 5 ^ 3 = " + (5 ^ 3));
    System.out.println("    (a ^ b) ^ c = a ^ (b ^ c) : " + ((1 ^ 2) ^ 3) + " = " + (1 ^ (2 ^ 3)));
  }

  /**
   * Finds single number when others appear 3 times.
   *
   * <p><b>LOGIC (Bit Counting):</b>
   * For each bit position, count how many numbers have that bit set.
   * If count % 3 != 0, the single number has that bit set.
   *
   * <p><b>Time Complexity: O(32n) = O(n)</b>
   * <p><b>Space Complexity: O(1)</b>
   */
  public static int singleNumberII(int[] nums) {
    int result = 0;

    for (int i = 0; i < 32; i++) {
      int bitCount = 0;
      for (int num : nums) {
        bitCount += (num >> i) & 1;
      }
      if (bitCount % 3 != 0) {
        result |= (1 << i);
      }
    }

    return result;
  }

  /**
   * Finds two single numbers when others appear twice.
   *
   * <p><b>LOGIC (XOR + Bit Separation):</b>
   * <ol>
   *   <li>XOR all numbers → get a ^ b (the two singles XOR'd)</li>
   *   <li>Find any set bit in a ^ b (where a and b differ)</li>
   *   <li>Use this bit to divide numbers into two groups</li>
   *   <li>XOR each group separately to get a and b</li>
   * </ol>
   *
   * <p><b>Time Complexity: O(n)</b>
   * <p><b>Space Complexity: O(1)</b>
   */
  public static int[] singleNumberIII(int[] nums) {
    // Step 1: XOR all numbers to get a ^ b.
    int xor = 0;
    for (int num : nums) {
      xor ^= num;
    }

    // Step 2: Find rightmost set bit (where a and b differ).
    int rightmostBit = xor & (-xor);

    // Step 3: Separate into two groups and XOR each.
    int a = 0, b = 0;
    for (int num : nums) {
      if ((num & rightmostBit) == 0) {
        a ^= num;
      } else {
        b ^= num;
      }
    }

    return new int[] {a, b};
  }
}
