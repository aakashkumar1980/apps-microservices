package com.example.tutorial.dsa.medium.sorting;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * QuickSelectAlgorithm
 * ----------------------------------
 * <p>This program finds the kth smallest/largest element using QuickSelect.
 * An efficient alternative to full sorting when you only need one element.
 *
 * <p><b>Problem Statement:</b>
 * Find the kth smallest element in an unsorted array in O(n) average time.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Find median transaction without full sorting</li>
 *   <li>Quickly identify kth highest spender</li>
 *   <li>Select percentile values for analytics</li>
 * </ul>
 *
 * <p><b>Key Properties:</b>
 * <ul>
 *   <li>O(n) average time vs O(n log n) for full sort</li>
 *   <li>In-place algorithm - modifies input array</li>
 *   <li>Related to QuickSort's partitioning</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Facebook, Amazon, Google
 */
@Component
public class QuickSelectAlgorithm implements CommandLineRunner {
  private static final Random random = new Random();

  public static void main(String[] args) {
    SpringApplication.run(QuickSelectAlgorithm.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== QuickSelectAlgorithm: Selection Algorithm Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate finding kth element
    System.out.println("--- Finding Kth Smallest Transaction ---\n");
    int[] transactions = {350, 120, 500, 200, 90, 400, 180};
    System.out.println("Transactions: " + Arrays.toString(transactions));

    for (int k = 1; k <= 3; k++) {
      int[] copy = transactions.clone();
      int kthSmallest = quickSelect(copy, k);
      System.out.println("  " + k + "th smallest: $" + kthSmallest);
    }

    // Find median
    System.out.println("\n--- Finding Median ---\n");
    int[] data = {7, 2, 1, 8, 6, 3, 5, 4};
    int n = data.length;
    int median;
    if (n % 2 == 1) {
      median = quickSelect(data.clone(), (n + 1) / 2);
    } else {
      int left = quickSelect(data.clone(), n / 2);
      int right = quickSelect(data.clone(), n / 2 + 1);
      median = (left + right) / 2;
    }
    System.out.println("Data: " + Arrays.toString(data));
    System.out.println("Median: " + median);
  }

  /**
   * Finds the kth smallest element using QuickSelect algorithm.
   *
   * <p><b>LOGIC (Partition-based Selection):</b>
   * <ol>
   *   <li>Choose a pivot (randomly for better average case)</li>
   *   <li>Partition array: elements < pivot go left, > pivot go right</li>
   *   <li>If pivot is at position k-1, we found our answer</li>
   *   <li>Otherwise, recurse into the appropriate half</li>
   * </ol>
   *
   * <p><b>Key Insight:</b>
   * <br>After partitioning, we know exactly how many elements are smaller than pivot.
   * <br>We only need to recurse into ONE half, not both (unlike QuickSort).
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * arr = [7, 2, 1, 8, 6, 3], k = 3 (find 3rd smallest)
   *
   * Partition with pivot 3:
   *   Elements < 3: [2, 1]
   *   Pivot: 3 at index 2
   *   Elements > 3: [8, 6, 7]
   *
   * Pivot at index 2 = k-1? Yes! (k=3, index=2)
   *
   * Return 3
   * </pre>
   *
   * <p><b>Time Complexity: O(n) average, O(n²) worst</b>
   * <br>Each partition reduces problem by roughly half (average).
   * <br><i>Like finding a specific person in a crowd - divide crowd, check which side,
   * repeat. Usually n + n/2 + n/4... = ~2n = O(n).</i>
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>In-place partitioning, but input array is modified.
   *
   * @param arr the array to search (will be modified)
   * @param k which smallest element to find (1-indexed)
   * @return the kth smallest element
   */
  public static int quickSelect(int[] arr, int k) {
    if (arr == null || k < 1 || k > arr.length) {
      throw new IllegalArgumentException("Invalid input");
    }

    return quickSelectHelper(arr, 0, arr.length - 1, k - 1);  // Convert to 0-indexed.
  }

  private static int quickSelectHelper(int[] arr, int left, int right, int k) {
    // Base case: single element.
    if (left == right) {
      return arr[left];
    }

    // Randomized pivot selection for better average case.
    int pivotIndex = left + random.nextInt(right - left + 1);
    pivotIndex = partition(arr, left, right, pivotIndex);

    // Check where pivot ended up.
    if (k == pivotIndex) {
      return arr[k];
    } else if (k < pivotIndex) {
      return quickSelectHelper(arr, left, pivotIndex - 1, k);
    } else {
      return quickSelectHelper(arr, pivotIndex + 1, right, k);
    }
  }

  /**
   * Partitions array around pivot using Lomuto scheme.
   *
   * @param arr the array
   * @param left start index
   * @param right end index
   * @param pivotIndex index of pivot element
   * @return final position of pivot after partitioning
   */
  private static int partition(int[] arr, int left, int right, int pivotIndex) {
    int pivotValue = arr[pivotIndex];

    // Move pivot to end.
    swap(arr, pivotIndex, right);

    // Partition elements.
    int storeIndex = left;
    for (int i = left; i < right; i++) {
      if (arr[i] < pivotValue) {
        swap(arr, i, storeIndex);
        storeIndex++;
      }
    }

    // Move pivot to its final position.
    swap(arr, storeIndex, right);

    return storeIndex;
  }

  private static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }
}
