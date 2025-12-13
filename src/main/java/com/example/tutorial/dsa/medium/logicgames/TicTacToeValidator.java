package com.example.tutorial.dsa.medium.logicgames;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TicTacToeValidator
 * ----------------------------------
 * <p>This program implements a Tic-Tac-Toe game with O(1) winner detection.
 * The core problem solved here is Design Tic-Tac-Toe (LeetCode #348).
 *
 * <p><b>Problem Statement:</b>
 * Design a Tic-Tac-Toe game that is played between two players on an n x n grid.
 * Determine the winner in O(1) time after each move.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Gamified reward systems for customer engagement</li>
 *   <li>Promotional game mechanics</li>
 *   <li>State validation for multi-step processes</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Facebook, Google, Microsoft
 *
 * @see <a href="https://leetcode.com/problems/design-tic-tac-toe/">LeetCode 348 - Design Tic-Tac-Toe</a>
 */
@Component
public class TicTacToeValidator implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(TicTacToeValidator.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== TicTacToeValidator: Tic-Tac-Toe Game Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Play a game
    System.out.println("--- Playing Tic-Tac-Toe (3x3) ---\n");
    TicTacToe game = new TicTacToe(3);

    // Simulate moves
    int[][] moves = {
        {0, 0, 1},  // Player 1 at (0,0)
        {0, 2, 2},  // Player 2 at (0,2)
        {2, 2, 1},  // Player 1 at (2,2)
        {1, 1, 2},  // Player 2 at (1,1)
        {2, 0, 1},  // Player 1 at (2,0)
        {1, 0, 2},  // Player 2 at (1,0)
        {2, 1, 1},  // Player 1 at (2,1) - wins!
    };

    for (int[] move : moves) {
      int row = move[0];
      int col = move[1];
      int player = move[2];

      int result = game.move(row, col, player);
      System.out.println("Player " + player + " moves to (" + row + "," + col + ")");
      game.printBoard();

      if (result != 0) {
        System.out.println("\n*** Player " + result + " wins! ***\n");
        break;
      }
      System.out.println();
    }

    // Another game with diagonal win
    System.out.println("--- Another Game (Diagonal Win) ---\n");
    TicTacToe game2 = new TicTacToe(3);

    int[][] moves2 = {
        {0, 0, 1},  // X
        {0, 1, 2},  // O
        {1, 1, 1},  // X (center)
        {0, 2, 2},  // O
        {2, 2, 1},  // X (diagonal win!)
    };

    for (int[] move : moves2) {
      int result = game2.move(move[0], move[1], move[2]);
      System.out.println("Player " + move[2] + " moves to (" + move[0] + "," + move[1] + ")");
      game2.printBoard();

      if (result != 0) {
        System.out.println("\n*** Player " + result + " wins (diagonal)! ***\n");
        break;
      }
      System.out.println();
    }
  }

  /**
   * TicTacToe class with O(1) winner detection.
   *
   * <p><b>LOGIC (Counter-based Winner Detection):</b>
   * <ol>
   *   <li>Track row sums, column sums, and diagonal sums</li>
   *   <li>Player 1 adds +1, Player 2 adds -1</li>
   *   <li>If any sum reaches +n or -n, that player wins</li>
   *   <li>No need to scan the board - O(1) check!</li>
   * </ol>
   *
   * <p><b>Example Walkthrough (3x3):</b>
   * <pre>
   * Player 1 moves: (0,0), (1,1), (2,2) - main diagonal
   *
   * After (0,0): rows[0]=1, cols[0]=1, diag=1
   * After (1,1): rows[1]=1, cols[1]=1, diag=2
   * After (2,2): rows[2]=1, cols[2]=1, diag=3 → Win!
   *
   * diag == n (3), so Player 1 wins.
   * </pre>
   *
   * <p><b>Time Complexity: O(1)</b> per move.
   * <br><i>Like tracking vote counts - just increment counters, don't scan all votes.</i>
   *
   * <p><b>Space Complexity: O(n)</b>
   * <br>Arrays for row and column sums.
   */
  public static class TicTacToe {
    private final int n;
    private final int[] rows;     // Sum of marks in each row.
    private final int[] cols;     // Sum of marks in each column.
    private int diag;             // Sum of marks in main diagonal.
    private int antiDiag;         // Sum of marks in anti-diagonal.
    private final char[][] board; // For display purposes.

    public TicTacToe(int n) {
      this.n = n;
      this.rows = new int[n];
      this.cols = new int[n];
      this.diag = 0;
      this.antiDiag = 0;
      this.board = new char[n][n];
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          board[i][j] = '.';
        }
      }
    }

    /**
     * Makes a move and returns winner (0 if no winner yet).
     *
     * @param row the row (0-indexed)
     * @param col the column (0-indexed)
     * @param player 1 or 2
     * @return 0 if no winner, otherwise winning player number
     */
    public int move(int row, int col, int player) {
      // Player 1 adds +1, Player 2 adds -1.
      int delta = (player == 1) ? 1 : -1;

      rows[row] += delta;
      cols[col] += delta;

      // Check diagonals.
      if (row == col) {
        diag += delta;
      }
      if (row + col == n - 1) {
        antiDiag += delta;
      }

      // Update board for display.
      board[row][col] = (player == 1) ? 'X' : 'O';

      // Check for winner.
      if (Math.abs(rows[row]) == n
          || Math.abs(cols[col]) == n
          || Math.abs(diag) == n
          || Math.abs(antiDiag) == n) {
        return player;
      }

      return 0;
    }

    /**
     * Prints the current board state.
     */
    public void printBoard() {
      for (int i = 0; i < n; i++) {
        System.out.print("  ");
        for (int j = 0; j < n; j++) {
          System.out.print(board[i][j]);
          if (j < n - 1) {
            System.out.print(" | ");
          }
        }
        System.out.println();
        if (i < n - 1) {
          System.out.println("  " + "-".repeat(4 * n - 3));
        }
      }
    }
  }
}
