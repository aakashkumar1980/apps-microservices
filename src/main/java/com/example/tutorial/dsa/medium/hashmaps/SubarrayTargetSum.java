package com.example.tutorial.dsa.medium.hashmaps;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SubarrayTargetSum
 * ----------------------------------
 * <p>This program counts subarrays with sum equal to k using prefix sum + HashMap.
 * The core problem solved here is Subarray Sum Equals K (LeetCode #560).
 *
 * <p><b>Problem Statement:</b>
 * Given an array of integers nums and an integer k, return the total number of
 * continuous subarrays whose sum equals to k.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Find consecutive transactions that total a specific amount</li>
 *   <li>Identify spending patterns matching budget thresholds</li>
 *   <li>Count time windows where reward points hit target</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: nums = [1,1,1], k = 2 → Output: 2 ([1,1] at index 0-1 and 1-2)</li>
 *   <li>Input: nums = [1,2,3], k = 3 → Output: 2 ([1,2] and [3])</li>
 *   <li>Input: nums = [1,-1,0], k = 0 → Output: 3</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Facebook, Amazon, Google, Microsoft
 *
 * @see <a href="https://leetcode.com/problems/subarray-sum-equals-k/">LeetCode 560 - Subarray Sum Equals K</a>
 */
@Component
public class SubarrayTargetSum implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(SubarrayTargetSum.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== SubarrayTargetSum: Prefix Sum + HashMap Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate with transaction amounts
    System.out.println("--- Finding Transaction Sequences with Target Sum ---\n");
    int[] transactions = {100, 50, -50, 100, 50, -100, 50};
    int targetSum = 100;
    System.out.println("Transactions: " + Arrays.toString(transactions));
    System.out.println("Target sum: " + targetSum);
    int count = subarraySum(transactions, targetSum);
    System.out.println("Number of subarrays with sum " + targetSum + ": " + count + "\n");

    // Test cases
    System.out.println("--- Additional Examples ---\n");
    int[][] testArrays = {
        {1, 1, 1},
        {1, 2, 3},
        {1, -1, 0},
        {3, 4, 7, 2, -3, 1, 4, 2}
    };
    int[] targets = {2, 3, 0, 7};

    for (int i = 0; i < testArrays.length; i++) {
      int[] nums = testArrays[i];
      int k = targets[i];
      int result = subarraySum(nums, k);
      System.out.println("Array: " + Arrays.toString(nums) + ", k = " + k);
      System.out.println("Count of subarrays with sum k: " + result + "\n");
    }
  }

  /**
   * Counts subarrays with sum equal to k using prefix sum + HashMap technique.
   *
   * <p><b>LOGIC (Prefix Sum + HashMap):</b>
   * <ol>
   *   <li>Maintain running prefix sum as we iterate</li>
   *   <li>If prefixSum - k exists in map, those positions form valid subarrays ending here</li>
   *   <li>Store frequency of each prefix sum in HashMap</li>
   *   <li>Key insight: sum(i,j) = prefixSum[j] - prefixSum[i-1]</li>
   *   <li>If prefixSum[j] - k = prefixSum[i-1], then sum(i,j) = k</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * nums = [1, 1, 1], k = 2
   * map = {0: 1}, prefixSum = 0, count = 0
   *
   * i=0: prefixSum = 0+1 = 1
   *      need: 1-2 = -1, not in map
   *      map = {0:1, 1:1}, count = 0
   *
   * i=1: prefixSum = 1+1 = 2
   *      need: 2-2 = 0, IS in map with count 1!
   *      map = {0:1, 1:1, 2:1}, count = 1
   *
   * i=2: prefixSum = 2+1 = 3
   *      need: 3-2 = 1, IS in map with count 1!
   *      map = {0:1, 1:1, 2:1, 3:1}, count = 2
   *
   * Result: 2
   * </pre>
   *
   * <p><b>Why initialize map with {0: 1}?</b>
   * <br>This handles the case when a subarray starting from index 0 sums to k.
   * <br>If prefixSum at some point equals k, we need prefixSum - k = 0 to be found.
   *
   * <p><b>Time Complexity: O(n)</b>
   * <br>Single pass through array with O(1) HashMap operations.
   * <br><i>Like tallying a cash register receipt - you go line by line,
   * keeping a running total and checking if any segment matches your target.
   * With n items, you check n times.</i>
   *
   * <p><b>Space Complexity: O(n)</b>
   * <br>HashMap may store up to n different prefix sums.
   * <br><i>Like keeping a ledger of all running totals seen - in worst case,
   * every position has a unique total, so n entries.</i>
   *
   * @param nums the array of integers
   * @param k the target sum
   * @return count of subarrays with sum equal to k
   */
  public static int subarraySum(int[] nums, int k) {
    // Edge case.
    if (nums == null || nums.length == 0) {
      return 0;
    }

    // HashMap: prefixSum -> frequency.
    // Initialize with 0:1 to handle subarrays starting from index 0.
    Map<Integer, Integer> prefixSumCount = new HashMap<>();
    prefixSumCount.put(0, 1);

    int prefixSum = 0;
    int count = 0;

    for (int num : nums) {
      // Update running prefix sum.
      prefixSum += num;

      // If (prefixSum - k) exists, those subarrays end here with sum k.
      // Because: currentPrefixSum - previousPrefixSum = k.
      if (prefixSumCount.containsKey(prefixSum - k)) {
        count += prefixSumCount.get(prefixSum - k);
      }

      // Record this prefix sum.
      prefixSumCount.put(prefixSum, prefixSumCount.getOrDefault(prefixSum, 0) + 1);
    }

    return count;
  }
}
