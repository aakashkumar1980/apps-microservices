package com.example.tutorial.dsa.basics.arrays;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * DataRotationHandler
 * ----------------------------------
 * <p>This program rotates array elements by k positions using reversal algorithm.
 * The core problem solved here is Rotate Array (LeetCode #189).
 *
 * <p><b>Problem Statement:</b>
 * Given an integer array nums, rotate the array to the right by k steps.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Rotate featured offers carousel display</li>
 *   <li>Cycle through promotional content</li>
 *   <li>Implement round-robin offer distribution</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: nums = [1,2,3,4,5,6,7], k = 3 → Output: [5,6,7,1,2,3,4]</li>
 *   <li>Input: nums = [-1,-100,3,99], k = 2 → Output: [3,99,-1,-100]</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Microsoft, Amazon, Facebook
 *
 * @see <a href="https://leetcode.com/problems/rotate-array/">LeetCode 189 - Rotate Array</a>
 */
@Component
public class DataRotationHandler implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataRotationHandler.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== DataRotationHandler: Array Rotation Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate rotation with offer titles
    System.out.println("--- Rotating Offer Titles Display ---\n");
    String[] offerTitles = offers.stream()
        .map(Offer::getTitle)
        .toArray(String[]::new);

    System.out.println("Original order: " + Arrays.toString(offerTitles));
    rotateStrings(offerTitles, 2);
    System.out.println("After rotating by 2: " + Arrays.toString(offerTitles) + "\n");

    // Demonstrate with numeric arrays
    System.out.println("--- Numeric Array Examples ---\n");
    int[][] testArrays = {
        {1, 2, 3, 4, 5, 6, 7},
        {-1, -100, 3, 99}
    };
    int[] rotations = {3, 2};

    for (int i = 0; i < testArrays.length; i++) {
      int[] nums = testArrays[i].clone();
      System.out.println("Original: " + Arrays.toString(nums));
      System.out.println("Rotate by: " + rotations[i]);
      rotate(nums, rotations[i]);
      System.out.println("Result: " + Arrays.toString(nums) + "\n");
    }
  }

  /**
   * Rotates array to the right by k positions using reversal algorithm.
   *
   * <p><b>LOGIC (Reversal Algorithm):</b>
   * <ol>
   *   <li>Normalize k (in case k > array length)</li>
   *   <li>Reverse entire array</li>
   *   <li>Reverse first k elements</li>
   *   <li>Reverse remaining n-k elements</li>
   * </ol>
   *
   * <p><b>Why does reversal work?</b>
   * <br>Think of it like rearranging books on a shelf:
   * <br>- Original: [A B C D E F G], want last 3 at front
   * <br>- Reverse all: [G F E D C B A] - now G,F,E are at front but backwards
   * <br>- Reverse first 3: [E F G D C B A] - first part is correct!
   * <br>- Reverse rest: [E F G A B C D] - second part is correct!
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * nums = [1,2,3,4,5,6,7], k = 3
   *
   * Step 1: k = 3 % 7 = 3 (normalize)
   * Step 2: Reverse all → [7,6,5,4,3,2,1]
   * Step 3: Reverse [0,k-1] → [5,6,7,4,3,2,1]
   * Step 4: Reverse [k,n-1] → [5,6,7,1,2,3,4]
   *
   * Result: [5,6,7,1,2,3,4]
   * </pre>
   *
   * <p><b>Time Complexity: O(n)</b>
   * <br>We perform 3 reversals, each touching at most n elements.
   * <br><i>Like flipping a deck of cards 3 times - each flip goes through
   * all cards once. Total work = 3n, which simplifies to O(n).</i>
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>Reversal is done in-place using only a temp variable for swapping.
   * <br><i>Like rearranging books on a shelf - you only need your two hands
   * to swap books, no extra shelf space required.</i>
   *
   * @param nums the array to rotate
   * @param k number of positions to rotate right
   */
  public static void rotate(int[] nums, int k) {
    // Edge case: empty or single element.
    if (nums == null || nums.length <= 1) {
      return;
    }

    int n = nums.length;

    // Normalize k (if k > n, we only need k % n rotations).
    // Like rotating 10 positions in a 7-element array = 3 positions.
    k = k % n;

    // If k is 0, no rotation needed.
    if (k == 0) {
      return;
    }

    // Three-step reversal.
    reverse(nums, 0, n - 1);      // Reverse entire array.
    reverse(nums, 0, k - 1);      // Reverse first k elements.
    reverse(nums, k, n - 1);      // Reverse remaining elements.
  }

  /**
   * Reverses elements in array between indices start and end (inclusive).
   *
   * @param nums the array
   * @param start starting index
   * @param end ending index
   */
  private static void reverse(int[] nums, int start, int end) {
    while (start < end) {
      // Swap elements using temp variable.
      int temp = nums[start];
      nums[start] = nums[end];
      nums[end] = temp;

      // Move pointers inward.
      start++;
      end--;
    }
  }

  /**
   * String version for rotating string arrays (like offer titles).
   *
   * @param strings the array to rotate
   * @param k number of positions to rotate right
   */
  public static void rotateStrings(String[] strings, int k) {
    if (strings == null || strings.length <= 1) {
      return;
    }

    int n = strings.length;
    k = k % n;
    if (k == 0) {
      return;
    }

    reverseStrings(strings, 0, n - 1);
    reverseStrings(strings, 0, k - 1);
    reverseStrings(strings, k, n - 1);
  }

  private static void reverseStrings(String[] strings, int start, int end) {
    while (start < end) {
      String temp = strings[start];
      strings[start] = strings[end];
      strings[end] = temp;
      start++;
      end--;
    }
  }
}
