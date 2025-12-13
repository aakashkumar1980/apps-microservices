package com.example.tutorial.dsa.medium.matrix;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * MatrixRotator
 * ----------------------------------
 * <p>This program rotates a matrix 90 degrees clockwise in-place.
 * The core problem solved here is Rotate Image (LeetCode #48).
 *
 * <p><b>Problem Statement:</b>
 * Given an n x n 2D matrix, rotate it 90 degrees clockwise in-place.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Rotate dashboard grid layouts</li>
 *   <li>Transform data visualization orientations</li>
 *   <li>Reorient heatmap displays</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: [[1,2,3],[4,5,6],[7,8,9]] → Output: [[7,4,1],[8,5,2],[9,6,3]]</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Microsoft (35% interview frequency!), Amazon, Google
 *
 * @see <a href="https://leetcode.com/problems/rotate-image/">LeetCode 48 - Rotate Image</a>
 */
@Component
public class MatrixRotator implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(MatrixRotator.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== MatrixRotator: 90° Clockwise Rotation Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate matrix rotation
    System.out.println("--- Rotating Dashboard Grid ---\n");
    int[][] dashboard = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9}
    };

    System.out.println("Original:");
    printMatrix(dashboard);

    rotate(dashboard);

    System.out.println("After 90° clockwise rotation:");
    printMatrix(dashboard);

    // 4x4 example
    System.out.println("--- 4x4 Matrix Example ---\n");
    int[][] matrix4 = {
        {1, 2, 3, 4},
        {5, 6, 7, 8},
        {9, 10, 11, 12},
        {13, 14, 15, 16}
    };

    System.out.println("Original:");
    printMatrix(matrix4);

    rotate(matrix4);

    System.out.println("After 90° clockwise rotation:");
    printMatrix(matrix4);
  }

  /**
   * Rotates n x n matrix 90 degrees clockwise in-place.
   *
   * <p><b>LOGIC (Transpose + Reverse Rows):</b>
   * <ol>
   *   <li>Transpose the matrix (swap [i][j] with [j][i])</li>
   *   <li>Reverse each row</li>
   * </ol>
   *
   * <p><b>Why this works:</b>
   * <pre>
   * Original:    Transpose:    Reverse rows:
   * 1 2 3        1 4 7         7 4 1
   * 4 5 6   →    2 5 8    →    8 5 2
   * 7 8 9        3 6 9         9 6 3
   * </pre>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * Step 1 - Transpose (swap across diagonal):
   *   [0][1]↔[1][0]: 2↔4
   *   [0][2]↔[2][0]: 3↔7
   *   [1][2]↔[2][1]: 6↔8
   *   Result: [[1,4,7],[2,5,8],[3,6,9]]
   *
   * Step 2 - Reverse each row:
   *   Row 0: [1,4,7] → [7,4,1]
   *   Row 1: [2,5,8] → [8,5,2]
   *   Row 2: [3,6,9] → [9,6,3]
   *   Result: [[7,4,1],[8,5,2],[9,6,3]]
   * </pre>
   *
   * <p><b>Time Complexity: O(n²)</b>
   * <br>We visit each element twice (transpose + reverse).
   * <br><i>Like flipping a photo twice - once diagonally, once horizontally.
   * With n² pixels, you touch each pixel twice = 2n² work = O(n²).</i>
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>In-place rotation using swaps, no extra matrix needed.
   * <br><i>Like rotating a physical picture frame - you rearrange what's there
   * without needing another frame.</i>
   *
   * @param matrix n x n matrix to rotate
   */
  public static void rotate(int[][] matrix) {
    if (matrix == null || matrix.length == 0) {
      return;
    }

    int n = matrix.length;

    // Step 1: Transpose (swap [i][j] with [j][i]).
    // Only swap above diagonal (i < j) to avoid double-swapping.
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        int temp = matrix[i][j];
        matrix[i][j] = matrix[j][i];
        matrix[j][i] = temp;
      }
    }

    // Step 2: Reverse each row.
    for (int i = 0; i < n; i++) {
      int left = 0, right = n - 1;
      while (left < right) {
        int temp = matrix[i][left];
        matrix[i][left] = matrix[i][right];
        matrix[i][right] = temp;
        left++;
        right--;
      }
    }
  }

  /**
   * Rotates 90 degrees counter-clockwise.
   * Method: Transpose + Reverse columns (or Reverse rows + Transpose).
   *
   * @param matrix n x n matrix to rotate
   */
  public static void rotateCounterClockwise(int[][] matrix) {
    if (matrix == null || matrix.length == 0) {
      return;
    }

    int n = matrix.length;

    // Step 1: Transpose.
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        int temp = matrix[i][j];
        matrix[i][j] = matrix[j][i];
        matrix[j][i] = temp;
      }
    }

    // Step 2: Reverse each column.
    for (int j = 0; j < n; j++) {
      int top = 0, bottom = n - 1;
      while (top < bottom) {
        int temp = matrix[top][j];
        matrix[top][j] = matrix[bottom][j];
        matrix[bottom][j] = temp;
        top++;
        bottom--;
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
