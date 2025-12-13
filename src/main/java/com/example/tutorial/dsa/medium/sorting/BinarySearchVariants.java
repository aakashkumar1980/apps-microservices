package com.example.tutorial.dsa.medium.sorting;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * BinarySearchVariants
 * ----------------------------------
 * <p>This program demonstrates binary search variations for finding boundaries.
 * The core problem solved here is Find First and Last Position (LeetCode #34).
 *
 * <p><b>Problem Statement:</b>
 * Given a sorted array of integers and a target value, find the starting and
 * ending position of the target. If not found, return [-1, -1].
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Find range of offers within a price bracket</li>
 *   <li>Locate all transactions within a date range</li>
 *   <li>Search for reward tier boundaries</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: nums = [5,7,7,8,8,10], target = 8 → Output: [3,4]</li>
 *   <li>Input: nums = [5,7,7,8,8,10], target = 6 → Output: [-1,-1]</li>
 *   <li>Input: nums = [], target = 0 → Output: [-1,-1]</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Facebook, Amazon, LinkedIn, Microsoft
 *
 * @see <a href="https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/">LeetCode 34</a>
 */
@Component
public class BinarySearchVariants implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(BinarySearchVariants.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== BinarySearchVariants: Binary Search Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate finding offer range in sorted price list
    System.out.println("--- Finding Price Range in Sorted Offers ---\n");
    int[] prices = {50, 100, 100, 100, 150, 200, 200, 250};
    int targetPrice = 100;
    System.out.println("Sorted prices: " + Arrays.toString(prices));
    System.out.println("Target price: $" + targetPrice);
    int[] range = searchRange(prices, targetPrice);
    System.out.println("Range of $" + targetPrice + " offers: " + Arrays.toString(range) + "\n");

    // Test cases
    System.out.println("--- Additional Examples ---\n");
    int[][] testArrays = {
        {5, 7, 7, 8, 8, 10},
        {5, 7, 7, 8, 8, 10},
        {}
    };
    int[] targets = {8, 6, 0};

    for (int i = 0; i < testArrays.length; i++) {
      int[] nums = testArrays[i];
      int target = targets[i];
      int[] result = searchRange(nums, target);
      System.out.println("Array: " + Arrays.toString(nums));
      System.out.println("Target: " + target);
      System.out.println("Range: " + Arrays.toString(result) + "\n");
    }
  }

  /**
   * Finds first and last position of target using two binary searches.
   *
   * <p><b>LOGIC (Modified Binary Search):</b>
   * <ol>
   *   <li>First binary search: find leftmost (first) occurrence</li>
   *   <li>Second binary search: find rightmost (last) occurrence</li>
   *   <li>Key modification: when target found, continue searching to find boundary</li>
   * </ol>
   *
   * <p><b>Example Walkthrough (Finding First 8):</b>
   * <pre>
   * nums = [5, 7, 7, 8, 8, 10], target = 8
   *
   * left=0, right=5, mid=2, nums[2]=7 < 8 → left=3
   * left=3, right=5, mid=4, nums[4]=8 = 8 → found! but continue left
   *                                         right=3, result=4
   * left=3, right=3, mid=3, nums[3]=8 = 8 → found! continue left
   *                                         right=2, result=3
   * left=3, right=2 → exit
   *
   * First position: 3
   * </pre>
   *
   * <p><b>Time Complexity: O(log n)</b>
   * <br>Two binary searches, each O(log n).
   * <br><i>Like finding a word in a dictionary - you flip to the middle,
   * decide which half to search, repeat. With n pages, ~log(n) flips.</i>
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>Only using a few pointer variables.
   * <br><i>Like using bookmarks - just a few fingers to track position.</i>
   *
   * @param nums sorted array of integers
   * @param target value to find
   * @return array of [first position, last position], or [-1,-1] if not found
   */
  public static int[] searchRange(int[] nums, int target) {
    int[] result = {-1, -1};

    if (nums == null || nums.length == 0) {
      return result;
    }

    // Find first occurrence.
    result[0] = findFirst(nums, target);

    // If first not found, target doesn't exist.
    if (result[0] == -1) {
      return result;
    }

    // Find last occurrence.
    result[1] = findLast(nums, target);

    return result;
  }

  /**
   * Finds the first (leftmost) occurrence of target.
   *
   * @param nums sorted array
   * @param target value to find
   * @return first index, or -1 if not found
   */
  private static int findFirst(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;
    int result = -1;

    while (left <= right) {
      int mid = left + (right - left) / 2;

      if (nums[mid] == target) {
        result = mid;           // Found, but keep searching left.
        right = mid - 1;        // Look for earlier occurrence.
      } else if (nums[mid] < target) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return result;
  }

  /**
   * Finds the last (rightmost) occurrence of target.
   *
   * @param nums sorted array
   * @param target value to find
   * @return last index, or -1 if not found
   */
  private static int findLast(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;
    int result = -1;

    while (left <= right) {
      int mid = left + (right - left) / 2;

      if (nums[mid] == target) {
        result = mid;           // Found, but keep searching right.
        left = mid + 1;         // Look for later occurrence.
      } else if (nums[mid] < target) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return result;
  }

  /**
   * Standard binary search - returns index if found, -1 otherwise.
   *
   * @param nums sorted array
   * @param target value to find
   * @return index of target, or -1
   */
  public static int binarySearch(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;

    while (left <= right) {
      int mid = left + (right - left) / 2;  // Avoids integer overflow.

      if (nums[mid] == target) {
        return mid;
      } else if (nums[mid] < target) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return -1;
  }
}
