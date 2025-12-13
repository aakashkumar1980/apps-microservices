"""
TicTacToeValidator
----------------------------------
This program implements a Tic-Tac-Toe game with O(1) winner detection.
The core problem solved here is Design Tic-Tac-Toe (LeetCode #348).

Problem Statement:
    Design a Tic-Tac-Toe game that is played between two players on an n x n grid.
    Determine the winner in O(1) time after each move.

Real UseCase:
    In a credit card offers system:
    - Gamified reward systems for customer engagement
    - Promotional game mechanics
    - State validation for multi-step processes

Company Tags: Amazon, Facebook, Google, Microsoft

See: https://leetcode.com/problems/design-tic-tac-toe/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


class TicTacToe:
    """
    TicTacToe class with O(1) winner detection.

    LOGIC (Counter-based Winner Detection):
        1. Track row sums, column sums, and diagonal sums
        2. Player 1 adds +1, Player 2 adds -1
        3. If any sum reaches +n or -n, that player wins
        4. No need to scan the board - O(1) check!

    Example Walkthrough (3x3):
        Player 1 moves: (0,0), (1,1), (2,2) - main diagonal

        After (0,0): rows[0]=1, cols[0]=1, diag=1
        After (1,1): rows[1]=1, cols[1]=1, diag=2
        After (2,2): rows[2]=1, cols[2]=1, diag=3 -> Win!

        diag == n (3), so Player 1 wins.

    Time Complexity: O(1) per move.
        Like tracking vote counts - just increment counters, don't scan all votes.

    Space Complexity: O(n)
        Arrays for row and column sums.
    """

    def __init__(self, n: int):
        """
        Initialize Tic-Tac-Toe board.

        Args:
            n: Size of the board (n x n).
        """
        self.n = n
        self.rows = [0] * n      # Sum of marks in each row.
        self.cols = [0] * n      # Sum of marks in each column.
        self.diag = 0            # Sum of marks in main diagonal.
        self.anti_diag = 0       # Sum of marks in anti-diagonal.
        self.board = [['.' for _ in range(n)] for _ in range(n)]  # For display.

    def move(self, row: int, col: int, player: int) -> int:
        """
        Makes a move and returns winner (0 if no winner yet).

        Args:
            row: The row (0-indexed).
            col: The column (0-indexed).
            player: 1 or 2.

        Returns:
            0 if no winner, otherwise winning player number.
        """
        # Player 1 adds +1, Player 2 adds -1.
        delta = 1 if player == 1 else -1

        self.rows[row] += delta
        self.cols[col] += delta

        # Check diagonals.
        if row == col:
            self.diag += delta
        if row + col == self.n - 1:
            self.anti_diag += delta

        # Update board for display.
        self.board[row][col] = 'X' if player == 1 else 'O'

        # Check for winner.
        if (abs(self.rows[row]) == self.n or
                abs(self.cols[col]) == self.n or
                abs(self.diag) == self.n or
                abs(self.anti_diag) == self.n):
            return player

        return 0

    def print_board(self) -> None:
        """Prints the current board state."""
        for i in range(self.n):
            print("  " + " | ".join(self.board[i]))
            if i < self.n - 1:
                print("  " + "-" * (4 * self.n - 3))


def main():
    """Main function to demonstrate the TicTacToeValidator."""
    print("=== TicTacToeValidator: Tic-Tac-Toe Game Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Play a game
    print("--- Playing Tic-Tac-Toe (3x3) ---\n")
    game = TicTacToe(3)

    # Simulate moves
    moves = [
        (0, 0, 1),  # Player 1 at (0,0)
        (0, 2, 2),  # Player 2 at (0,2)
        (2, 2, 1),  # Player 1 at (2,2)
        (1, 1, 2),  # Player 2 at (1,1)
        (2, 0, 1),  # Player 1 at (2,0)
        (1, 0, 2),  # Player 2 at (1,0)
        (2, 1, 1),  # Player 1 at (2,1) - wins!
    ]

    for row, col, player in moves:
        result = game.move(row, col, player)
        print(f"Player {player} moves to ({row},{col})")
        game.print_board()

        if result != 0:
            print(f"\n*** Player {result} wins! ***\n")
            break
        print()

    # Another game with diagonal win
    print("--- Another Game (Diagonal Win) ---\n")
    game2 = TicTacToe(3)

    moves2 = [
        (0, 0, 1),  # X
        (0, 1, 2),  # O
        (1, 1, 1),  # X (center)
        (0, 2, 2),  # O
        (2, 2, 1),  # X (diagonal win!)
    ]

    for row, col, player in moves2:
        result = game2.move(row, col, player)
        print(f"Player {player} moves to ({row},{col})")
        game2.print_board()

        if result != 0:
            print(f"\n*** Player {result} wins (diagonal)! ***\n")
            break
        print()


if __name__ == "__main__":
    main()
