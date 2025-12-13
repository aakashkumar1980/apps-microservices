package com.example.tutorial.dsa.medium.bits;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * BitwiseSubsetGenerator
 * ----------------------------------
 * <p>This program generates all subsets using bit manipulation.
 * The core problem solved here is Subsets (LeetCode #78).
 *
 * <p><b>Problem Statement:</b>
 * Given an integer array nums of unique elements, return all possible subsets.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Generate all feature flag combinations</li>
 *   <li>Find all possible offer bundles</li>
 *   <li>Permission set combinations</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Facebook, Google, Microsoft
 *
 * @see <a href="https://leetcode.com/problems/subsets/">LeetCode 78 - Subsets</a>
 */
@Component
public class BitwiseSubsetGenerator implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(BitwiseSubsetGenerator.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== BitwiseSubsetGenerator: Subset Generation Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demo 1: Generate all subsets
    System.out.println("--- Demo 1: Generate All Subsets (LeetCode #78) ---\n");
    int[] nums = {1, 2, 3};
    System.out.println("  Input: " + Arrays.toString(nums));
    System.out.println("  All subsets:");
    List<List<Integer>> subsets = subsets(nums);
    for (int i = 0; i < subsets.size(); i++) {
      String binary = padBinary(i, nums.length);
      System.out.println("    " + binary + " → " + subsets.get(i));
    }

    // Demo 2: Subsets of offer types
    System.out.println("\n--- Demo 2: Offer Type Combinations ---\n");
    String[] offerTypes = {"Cashback", "Points", "Discount"};
    System.out.println("  Offer types: " + Arrays.toString(offerTypes));
    System.out.println("  All combinations:");
    List<List<String>> offerCombos = subsetsOfStrings(offerTypes);
    for (List<String> combo : offerCombos) {
      System.out.println("    " + (combo.isEmpty() ? "(none)" : combo));
    }

    // Demo 3: Subsets with sum constraint
    System.out.println("\n--- Demo 3: Subsets with Target Sum ---\n");
    int[] values = {1, 2, 3, 4, 5};
    int target = 7;
    System.out.println("  Values: " + Arrays.toString(values));
    System.out.println("  Subsets summing to " + target + ":");
    List<List<Integer>> sumSubsets = subsetsWithSum(values, target);
    for (List<Integer> subset : sumSubsets) {
      int sum = subset.stream().mapToInt(Integer::intValue).sum();
      System.out.println("    " + subset + " (sum=" + sum + ")");
    }

    // Demo 4: Bit mask iteration
    System.out.println("\n--- Demo 4: Iterating Submasks ---\n");
    demoSubmaskIteration();
  }

  /**
   * Generates all subsets using bit manipulation.
   *
   * <p><b>LOGIC (Bit Mask Enumeration):</b>
   * <ol>
   *   <li>For n elements, there are 2^n subsets</li>
   *   <li>Each number from 0 to 2^n - 1 represents a subset</li>
   *   <li>If bit i is set, element i is in the subset</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * nums = [1, 2, 3], n = 3, 2^n = 8 subsets
   *
   * mask=0 (000): []           - no bits set
   * mask=1 (001): [1]          - bit 0 set
   * mask=2 (010): [2]          - bit 1 set
   * mask=3 (011): [1, 2]       - bits 0,1 set
   * mask=4 (100): [3]          - bit 2 set
   * mask=5 (101): [1, 3]       - bits 0,2 set
   * mask=6 (110): [2, 3]       - bits 1,2 set
   * mask=7 (111): [1, 2, 3]    - all bits set
   * </pre>
   *
   * <p><b>Time Complexity: O(n × 2^n)</b>
   * <br>2^n subsets, O(n) to build each.
   * <br><i>Like a light switch panel - each combination of on/off
   * represents a different subset.</i>
   *
   * <p><b>Space Complexity: O(n × 2^n)</b>
   * <br>Storing all subsets.
   *
   * @param nums array of unique integers
   * @return list of all subsets
   */
  public static List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    int n = nums.length;
    int totalSubsets = 1 << n;  // 2^n

    for (int mask = 0; mask < totalSubsets; mask++) {
      List<Integer> subset = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        if ((mask & (1 << i)) != 0) {
          subset.add(nums[i]);
        }
      }
      result.add(subset);
    }

    return result;
  }

  /**
   * Generates all subsets of strings.
   */
  public static List<List<String>> subsetsOfStrings(String[] items) {
    List<List<String>> result = new ArrayList<>();
    int n = items.length;
    int totalSubsets = 1 << n;

    for (int mask = 0; mask < totalSubsets; mask++) {
      List<String> subset = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        if ((mask & (1 << i)) != 0) {
          subset.add(items[i]);
        }
      }
      result.add(subset);
    }

    return result;
  }

  /**
   * Finds all subsets with a specific sum.
   */
  public static List<List<Integer>> subsetsWithSum(int[] nums, int targetSum) {
    List<List<Integer>> result = new ArrayList<>();
    int n = nums.length;
    int totalSubsets = 1 << n;

    for (int mask = 0; mask < totalSubsets; mask++) {
      List<Integer> subset = new ArrayList<>();
      int sum = 0;

      for (int i = 0; i < n; i++) {
        if ((mask & (1 << i)) != 0) {
          subset.add(nums[i]);
          sum += nums[i];
        }
      }

      if (sum == targetSum) {
        result.add(subset);
      }
    }

    return result;
  }

  /**
   * Demonstrates iterating over all submasks of a mask.
   */
  private void demoSubmaskIteration() {
    int mask = 5;  // 101 in binary
    System.out.println("  Full mask: " + mask + " (" + Integer.toBinaryString(mask) + ")");
    System.out.println("  All submasks:");

    // Iterate submasks: start from mask, go to 0
    for (int sub = mask; sub > 0; sub = (sub - 1) & mask) {
      System.out.println("    " + sub + " (" + Integer.toBinaryString(sub) + ")");
    }
    System.out.println("    0 (0)");
  }

  private static String padBinary(int n, int width) {
    String binary = Integer.toBinaryString(n);
    while (binary.length() < width) {
      binary = "0" + binary;
    }
    return binary;
  }
}
