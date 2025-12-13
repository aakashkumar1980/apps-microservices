package com.example.tutorial.dsa.basics.arrays;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * MissingNumberFinder
 * ----------------------------------
 * <p>This program finds the missing number in a sequence using XOR or math.
 * The core problem solved here is Missing Number (LeetCode #268).
 *
 * <p><b>Problem Statement:</b>
 * Given an array nums containing n distinct numbers in the range [0, n],
 * return the only number in the range that is missing from the array.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Detect missing sequence numbers in batch processing</li>
 *   <li>Find gaps in offer ID sequences</li>
 *   <li>Validate completeness of data imports</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: [3,0,1] → Output: 2 (range is 0-3, missing 2)</li>
 *   <li>Input: [0,1] → Output: 2 (range is 0-2, missing 2)</li>
 *   <li>Input: [9,6,4,2,3,5,7,0,1] → Output: 8</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Microsoft, Facebook
 *
 * @see <a href="https://leetcode.com/problems/missing-number/">LeetCode 268 - Missing Number</a>
 */
@Component
public class MissingNumberFinder implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(MissingNumberFinder.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== MissingNumberFinder: Find Missing Number Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate finding missing sequence number
    System.out.println("--- Finding Missing Batch Sequence ---\n");
    int[] batchNumbers = {0, 1, 2, 4, 5};  // Missing batch #3
    System.out.println("Batch numbers received: " + Arrays.toString(batchNumbers));
    System.out.println("Expected batches: 0 to " + batchNumbers.length);
    int missingBatch = missingNumber(batchNumbers);
    System.out.println("Missing batch number: " + missingBatch + "\n");

    // Test cases
    System.out.println("--- Additional Examples ---\n");
    int[][] testCases = {
        {3, 0, 1},
        {0, 1},
        {9, 6, 4, 2, 3, 5, 7, 0, 1},
        {0}
    };

    for (int[] nums : testCases) {
      System.out.println("Array: " + Arrays.toString(nums));
      System.out.println("Range: [0, " + nums.length + "]");
      System.out.println("Missing (XOR): " + missingNumber(nums));
      System.out.println("Missing (Sum): " + missingNumberSum(nums) + "\n");
    }
  }

  /**
   * Finds missing number using XOR trick (bit manipulation).
   *
   * <p><b>LOGIC (XOR Properties):</b>
   * <ol>
   *   <li>XOR of a number with itself is 0: a ^ a = 0</li>
   *   <li>XOR of a number with 0 is itself: a ^ 0 = a</li>
   *   <li>XOR is commutative and associative: order doesn't matter</li>
   *   <li>If we XOR all indices (0 to n) with all array elements,
   *       pairs will cancel out, leaving only the missing number!</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * nums = [3, 0, 1], n = 3
   *
   * XOR all indices 0 to n:  0 ^ 1 ^ 2 ^ 3 = some value X
   * XOR all array elements:  3 ^ 0 ^ 1 = some value Y
   *
   * Combined: (0 ^ 1 ^ 2 ^ 3) ^ (3 ^ 0 ^ 1)
   *         = (0 ^ 0) ^ (1 ^ 1) ^ (3 ^ 3) ^ 2
   *         = 0 ^ 0 ^ 0 ^ 2
   *         = 2
   *
   * Missing number is 2!
   * </pre>
   *
   * <p><b>Time Complexity: O(n)</b>
   * <br>Single pass through the array.
   * <br><i>Like checking attendance by calling roll numbers - you go through
   * the list once, and XOR magic finds who's missing.</i>
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>Only using one variable for XOR result.
   * <br><i>Like keeping a mental tally - no paper needed, just remember one number.</i>
   *
   * @param nums array containing n distinct numbers in range [0, n]
   * @return the missing number
   */
  public static int missingNumber(int[] nums) {
    int n = nums.length;
    int xor = n;  // Start with n (the largest index not in array).

    // XOR each index with its value.
    // All paired numbers cancel out, leaving only the missing one.
    for (int i = 0; i < n; i++) {
      xor ^= i ^ nums[i];
    }

    return xor;
  }

  /**
   * Finds missing number using mathematical approach (sum formula).
   *
   * <p><b>LOGIC (Gauss Formula):</b>
   * <ol>
   *   <li>Expected sum of 0 to n = n * (n + 1) / 2</li>
   *   <li>Calculate actual sum of array elements</li>
   *   <li>Missing number = Expected sum - Actual sum</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * nums = [3, 0, 1], n = 3
   *
   * Expected sum: 3 * 4 / 2 = 6 (sum of 0+1+2+3)
   * Actual sum: 3 + 0 + 1 = 4
   * Missing: 6 - 4 = 2
   * </pre>
   *
   * <p><b>Time Complexity: O(n)</b>
   * <br>Single pass to sum array elements.
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>Only using sum variables.
   *
   * <p><b>Note:</b> This approach may overflow for very large arrays.
   * XOR method is safer for large inputs.
   *
   * @param nums array containing n distinct numbers in range [0, n]
   * @return the missing number
   */
  public static int missingNumberSum(int[] nums) {
    int n = nums.length;

    // Expected sum using Gauss formula: sum of 0 to n.
    int expectedSum = n * (n + 1) / 2;

    // Actual sum of array elements.
    int actualSum = 0;
    for (int num : nums) {
      actualSum += num;
    }

    // The difference is our missing number.
    return expectedSum - actualSum;
  }
}
