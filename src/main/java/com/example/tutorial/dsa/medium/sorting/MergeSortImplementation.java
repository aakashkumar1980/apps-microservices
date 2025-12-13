package com.example.tutorial.dsa.medium.sorting;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * MergeSortImplementation
 * ----------------------------------
 * <p>This program demonstrates Merge Sort using divide and conquer.
 * Also solves Sort List (LeetCode #148) conceptually.
 *
 * <p><b>Problem Statement:</b>
 * Implement merge sort algorithm to sort an array in O(n log n) time.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Sort large transaction datasets efficiently</li>
 *   <li>Stable sort for maintaining original order of equal elements</li>
 *   <li>External sorting for data that doesn't fit in memory</li>
 * </ul>
 *
 * <p><b>Key Properties:</b>
 * <ul>
 *   <li>Stable sort (maintains relative order of equal elements)</li>
 *   <li>Guaranteed O(n log n) time - no worst case degradation</li>
 *   <li>Parallelizable - subarrays can be sorted independently</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Microsoft, Google
 *
 * @see <a href="https://leetcode.com/problems/sort-list/">LeetCode 148 - Sort List</a>
 */
@Component
public class MergeSortImplementation implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(MergeSortImplementation.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== MergeSortImplementation: Merge Sort Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate sorting transaction amounts
    System.out.println("--- Sorting Transaction Amounts ---\n");
    int[] transactions = {350, 120, 500, 200, 90, 400, 180};
    System.out.println("Original: " + Arrays.toString(transactions));
    mergeSort(transactions);
    System.out.println("Sorted:   " + Arrays.toString(transactions) + "\n");

    // Test cases
    System.out.println("--- Additional Examples ---\n");
    int[][] testArrays = {
        {38, 27, 43, 3, 9, 82, 10},
        {5, 4, 3, 2, 1},
        {1}
    };

    for (int[] arr : testArrays) {
      System.out.println("Original: " + Arrays.toString(arr));
      mergeSort(arr);
      System.out.println("Sorted:   " + Arrays.toString(arr) + "\n");
    }
  }

  /**
   * Sorts array using merge sort algorithm.
   *
   * <p><b>LOGIC (Divide and Conquer):</b>
   * <ol>
   *   <li>Divide: Split array into two halves</li>
   *   <li>Conquer: Recursively sort each half</li>
   *   <li>Combine: Merge two sorted halves</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * [38, 27, 43, 3]
   *
   * Divide: [38, 27] and [43, 3]
   *
   * [38, 27] → [38] and [27] → merge → [27, 38]
   * [43, 3]  → [43] and [3]  → merge → [3, 43]
   *
   * Merge [27, 38] and [3, 43]:
   *   Compare 27 vs 3 → take 3 → [3]
   *   Compare 27 vs 43 → take 27 → [3, 27]
   *   Compare 38 vs 43 → take 38 → [3, 27, 38]
   *   Take remaining 43 → [3, 27, 38, 43]
   *
   * Result: [3, 27, 38, 43]
   * </pre>
   *
   * <p><b>Time Complexity: O(n log n)</b>
   * <br>log n levels of recursion, each level does O(n) work merging.
   * <br><i>Like sorting a deck of cards - split into piles (log n splits),
   * then merge piles back (n comparisons per level). Total = n × log n.</i>
   *
   * <p><b>Space Complexity: O(n)</b>
   * <br>Temporary array needed for merging.
   * <br><i>Like needing a temporary table to merge two sorted piles of papers.</i>
   *
   * @param arr the array to sort
   */
  public static void mergeSort(int[] arr) {
    if (arr == null || arr.length <= 1) {
      return;
    }

    int[] temp = new int[arr.length];
    mergeSortHelper(arr, temp, 0, arr.length - 1);
  }

  private static void mergeSortHelper(int[] arr, int[] temp, int left, int right) {
    if (left >= right) {
      return;  // Base case: single element is sorted.
    }

    int mid = left + (right - left) / 2;

    // Recursively sort left and right halves.
    mergeSortHelper(arr, temp, left, mid);
    mergeSortHelper(arr, temp, mid + 1, right);

    // Merge the sorted halves.
    merge(arr, temp, left, mid, right);
  }

  private static void merge(int[] arr, int[] temp, int left, int mid, int right) {
    // Copy to temp array.
    for (int i = left; i <= right; i++) {
      temp[i] = arr[i];
    }

    int i = left;      // Pointer for left half.
    int j = mid + 1;   // Pointer for right half.
    int k = left;      // Pointer for merged array.

    // Merge by comparing elements.
    while (i <= mid && j <= right) {
      if (temp[i] <= temp[j]) {
        arr[k++] = temp[i++];
      } else {
        arr[k++] = temp[j++];
      }
    }

    // Copy remaining elements from left half (right half already in place).
    while (i <= mid) {
      arr[k++] = temp[i++];
    }
  }
}
