package com.example.tutorial.dsa.basics.arrays;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * RecentTransactionCompactor
 * ----------------------------------
 * <p>This program moves all zeros to the end while maintaining relative order.
 * The core problem solved here is Move Zeroes (LeetCode #283).
 *
 * <p><b>Problem Statement:</b>
 * Given an integer array nums, move all 0's to the end of it while maintaining
 * the relative order of the non-zero elements. Do it in-place.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Compact transaction data by moving null/void entries to end</li>
 *   <li>Clean up offer lists by pushing inactive offers to end</li>
 *   <li>Reorganize data streams removing empty placeholders</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: [0,1,0,3,12] → Output: [1,3,12,0,0]</li>
 *   <li>Input: [0] → Output: [0]</li>
 *   <li>Input: [1,2,3] → Output: [1,2,3] (no zeros)</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Facebook, Bloomberg, Apple
 *
 * @see <a href="https://leetcode.com/problems/move-zeroes/">LeetCode 283 - Move Zeroes</a>
 */
@Component
public class RecentTransactionCompactor implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(RecentTransactionCompactor.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== RecentTransactionCompactor: Move Zeros Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate with transaction amounts (0 = void/cancelled)
    System.out.println("--- Compacting Transaction Data ---\n");
    int[] transactions = {150, 0, 75, 0, 200, 0, 50};
    System.out.println("Original transactions (0 = void): " + Arrays.toString(transactions));
    moveZeroes(transactions);
    System.out.println("After compacting: " + Arrays.toString(transactions) + "\n");

    // Demonstrate with various test cases
    System.out.println("--- Additional Examples ---\n");
    int[][] testCases = {
        {0, 1, 0, 3, 12},
        {0},
        {1, 2, 3},
        {0, 0, 0, 1},
        {1, 0, 0, 0}
    };

    for (int[] nums : testCases) {
      int[] original = nums.clone();
      moveZeroes(nums);
      System.out.println("Original: " + Arrays.toString(original));
      System.out.println("Result:   " + Arrays.toString(nums) + "\n");
    }
  }

  /**
   * Moves all zeros to end while maintaining order of non-zero elements.
   *
   * <p><b>LOGIC (Two-Pointer Technique):</b>
   * <ol>
   *   <li>Use a "write" pointer (insertPos) to track where next non-zero goes</li>
   *   <li>Use a "read" pointer (i) to scan through array</li>
   *   <li>When we find a non-zero, write it at insertPos and increment</li>
   *   <li>After scanning, fill remaining positions with zeros</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * nums = [0, 1, 0, 3, 12]
   * insertPos = 0
   *
   * i=0: nums[0]=0, skip (it's zero)
   * i=1: nums[1]=1, write at insertPos=0 → [1,1,0,3,12], insertPos=1
   * i=2: nums[2]=0, skip
   * i=3: nums[3]=3, write at insertPos=1 → [1,3,0,3,12], insertPos=2
   * i=4: nums[4]=12, write at insertPos=2 → [1,3,12,3,12], insertPos=3
   *
   * Fill zeros from insertPos=3 to end:
   * → [1,3,12,0,0]
   *
   * Result: [1,3,12,0,0]
   * </pre>
   *
   * <p><b>Time Complexity: O(n)</b>
   * <br>We make one pass to move non-zeros, then fill zeros - total ~2n operations.
   * <br><i>Like sorting a messy desk - you pick up all the important papers first
   * (one pass), then clear the remaining clutter (second pass). Two trips
   * through n items = 2n work, simplified to O(n).</i>
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>We only use the insertPos pointer variable, no extra arrays.
   * <br><i>Like organizing your desk using just your hands - no extra desk needed.
   * Just a mental note (insertPos) of where to place the next item.</i>
   *
   * @param nums the array to compact (modified in-place)
   */
  public static void moveZeroes(int[] nums) {
    // Edge case: nothing to do for empty or single element.
    if (nums == null || nums.length <= 1) {
      return;
    }

    // insertPos tracks where the next non-zero should be placed.
    // Think of it as a "write head" on a tape.
    int insertPos = 0;

    // First pass: move all non-zero elements to the front.
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] != 0) {
        nums[insertPos] = nums[i];
        insertPos++;
      }
    }

    // Second pass: fill the rest with zeros.
    while (insertPos < nums.length) {
      nums[insertPos] = 0;
      insertPos++;
    }
  }

  /**
   * Alternative approach using swap (single pass, more swaps).
   * Useful when you want to minimize writes to original positions.
   *
   * @param nums the array to compact
   */
  public static void moveZeroesSwap(int[] nums) {
    if (nums == null || nums.length <= 1) {
      return;
    }

    int insertPos = 0;

    for (int i = 0; i < nums.length; i++) {
      if (nums[i] != 0) {
        // Swap current non-zero with position at insertPos.
        int temp = nums[insertPos];
        nums[insertPos] = nums[i];
        nums[i] = temp;
        insertPos++;
      }
    }
  }
}
