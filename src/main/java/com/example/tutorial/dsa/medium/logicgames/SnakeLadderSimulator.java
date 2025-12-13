package com.example.tutorial.dsa.medium.logicgames;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * SnakeLadderSimulator
 * ----------------------------------
 * <p>This program finds the minimum moves to win Snakes and Ladders.
 * The core problem solved here is Snakes and Ladders (LeetCode #909).
 *
 * <p><b>Problem Statement:</b>
 * Given a snakes and ladders board, find the minimum number of dice rolls
 * required to reach the final square from square 1.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Gamified reward progression systems</li>
 *   <li>Customer journey optimization with shortcuts and penalties</li>
 *   <li>Level-up mechanics for loyalty programs</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Google, Microsoft
 *
 * @see <a href="https://leetcode.com/problems/snakes-and-ladders/">LeetCode 909 - Snakes and Ladders</a>
 */
@Component
public class SnakeLadderSimulator implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(SnakeLadderSimulator.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== SnakeLadderSimulator: Snakes and Ladders Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Example board (6x6 = 36 squares)
    // -1 means no snake/ladder
    // Positive number means snake/ladder destination
    System.out.println("--- Example: 6x6 Board ---\n");

    int[][] board = {
        {-1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1},
        {-1, 35, -1, -1, 13, -1},
        {-1, -1, -1, -1, -1, -1},
        {-1, 15, -1, -1, -1, -1}
    };

    printBoard(board);

    int minMoves = snakesAndLadders(board);
    System.out.println("\nMinimum moves to reach end: " + minMoves + "\n");

    // Another example with more snakes and ladders
    System.out.println("--- Example: Board with More Snakes/Ladders ---\n");
    int[][] board2 = {
        {-1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1}
    };
    // Add ladder: 2 -> 15
    board2[5][1] = 15;
    // Add ladder: 5 -> 22
    board2[5][4] = 22;
    // Add snake: 17 -> 4
    board2[3][4] = 4;
    // Add ladder: 21 -> 33
    board2[2][2] = 33;

    printBoard(board2);

    int minMoves2 = snakesAndLadders(board2);
    System.out.println("\nMinimum moves to reach end: " + minMoves2 + "\n");

    // Simulate a game
    System.out.println("--- Simulating Game Play ---\n");
    simulateGame(board);
  }

  /**
   * Finds minimum moves using BFS.
   *
   * <p><b>LOGIC (BFS Shortest Path):</b>
   * <ol>
   *   <li>Treat board as a graph where each square connects to 6 next squares</li>
   *   <li>Snakes/ladders are instant teleports (no extra move cost)</li>
   *   <li>BFS guarantees shortest path in unweighted graph</li>
   *   <li>Convert board position to row/col considering Boustrophedon order</li>
   * </ol>
   *
   * <p><b>Boustrophedon Pattern:</b>
   * <pre>
   * Board numbered like this (for 6x6):
   * 36 35 34 33 32 31    ← right to left
   * 25 26 27 28 29 30    → left to right
   * 24 23 22 21 20 19    ← right to left
   * 13 14 15 16 17 18    → left to right
   * 12 11 10  9  8  7    ← right to left
   *  1  2  3  4  5  6    → left to right
   * </pre>
   *
   * <p><b>Time Complexity: O(n²)</b>
   * <br>Where n² is the board size. Each square visited once.
   * <br><i>Like finding shortest path in a maze - explore level by level
   * until reaching the exit.</i>
   *
   * <p><b>Space Complexity: O(n²)</b>
   * <br>For visited set and BFS queue.
   *
   * @param board the game board where board[i][j] = -1 (no snake/ladder) or destination
   * @return minimum number of moves, or -1 if impossible
   */
  public static int snakesAndLadders(int[][] board) {
    int n = board.length;
    int target = n * n;

    // BFS
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[] {1, 0});  // {position, moves}

    boolean[] visited = new boolean[target + 1];
    visited[1] = true;

    while (!queue.isEmpty()) {
      int[] current = queue.poll();
      int pos = current[0];
      int moves = current[1];

      // Try all dice rolls (1-6).
      for (int dice = 1; dice <= 6; dice++) {
        int nextPos = pos + dice;
        if (nextPos > target) {
          continue;
        }

        // Convert position to row/col.
        int[] coords = getCoordinates(nextPos, n);
        int boardValue = board[coords[0]][coords[1]];

        // Check for snake/ladder.
        if (boardValue != -1) {
          nextPos = boardValue;
        }

        // Check if reached target.
        if (nextPos == target) {
          return moves + 1;
        }

        if (!visited[nextPos]) {
          visited[nextPos] = true;
          queue.offer(new int[] {nextPos, moves + 1});
        }
      }
    }

    return -1;  // Cannot reach target.
  }

  /**
   * Converts 1-indexed position to board coordinates.
   */
  private static int[] getCoordinates(int pos, int n) {
    int row = n - 1 - (pos - 1) / n;
    int col;
    if ((n - 1 - row) % 2 == 0) {
      // Left to right row.
      col = (pos - 1) % n;
    } else {
      // Right to left row.
      col = n - 1 - (pos - 1) % n;
    }
    return new int[] {row, col};
  }

  /**
   * Simulates a game with random dice rolls.
   */
  private static void simulateGame(int[][] board) {
    int n = board.length;
    int target = n * n;
    int pos = 1;
    int moves = 0;
    Random random = new Random(42);  // Fixed seed for reproducibility.

    System.out.println("Starting at position 1");

    while (pos < target && moves < 20) {
      int dice = random.nextInt(6) + 1;
      int nextPos = pos + dice;

      if (nextPos > target) {
        System.out.println("  Roll " + dice + ": stays at " + pos + " (would exceed)");
        moves++;
        continue;
      }

      int[] coords = getCoordinates(nextPos, n);
      int boardValue = board[coords[0]][coords[1]];

      String action = "";
      if (boardValue != -1) {
        if (boardValue > nextPos) {
          action = " (Ladder to " + boardValue + "!)";
        } else {
          action = " (Snake to " + boardValue + "!)";
        }
        nextPos = boardValue;
      }

      System.out.println("  Roll " + dice + ": " + pos + " → " + nextPos + action);
      pos = nextPos;
      moves++;
    }

    if (pos >= target) {
      System.out.println("\nReached the end in " + moves + " moves!");
    } else {
      System.out.println("\nGame still in progress at position " + pos);
    }
  }

  private static void printBoard(int[][] board) {
    int n = board.length;
    System.out.println("Board layout:");
    for (int i = 0; i < n; i++) {
      System.out.print("  ");
      for (int j = 0; j < n; j++) {
        if (board[i][j] == -1) {
          System.out.print("  . ");
        } else {
          System.out.printf("%3d ", board[i][j]);
        }
      }
      System.out.println();
    }
  }
}
