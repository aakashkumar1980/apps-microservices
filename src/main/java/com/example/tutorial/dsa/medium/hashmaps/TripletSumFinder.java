package com.example.tutorial.dsa.medium.hashmaps;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TripletSumFinder
 * ----------------------------------
 * <p>This program finds all unique triplets that sum to zero using sorting + two-pointer.
 * The core problem solved here is 3Sum (LeetCode #15).
 *
 * <p><b>Problem Statement:</b>
 * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]]
 * such that i != j, i != k, j != k, and nums[i] + nums[j] + nums[k] == 0.
 * The solution must not contain duplicate triplets.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Find three transactions that balance to zero (refunds, charges, adjustments)</li>
 *   <li>Identify offer combinations with net-zero cost impact</li>
 *   <li>Reconcile multi-party transactions</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: [-1,0,1,2,-1,-4] → Output: [[-1,-1,2],[-1,0,1]]</li>
 *   <li>Input: [0,1,1] → Output: [] (no triplets sum to 0)</li>
 *   <li>Input: [0,0,0] → Output: [[0,0,0]]</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Facebook, Microsoft, Bloomberg
 *
 * @see <a href="https://leetcode.com/problems/3sum/">LeetCode 15 - 3Sum</a>
 */
@Component
public class TripletSumFinder implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(TripletSumFinder.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== TripletSumFinder: 3Sum Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate with transaction adjustments (positive = charge, negative = refund)
    System.out.println("--- Finding Zero-Sum Transaction Triplets ---\n");
    int[] transactions = {-100, 50, -50, 100, -75, 75, 25};
    System.out.println("Transaction adjustments: " + Arrays.toString(transactions));
    List<List<Integer>> triplets = threeSum(transactions);
    System.out.println("Zero-sum triplets: " + triplets + "\n");

    // Test cases
    System.out.println("--- Additional Examples ---\n");
    int[][] testCases = {
        {-1, 0, 1, 2, -1, -4},
        {0, 1, 1},
        {0, 0, 0}
    };

    for (int[] nums : testCases) {
      List<List<Integer>> result = threeSum(nums);
      System.out.println("Array: " + Arrays.toString(nums));
      System.out.println("Triplets: " + result + "\n");
    }
  }

  /**
   * Finds all unique triplets that sum to zero using sorting + two-pointer technique.
   *
   * <p><b>LOGIC (Sort + Two Pointer):</b>
   * <ol>
   *   <li>Sort the array - enables two-pointer technique and easy duplicate skipping</li>
   *   <li>Fix first element (nums[i]), then find pairs that sum to -nums[i]</li>
   *   <li>Use two pointers (left, right) on remaining elements</li>
   *   <li>If sum too small, move left pointer right</li>
   *   <li>If sum too big, move right pointer left</li>
   *   <li>Skip duplicates to avoid duplicate triplets</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * nums = [-1, 0, 1, 2, -1, -4]
   * After sorting: [-4, -1, -1, 0, 1, 2]
   *
   * i=0: fix -4, need pairs summing to 4
   *      left=1 (-1), right=5 (2): sum = -1+2 = 1 < 4, move left
   *      (no pairs found for -4)
   *
   * i=1: fix -1, need pairs summing to 1
   *      left=2 (-1), right=5 (2): sum = -1+2 = 1 = 1, found! [-1,-1,2]
   *      skip duplicates...
   *      left=3 (0), right=4 (1): sum = 0+1 = 1 = 1, found! [-1,0,1]
   *
   * i=2: skip (same as i=1, duplicate)
   *
   * Result: [[-1,-1,2], [-1,0,1]]
   * </pre>
   *
   * <p><b>Time Complexity: O(n²)</b>
   * <br>Sorting is O(n log n), then we have n iterations with two-pointer O(n) each.
   * <br><i>Like organizing a team-building exercise - first arrange everyone by height
   * (sorting), then for each person, find two others from opposite ends who balance.
   * With 100 people, you'd do ~100 × 100 = 10,000 checks worst case.</i>
   *
   * <p><b>Space Complexity: O(1) or O(n)</b>
   * <br>O(1) extra space if we ignore the output list.
   * <br>O(n) if we count the space needed for sorting (depending on algorithm).
   * <br><i>Like using a clipboard to track results - the clipboard size depends
   * on how many valid triplets you find, but you don't need extra workspace.</i>
   *
   * @param nums the array of integers
   * @return list of all unique triplets that sum to zero
   */
  public static List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();

    // Edge case: need at least 3 elements.
    if (nums == null || nums.length < 3) {
      return result;
    }

    // Sort to enable two-pointer technique and duplicate handling.
    Arrays.sort(nums);

    // Fix first element, then use two-pointer for remaining.
    for (int i = 0; i < nums.length - 2; i++) {
      // Skip duplicates for first element.
      if (i > 0 && nums[i] == nums[i - 1]) {
        continue;
      }

      // Early termination: if smallest is positive, no solution.
      if (nums[i] > 0) {
        break;
      }

      // Two pointers from both ends of remaining array.
      int left = i + 1;
      int right = nums.length - 1;
      int target = -nums[i];  // We need left + right = -nums[i].

      while (left < right) {
        int sum = nums[left] + nums[right];

        if (sum == target) {
          // Found a triplet!
          result.add(Arrays.asList(nums[i], nums[left], nums[right]));

          // Skip duplicates.
          while (left < right && nums[left] == nums[left + 1]) left++;
          while (left < right && nums[right] == nums[right - 1]) right--;

          // Move both pointers.
          left++;
          right--;
        } else if (sum < target) {
          // Sum too small, need larger numbers.
          left++;
        } else {
          // Sum too big, need smaller numbers.
          right--;
        }
      }
    }

    return result;
  }
}
