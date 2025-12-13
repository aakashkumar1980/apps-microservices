package com.example.tutorial.dsa.medium.matrix;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SearchSorted2DMatrix
 * ----------------------------------
 * <p>This program searches for a target in a sorted 2D matrix.
 * The core problem solved here is Search a 2D Matrix (LeetCode #74).
 *
 * <p><b>Problem Statement:</b>
 * Search for a target in an m x n matrix where each row is sorted and the first
 * integer of each row is greater than the last integer of the previous row.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Binary search in paginated sorted data</li>
 *   <li>Find offer in sorted price-tier matrix</li>
 *   <li>Locate transaction in time-bucketed data</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Microsoft, Facebook
 *
 * @see <a href="https://leetcode.com/problems/search-a-2d-matrix/">LeetCode 74</a>
 */
@Component
public class SearchSorted2DMatrix implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(SearchSorted2DMatrix.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== SearchSorted2DMatrix: Binary Search in 2D Demo ===\n");

    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    int[][] matrix = {
        {1, 3, 5, 7},
        {10, 11, 16, 20},
        {23, 30, 34, 60}
    };

    int[] targets = {3, 13, 60, 1};
    for (int target : targets) {
      boolean found = searchMatrix(matrix, target);
      System.out.println("Search " + target + ": " + (found ? "Found" : "Not found"));
    }
  }

  /**
   * Searches for target using binary search treating matrix as 1D array.
   *
   * <p><b>LOGIC (Flattened Binary Search):</b>
   * <ol>
   *   <li>Treat m x n matrix as sorted 1D array of size m*n</li>
   *   <li>Convert 1D index to 2D: row = idx / n, col = idx % n</li>
   *   <li>Standard binary search</li>
   * </ol>
   *
   * <p><b>Time Complexity: O(log(m × n))</b>
   * <br>Binary search over m*n elements.
   *
   * <p><b>Space Complexity: O(1)</b>
   *
   * @param matrix sorted 2D matrix
   * @param target value to find
   * @return true if target exists
   */
  public static boolean searchMatrix(int[][] matrix, int target) {
    if (matrix == null || matrix.length == 0) return false;

    int m = matrix.length, n = matrix[0].length;
    int left = 0, right = m * n - 1;

    while (left <= right) {
      int mid = left + (right - left) / 2;
      int midValue = matrix[mid / n][mid % n];

      if (midValue == target) {
        return true;
      } else if (midValue < target) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return false;
  }
}
