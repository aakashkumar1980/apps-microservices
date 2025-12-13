package com.example.tutorial.dsa.medium.matrix;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * SpiralMatrixTraversal
 * ----------------------------------
 * <p>This program traverses a matrix in spiral order.
 * The core problem solved here is Spiral Matrix (LeetCode #54).
 *
 * <p><b>Problem Statement:</b>
 * Given an m x n matrix, return all elements in spiral order.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Generate dashboard display sequence</li>
 *   <li>Traverse data grids for reporting</li>
 *   <li>Create animation paths for UI elements</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: [[1,2,3],[4,5,6],[7,8,9]] → Output: [1,2,3,6,9,8,7,4,5]</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Microsoft, Google
 *
 * @see <a href="https://leetcode.com/problems/spiral-matrix/">LeetCode 54 - Spiral Matrix</a>
 */
@Component
public class SpiralMatrixTraversal implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(SpiralMatrixTraversal.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== SpiralMatrixTraversal: Spiral Order Demo ===\n");

    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demo with 3x3 matrix
    System.out.println("--- 3x3 Matrix Spiral ---\n");
    int[][] matrix3 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    printMatrix(matrix3);
    System.out.println("Spiral order: " + spiralOrder(matrix3) + "\n");

    // Demo with 3x4 matrix
    System.out.println("--- 3x4 Matrix Spiral ---\n");
    int[][] matrix34 = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
    printMatrix(matrix34);
    System.out.println("Spiral order: " + spiralOrder(matrix34));
  }

  /**
   * Returns elements in spiral order using boundary tracking.
   *
   * <p><b>LOGIC (Four Boundaries):</b>
   * <ol>
   *   <li>Maintain top, bottom, left, right boundaries</li>
   *   <li>Traverse right along top row, then shrink top</li>
   *   <li>Traverse down along right column, then shrink right</li>
   *   <li>Traverse left along bottom row, then shrink bottom</li>
   *   <li>Traverse up along left column, then shrink left</li>
   *   <li>Repeat until boundaries cross</li>
   * </ol>
   *
   * <p><b>Time Complexity: O(m × n)</b>
   * <br>Visit each element exactly once.
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>Only using boundary variables (output list not counted).
   *
   * @param matrix the input matrix
   * @return elements in spiral order
   */
  public static List<Integer> spiralOrder(int[][] matrix) {
    List<Integer> result = new ArrayList<>();
    if (matrix == null || matrix.length == 0) {
      return result;
    }

    int top = 0, bottom = matrix.length - 1;
    int left = 0, right = matrix[0].length - 1;

    while (top <= bottom && left <= right) {
      // Traverse right along top row.
      for (int j = left; j <= right; j++) {
        result.add(matrix[top][j]);
      }
      top++;

      // Traverse down along right column.
      for (int i = top; i <= bottom; i++) {
        result.add(matrix[i][right]);
      }
      right--;

      // Traverse left along bottom row (if still valid).
      if (top <= bottom) {
        for (int j = right; j >= left; j--) {
          result.add(matrix[bottom][j]);
        }
        bottom--;
      }

      // Traverse up along left column (if still valid).
      if (left <= right) {
        for (int i = bottom; i >= top; i--) {
          result.add(matrix[i][left]);
        }
        left++;
      }
    }

    return result;
  }

  private static void printMatrix(int[][] matrix) {
    for (int[] row : matrix) {
      StringBuilder sb = new StringBuilder("  [");
      for (int i = 0; i < row.length; i++) {
        sb.append(String.format("%2d", row[i]));
        if (i < row.length - 1) sb.append(", ");
      }
      sb.append("]");
      System.out.println(sb);
    }
    System.out.println();
  }
}
