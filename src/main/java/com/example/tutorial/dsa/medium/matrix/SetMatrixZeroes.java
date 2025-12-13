package com.example.tutorial.dsa.medium.matrix;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * SetMatrixZeroes
 * ----------------------------------
 * <p>This program sets entire row and column to zero if an element is zero.
 * The core problem solved here is Set Matrix Zeroes (LeetCode #73).
 *
 * <p><b>Problem Statement:</b>
 * Given an m x n matrix, if an element is 0, set its entire row and column to 0.
 * Do it in-place.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Nullify related data when invalid entry detected</li>
 *   <li>Cascade data normalization in grids</li>
 *   <li>Reset correlated metrics in dashboards</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Facebook, Amazon, Microsoft
 *
 * @see <a href="https://leetcode.com/problems/set-matrix-zeroes/">LeetCode 73 - Set Matrix Zeroes</a>
 */
@Component
public class SetMatrixZeroes implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(SetMatrixZeroes.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== SetMatrixZeroes: In-place Zero Setting Demo ===\n");

    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    int[][] matrix = {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
    System.out.println("Original:");
    printMatrix(matrix);

    setZeroes(matrix);

    System.out.println("After setting zeroes:");
    printMatrix(matrix);
  }

  /**
   * Sets rows and columns to zero using first row/column as markers.
   *
   * <p><b>LOGIC (O(1) Space with Markers):</b>
   * <ol>
   *   <li>Use first row and column as markers for which rows/cols to zero</li>
   *   <li>Track if first row/column themselves need zeroing (separate flags)</li>
   *   <li>Mark rows/columns by setting markers to 0</li>
   *   <li>Zero out cells based on markers</li>
   *   <li>Finally, zero first row/column if needed</li>
   * </ol>
   *
   * <p><b>Time Complexity: O(m × n)</b>
   * <br>Two passes through the matrix.
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>Using first row/column as markers instead of extra arrays.
   *
   * @param matrix the matrix to modify
   */
  public static void setZeroes(int[][] matrix) {
    if (matrix == null || matrix.length == 0) return;

    int m = matrix.length, n = matrix[0].length;
    boolean firstRowZero = false, firstColZero = false;

    // Check if first row has any zeros.
    for (int j = 0; j < n; j++) {
      if (matrix[0][j] == 0) {
        firstRowZero = true;
        break;
      }
    }

    // Check if first column has any zeros.
    for (int i = 0; i < m; i++) {
      if (matrix[i][0] == 0) {
        firstColZero = true;
        break;
      }
    }

    // Use first row/column as markers.
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        if (matrix[i][j] == 0) {
          matrix[i][0] = 0;  // Mark row.
          matrix[0][j] = 0;  // Mark column.
        }
      }
    }

    // Zero out cells based on markers.
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        if (matrix[i][0] == 0 || matrix[0][j] == 0) {
          matrix[i][j] = 0;
        }
      }
    }

    // Zero first row if needed.
    if (firstRowZero) {
      for (int j = 0; j < n; j++) {
        matrix[0][j] = 0;
      }
    }

    // Zero first column if needed.
    if (firstColZero) {
      for (int i = 0; i < m; i++) {
        matrix[i][0] = 0;
      }
    }
  }

  private static void printMatrix(int[][] matrix) {
    for (int[] row : matrix) {
      System.out.println("  " + Arrays.toString(row));
    }
    System.out.println();
  }
}
