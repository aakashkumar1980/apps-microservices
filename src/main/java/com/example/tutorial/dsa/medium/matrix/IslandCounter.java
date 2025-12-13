package com.example.tutorial.dsa.medium.matrix;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * IslandCounter
 * ----------------------------------
 * <p>This program counts the number of islands in a 2D grid.
 * The core problem solved here is Number of Islands (LeetCode #200).
 *
 * <p><b>Problem Statement:</b>
 * Given an m x n 2D binary grid which represents a map of '1's (land) and '0's (water),
 * return the number of islands. An island is surrounded by water and is formed by
 * connecting adjacent lands horizontally or vertically.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Identify clusters of related transactions</li>
 *   <li>Group connected merchant locations</li>
 *   <li>Find connected fraud patterns in transaction matrix</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Grid with isolated 1s → Each 1 is an island</li>
 *   <li>Grid with connected 1s → Connected 1s form one island</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Microsoft, Facebook, Google
 *
 * @see <a href="https://leetcode.com/problems/number-of-islands/">LeetCode 200 - Number of Islands</a>
 */
@Component
public class IslandCounter implements CommandLineRunner {
  // Direction arrays for 4-directional movement (up, down, left, right).
  private static final int[] DR = {-1, 1, 0, 0};
  private static final int[] DC = {0, 0, -1, 1};

  public static void main(String[] args) {
    SpringApplication.run(IslandCounter.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== IslandCounter: Connected Components Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate island counting
    System.out.println("--- Counting Islands (DFS approach) ---\n");

    char[][] grid1 = {
        {'1', '1', '1', '1', '0'},
        {'1', '1', '0', '1', '0'},
        {'1', '1', '0', '0', '0'},
        {'0', '0', '0', '0', '0'}
    };
    printGrid(grid1);
    System.out.println("Number of islands (DFS): " + numIslandsDFS(grid1) + "\n");

    char[][] grid2 = {
        {'1', '1', '0', '0', '0'},
        {'1', '1', '0', '0', '0'},
        {'0', '0', '1', '0', '0'},
        {'0', '0', '0', '1', '1'}
    };
    printGrid(grid2);
    System.out.println("Number of islands (DFS): " + numIslandsDFS(grid2) + "\n");

    // BFS approach
    System.out.println("--- Counting Islands (BFS approach) ---\n");
    char[][] grid3 = {
        {'1', '0', '1', '0', '1'},
        {'0', '1', '0', '1', '0'},
        {'1', '0', '1', '0', '1'}
    };
    printGrid(grid3);
    System.out.println("Number of islands (BFS): " + numIslandsBFS(grid3) + "\n");
  }

  /**
   * Counts islands using DFS (Depth-First Search).
   *
   * <p><b>LOGIC (DFS Flood Fill):</b>
   * <ol>
   *   <li>Scan grid cell by cell</li>
   *   <li>When '1' found, increment count and "sink" the entire island</li>
   *   <li>Sinking = DFS to mark all connected land as visited ('0')</li>
   *   <li>Continue scanning for next unvisited island</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * Grid:          After sinking island 1:
   * 1 1 0          0 0 0
   * 1 0 0    →     0 0 0
   * 0 0 1          0 0 1
   *
   * Found '1' at (0,0), sink it (DFS marks all connected as '0')
   * Continue scan, find '1' at (2,2), sink it
   * Total islands: 2
   * </pre>
   *
   * <p><b>Time Complexity: O(m × n)</b>
   * <br>Each cell is visited at most once.
   * <br><i>Like searching a map for countries - once you've colored a country,
   * you don't need to visit those cells again.</i>
   *
   * <p><b>Space Complexity: O(m × n)</b>
   * <br>Worst case recursion depth if entire grid is one island.
   * <br>Can be O(min(m, n)) with iterative BFS approach.
   *
   * @param grid the 2D grid of '1's (land) and '0's (water)
   * @return number of islands
   */
  public static int numIslandsDFS(char[][] grid) {
    if (grid == null || grid.length == 0) {
      return 0;
    }

    int rows = grid.length;
    int cols = grid[0].length;
    int islands = 0;

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (grid[r][c] == '1') {
          islands++;
          dfs(grid, r, c);  // Sink the island.
        }
      }
    }

    return islands;
  }

  /**
   * DFS helper to sink an island by marking all connected land as water.
   */
  private static void dfs(char[][] grid, int r, int c) {
    int rows = grid.length;
    int cols = grid[0].length;

    // Boundary and water check.
    if (r < 0 || r >= rows || c < 0 || c >= cols || grid[r][c] == '0') {
      return;
    }

    // Mark as visited (sink the land).
    grid[r][c] = '0';

    // Explore all 4 directions.
    for (int d = 0; d < 4; d++) {
      dfs(grid, r + DR[d], c + DC[d]);
    }
  }

  /**
   * Counts islands using BFS (Breadth-First Search).
   *
   * <p><b>LOGIC (BFS Flood Fill):</b>
   * <ol>
   *   <li>Scan grid cell by cell</li>
   *   <li>When '1' found, increment count and BFS to sink entire island</li>
   *   <li>BFS uses queue to visit neighbors level by level</li>
   *   <li>Continue scanning for next unvisited island</li>
   * </ol>
   *
   * <p><b>Time Complexity: O(m × n)</b>
   * <br>Each cell visited at most once.
   *
   * <p><b>Space Complexity: O(min(m, n))</b>
   * <br>Queue size bounded by the smaller dimension.
   *
   * @param grid the 2D grid of '1's (land) and '0's (water)
   * @return number of islands
   */
  public static int numIslandsBFS(char[][] grid) {
    if (grid == null || grid.length == 0) {
      return 0;
    }

    int rows = grid.length;
    int cols = grid[0].length;
    int islands = 0;

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (grid[r][c] == '1') {
          islands++;
          bfs(grid, r, c);  // Sink the island.
        }
      }
    }

    return islands;
  }

  /**
   * BFS helper to sink an island by marking all connected land as water.
   */
  private static void bfs(char[][] grid, int startR, int startC) {
    int rows = grid.length;
    int cols = grid[0].length;

    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[] {startR, startC});
    grid[startR][startC] = '0';  // Mark as visited.

    while (!queue.isEmpty()) {
      int[] cell = queue.poll();
      int r = cell[0];
      int c = cell[1];

      // Explore all 4 directions.
      for (int d = 0; d < 4; d++) {
        int nr = r + DR[d];
        int nc = c + DC[d];

        // Check bounds and if it's land.
        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] == '1') {
          grid[nr][nc] = '0';  // Mark as visited.
          queue.offer(new int[] {nr, nc});
        }
      }
    }
  }

  /**
   * Helper method to print a grid.
   */
  private static void printGrid(char[][] grid) {
    System.out.println("Grid:");
    for (char[] row : grid) {
      System.out.print("  ");
      for (char cell : row) {
        System.out.print(cell + " ");
      }
      System.out.println();
    }
  }
}
