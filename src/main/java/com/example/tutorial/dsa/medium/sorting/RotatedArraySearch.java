package com.example.tutorial.dsa.medium.sorting;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * RotatedArraySearch
 * ----------------------------------
 * <p>This program searches in a rotated sorted array using modified binary search.
 * The core problem solved here is Search in Rotated Sorted Array (LeetCode #33).
 *
 * <p><b>Problem Statement:</b>
 * Given a rotated sorted array and a target, return its index or -1 if not found.
 * The array was originally sorted in ascending order, then rotated at some pivot.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Search circular buffer of recent transactions</li>
 *   <li>Find offer in a rotated schedule</li>
 *   <li>Locate data in cyclic sorted structures</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: nums = [4,5,6,7,0,1,2], target = 0 → Output: 4</li>
 *   <li>Input: nums = [4,5,6,7,0,1,2], target = 3 → Output: -1</li>
 *   <li>Input: nums = [1], target = 0 → Output: -1</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Facebook, Amazon, Microsoft, LinkedIn (⭐ Very Popular!)
 *
 * @see <a href="https://leetcode.com/problems/search-in-rotated-sorted-array/">LeetCode 33</a>
 */
@Component
public class RotatedArraySearch implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(RotatedArraySearch.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== RotatedArraySearch: Search in Rotated Array Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate with rotated transaction IDs
    System.out.println("--- Searching Rotated Transaction Buffer ---\n");
    int[] transactionIds = {400, 500, 600, 100, 200, 300};
    int targetId = 200;
    System.out.println("Rotated buffer: " + Arrays.toString(transactionIds));
    System.out.println("Looking for ID: " + targetId);
    int index = search(transactionIds, targetId);
    System.out.println("Found at index: " + index + "\n");

    // Test cases
    System.out.println("--- Additional Examples ---\n");
    int[][] testArrays = {
        {4, 5, 6, 7, 0, 1, 2},
        {4, 5, 6, 7, 0, 1, 2},
        {1}
    };
    int[] targets = {0, 3, 0};

    for (int i = 0; i < testArrays.length; i++) {
      int[] nums = testArrays[i];
      int target = targets[i];
      int result = search(nums, target);
      System.out.println("Array: " + Arrays.toString(nums));
      System.out.println("Target: " + target);
      System.out.println("Index: " + result + "\n");
    }
  }

  /**
   * Searches for target in rotated sorted array using modified binary search.
   *
   * <p><b>LOGIC (Modified Binary Search):</b>
   * <ol>
   *   <li>Find mid, check if mid is target</li>
   *   <li>Determine which half is sorted (left or right)</li>
   *   <li>If target is in the sorted half, search there</li>
   *   <li>Otherwise, search the other half</li>
   * </ol>
   *
   * <p><b>Key Insight:</b>
   * <br>In a rotated array, at least one half (left or right of mid) is always sorted.
   * <br>We can easily check if target is in the sorted half.
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * nums = [4,5,6,7,0,1,2], target = 0
   *
   * left=0, right=6, mid=3, nums[mid]=7
   * Left half [4,5,6,7] is sorted (nums[left]=4 <= nums[mid]=7)
   * Target 0 not in [4,7], so search right half
   *
   * left=4, right=6, mid=5, nums[mid]=1
   * Left half [0,1] is sorted
   * Target 0 in [0,1]? Yes! Search left half
   *
   * left=4, right=4, mid=4, nums[mid]=0 = target!
   *
   * Return 4
   * </pre>
   *
   * <p><b>Time Complexity: O(log n)</b>
   * <br>Binary search - we eliminate half the array each iteration.
   * <br><i>Like finding a page in a scrambled book - even if pages are out of order,
   * you can still halve the search space each time.</i>
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>Only using pointer variables.
   *
   * @param nums rotated sorted array
   * @param target value to find
   * @return index of target, or -1 if not found
   */
  public static int search(int[] nums, int target) {
    if (nums == null || nums.length == 0) {
      return -1;
    }

    int left = 0;
    int right = nums.length - 1;

    while (left <= right) {
      int mid = left + (right - left) / 2;

      // Found target!
      if (nums[mid] == target) {
        return mid;
      }

      // Determine which half is sorted.
      if (nums[left] <= nums[mid]) {
        // Left half [left, mid] is sorted.
        if (target >= nums[left] && target < nums[mid]) {
          // Target is in sorted left half.
          right = mid - 1;
        } else {
          // Target is in right half.
          left = mid + 1;
        }
      } else {
        // Right half [mid, right] is sorted.
        if (target > nums[mid] && target <= nums[right]) {
          // Target is in sorted right half.
          left = mid + 1;
        } else {
          // Target is in left half.
          right = mid - 1;
        }
      }
    }

    return -1;
  }
}
