package com.example.tutorial.dsa.medium.matrix;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * RangeSumQuery2D
 * ----------------------------------
 * <p>This program implements 2D range sum query using prefix sums.
 * The core problem solved here is Range Sum Query 2D - Immutable (LeetCode #304).
 *
 * <p><b>Problem Statement:</b>
 * Given a 2D matrix, handle multiple queries of the form: sum of elements inside
 * the rectangle defined by upper left corner (row1, col1) and lower right corner (row2, col2).
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Sum transactions in a geographic region</li>
 *   <li>Calculate total rewards for a merchant category range</li>
 *   <li>Aggregate spending in time windows</li>
 * </ul>
 *
 * <p><b>Key Insight:</b>
 * Pre-compute prefix sums to answer any rectangular sum query in O(1) time.
 *
 * <p><b>Company Tags:</b> Amazon, Google, Facebook
 *
 * @see <a href="https://leetcode.com/problems/range-sum-query-2d-immutable/">LeetCode 304 - Range Sum Query 2D</a>
 */
@Component
public class RangeSumQuery2D implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(RangeSumQuery2D.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== RangeSumQuery2D: 2D Prefix Sum Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Create sample matrix representing transaction amounts by region/time
    int[][] matrix = {
        {3, 0, 1, 4, 2},
        {5, 6, 3, 2, 1},
        {1, 2, 0, 1, 5},
        {4, 1, 0, 1, 7},
        {1, 0, 3, 0, 5}
    };

    System.out.println("Transaction Matrix:");
    for (int[] row : matrix) {
      System.out.println("  " + Arrays.toString(row));
    }
    System.out.println();

    // Build the NumMatrix object
    NumMatrix numMatrix = new NumMatrix(matrix);

    // Demonstrate range sum queries
    System.out.println("--- Range Sum Queries ---\n");

    // Query 1: Sum of region (2,1) to (4,3)
    int sum1 = numMatrix.sumRegion(2, 1, 4, 3);
    System.out.println("sumRegion(2,1,4,3) = " + sum1);
    System.out.println("  Region: rows 2-4, cols 1-3");
    System.out.println("  Elements: 2+0+1 + 1+0+1 + 0+3+0 = " + sum1 + "\n");

    // Query 2: Sum of region (1,1) to (2,2)
    int sum2 = numMatrix.sumRegion(1, 1, 2, 2);
    System.out.println("sumRegion(1,1,2,2) = " + sum2);
    System.out.println("  Region: rows 1-2, cols 1-2");
    System.out.println("  Elements: 6+3 + 2+0 = " + sum2 + "\n");

    // Query 3: Sum of region (1,2) to (2,4)
    int sum3 = numMatrix.sumRegion(1, 2, 2, 4);
    System.out.println("sumRegion(1,2,2,4) = " + sum3);
    System.out.println("  Region: rows 1-2, cols 2-4");
    System.out.println("  Elements: 3+2+1 + 0+1+5 = " + sum3 + "\n");

    // Show the prefix sum matrix
    System.out.println("--- Prefix Sum Matrix (Internal) ---\n");
    numMatrix.printPrefixSum();
  }

  /**
   * NumMatrix class for 2D range sum queries using prefix sums.
   *
   * <p><b>LOGIC (2D Prefix Sum):</b>
   * <ol>
   *   <li>Build prefix sum matrix where prefix[i][j] = sum of all elements in rectangle (0,0) to (i-1,j-1)</li>
   *   <li>To get sum of rectangle (r1,c1) to (r2,c2):</li>
   *   <li>Use inclusion-exclusion: prefix[r2+1][c2+1] - prefix[r1][c2+1] - prefix[r2+1][c1] + prefix[r1][c1]</li>
   * </ol>
   *
   * <p><b>Visual Explanation:</b>
   * <pre>
   * To find sum of shaded region:
   * +---+---+---+
   * | A |   B   |
   * +---+-------+
   * |   |///////|
   * | C |/QUERY/|
   * |   |///////|
   * +---+-------+
   *
   * Query = Total - B - C + A
   *       = prefix[r2+1][c2+1] - prefix[r1][c2+1] - prefix[r2+1][c1] + prefix[r1][c1]
   *
   * We add A back because it was subtracted twice (once in B, once in C).
   * </pre>
   *
   * <p><b>Time Complexity:</b>
   * <ul>
   *   <li>Constructor: O(m × n) - build prefix sum matrix</li>
   *   <li>sumRegion: O(1) - constant time lookup</li>
   * </ul>
   * <br><i>Like pre-calculating running totals for a spreadsheet - initial work,
   * but then any sum becomes instant.</i>
   *
   * <p><b>Space Complexity: O(m × n)</b>
   * <br>For the prefix sum matrix.
   */
  public static class NumMatrix {
    private final int[][] prefix;

    /**
     * Constructs the prefix sum matrix.
     *
     * @param matrix the input matrix
     */
    public NumMatrix(int[][] matrix) {
      int rows = matrix.length;
      int cols = matrix[0].length;

      // prefix[i][j] = sum of all elements in rectangle (0,0) to (i-1,j-1).
      // We use (rows+1) x (cols+1) to handle edge cases cleanly.
      prefix = new int[rows + 1][cols + 1];

      for (int r = 1; r <= rows; r++) {
        for (int c = 1; c <= cols; c++) {
          // Sum = current element + left prefix + top prefix - overlap (top-left)
          prefix[r][c] = matrix[r - 1][c - 1]
              + prefix[r - 1][c]
              + prefix[r][c - 1]
              - prefix[r - 1][c - 1];
        }
      }
    }

    /**
     * Returns the sum of elements in the rectangle (row1, col1) to (row2, col2).
     *
     * @param row1 top-left row
     * @param col1 top-left column
     * @param row2 bottom-right row
     * @param col2 bottom-right column
     * @return sum of elements in the rectangle
     */
    public int sumRegion(int row1, int col1, int row2, int col2) {
      // Inclusion-exclusion principle.
      return prefix[row2 + 1][col2 + 1]
          - prefix[row1][col2 + 1]
          - prefix[row2 + 1][col1]
          + prefix[row1][col1];
    }

    /**
     * Helper to print the prefix sum matrix.
     */
    public void printPrefixSum() {
      System.out.println("Prefix Sum Matrix (1-indexed):");
      for (int[] row : prefix) {
        System.out.print("  ");
        for (int val : row) {
          System.out.printf("%3d ", val);
        }
        System.out.println();
      }
    }
  }
}
