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
 * DataPairReconciler
 * ----------------------------------
 * <p>This program finds two numbers that add up to a target using HashMap.
 * The core problem solved here is Two Sum (LeetCode #1).
 *
 * <p><b>Problem Statement:</b>
 * Given an array of integers nums and an integer target, return indices of the
 * two numbers such that they add up to target. Each input has exactly one solution.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Find two offers whose combined discount equals a target amount</li>
 *   <li>Reconcile pairs of transactions that net to a specific value</li>
 *   <li>Match complementary reward point redemptions</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: nums = [2,7,11,15], target = 9 → Output: [0,1] (2+7=9)</li>
 *   <li>Input: nums = [3,2,4], target = 6 → Output: [1,2] (2+4=6)</li>
 *   <li>Input: nums = [3,3], target = 6 → Output: [0,1] (3+3=6)</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Google, Facebook, Apple, Microsoft (⭐ Most Asked!)
 *
 * @see <a href="https://leetcode.com/problems/two-sum/">LeetCode 1 - Two Sum</a>
 */
@Component
public class DataPairReconciler implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataPairReconciler.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== DataPairReconciler: Two Sum Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate finding pair that sums to target
    System.out.println("--- Finding Transaction Pair for Target Sum ---\n");
    int[] transactions = {150, 200, 350, 400, 250};
    int targetSum = 600;
    System.out.println("Transactions: " + Arrays.toString(transactions));
    System.out.println("Target sum: " + targetSum);
    int[] result = twoSum(transactions, targetSum);
    if (result.length > 0) {
      System.out.println("Indices: " + Arrays.toString(result));
      System.out.println("Values: " + transactions[result[0]] + " + " +
          transactions[result[1]] + " = " + targetSum + "\n");
    }

    // Test cases
    System.out.println("--- Additional Examples ---\n");
    int[][] testArrays = {
        {2, 7, 11, 15},
        {3, 2, 4},
        {3, 3}
    };
    int[] targets = {9, 6, 6};

    for (int i = 0; i < testArrays.length; i++) {
      int[] nums = testArrays[i];
      int target = targets[i];
      int[] indices = twoSum(nums, target);
      System.out.println("Array: " + Arrays.toString(nums) + ", Target: " + target);
      System.out.println("Result: " + Arrays.toString(indices));
      if (indices.length == 2) {
        System.out.println("Sum: " + nums[indices[0]] + " + " + nums[indices[1]] +
            " = " + target + "\n");
      }
    }
  }

  /**
   * Finds two indices whose values sum to target using HashMap for O(1) lookup.
   *
   * <p><b>LOGIC (HashMap Complement Search):</b>
   * <ol>
   *   <li>For each number, we need its "complement" (target - current number)</li>
   *   <li>Use HashMap to store {value → index} as we iterate</li>
   *   <li>For each num, check if complement exists in map</li>
   *   <li>If yes, we found our pair! Return both indices</li>
   *   <li>If no, add current {num → index} to map for future lookups</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * nums = [2, 7, 11, 15], target = 9
   * map = {}
   *
   * i=0: num=2, complement=9-2=7, 7 not in map
   *      → add {2: 0}, map = {2: 0}
   *
   * i=1: num=7, complement=9-7=2, 2 IS in map at index 0!
   *      → return [0, 1]
   *
   * Result: [0, 1] because nums[0] + nums[1] = 2 + 7 = 9
   * </pre>
   *
   * <p><b>Time Complexity: O(n)</b>
   * <br>Single pass through the array, HashMap operations are O(1).
   * <br><i>Like looking for a dance partner at a party - you check your list
   * of "looking for partner" people (HashMap) for each new person.
   * If their complement is on the list, you found a match!</i>
   *
   * <p><b>Space Complexity: O(n)</b>
   * <br>HashMap may store up to n-1 elements.
   * <br><i>Like keeping a guest registry - worst case everyone signs in
   * before finding a match. The registry grows with number of guests.</i>
   *
   * @param nums the array of integers
   * @param target the target sum
   * @return array of two indices, or empty array if no solution
   */
  public static int[] twoSum(int[] nums, int target) {
    // HashMap: value -> index for O(1) complement lookup.
    Map<Integer, Integer> numToIndex = new HashMap<>();

    for (int i = 0; i < nums.length; i++) {
      // What number do we need to pair with nums[i]?
      int complement = target - nums[i];

      // If complement exists, we found our answer!
      if (numToIndex.containsKey(complement)) {
        return new int[] { numToIndex.get(complement), i };
      }

      // Otherwise, remember this number for future lookups.
      numToIndex.put(nums[i], i);
    }

    // No solution found (shouldn't happen per problem constraints).
    return new int[] {};
  }
}
