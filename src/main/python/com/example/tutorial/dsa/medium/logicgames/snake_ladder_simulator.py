"""
SnakeLadderSimulator
----------------------------------
This program finds the minimum moves to win Snakes and Ladders.
The core problem solved here is Snakes and Ladders (LeetCode #909).

Problem Statement:
    Given a snakes and ladders board, find the minimum number of dice rolls
    required to reach the final square from square 1.

Real UseCase:
    In a credit card offers system:
    - Gamified reward progression systems
    - Customer journey optimization with shortcuts and penalties
    - Level-up mechanics for loyalty programs

Company Tags: Amazon, Google, Microsoft

See: https://leetcode.com/problems/snakes-and-ladders/
"""

import sys
import os
import random
from typing import List, Tuple
from collections import deque

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def get_coordinates(pos: int, n: int) -> Tuple[int, int]:
    """Converts 1-indexed position to board coordinates."""
    row = n - 1 - (pos - 1) // n
    if (n - 1 - row) % 2 == 0:
        # Left to right row.
        col = (pos - 1) % n
    else:
        # Right to left row.
        col = n - 1 - (pos - 1) % n
    return row, col


def snakes_and_ladders(board: List[List[int]]) -> int:
    """
    Finds minimum moves using BFS.

    LOGIC (BFS Shortest Path):
        1. Treat board as a graph where each square connects to 6 next squares
        2. Snakes/ladders are instant teleports (no extra move cost)
        3. BFS guarantees shortest path in unweighted graph
        4. Convert board position to row/col considering Boustrophedon order

    Boustrophedon Pattern:
        Board numbered like this (for 6x6):
        36 35 34 33 32 31    <- right to left
        25 26 27 28 29 30    -> left to right
        24 23 22 21 20 19    <- right to left
        13 14 15 16 17 18    -> left to right
        12 11 10  9  8  7    <- right to left
         1  2  3  4  5  6    -> left to right

    Time Complexity: O(n^2)
        Where n^2 is the board size. Each square visited once.
        Like finding shortest path in a maze - explore level by level
        until reaching the exit.

    Space Complexity: O(n^2)
        For visited set and BFS queue.

    Args:
        board: The game board where board[i][j] = -1 (no snake/ladder) or destination.

    Returns:
        Minimum number of moves, or -1 if impossible.
    """
    n = len(board)
    target = n * n

    # BFS
    queue = deque([(1, 0)])  # (position, moves)
    visited = {1}

    while queue:
        pos, moves = queue.popleft()

        # Try all dice rolls (1-6).
        for dice in range(1, 7):
            next_pos = pos + dice
            if next_pos > target:
                continue

            # Convert position to row/col.
            row, col = get_coordinates(next_pos, n)
            board_value = board[row][col]

            # Check for snake/ladder.
            if board_value != -1:
                next_pos = board_value

            # Check if reached target.
            if next_pos == target:
                return moves + 1

            if next_pos not in visited:
                visited.add(next_pos)
                queue.append((next_pos, moves + 1))

    return -1  # Cannot reach target.


def simulate_game(board: List[List[int]]) -> None:
    """Simulates a game with random dice rolls."""
    n = len(board)
    target = n * n
    pos = 1
    moves = 0
    random.seed(42)  # Fixed seed for reproducibility.

    print("Starting at position 1")

    while pos < target and moves < 20:
        dice = random.randint(1, 6)
        next_pos = pos + dice

        if next_pos > target:
            print(f"  Roll {dice}: stays at {pos} (would exceed)")
            moves += 1
            continue

        row, col = get_coordinates(next_pos, n)
        board_value = board[row][col]

        action = ""
        if board_value != -1:
            if board_value > next_pos:
                action = f" (Ladder to {board_value}!)"
            else:
                action = f" (Snake to {board_value}!)"
            next_pos = board_value

        print(f"  Roll {dice}: {pos} -> {next_pos}{action}")
        pos = next_pos
        moves += 1

    if pos >= target:
        print(f"\nReached the end in {moves} moves!")
    else:
        print(f"\nGame still in progress at position {pos}")


def print_board(board: List[List[int]]) -> None:
    """Prints the board layout."""
    print("Board layout:")
    for row in board:
        print("  " + " ".join(f"{val:3d}" if val != -1 else "  ." for val in row))


def main():
    """Main function to demonstrate the SnakeLadderSimulator."""
    print("=== SnakeLadderSimulator: Snakes and Ladders Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Example board (6x6 = 36 squares)
    print("--- Example: 6x6 Board ---\n")

    board = [
        [-1, -1, -1, -1, -1, -1],
        [-1, -1, -1, -1, -1, -1],
        [-1, -1, -1, -1, -1, -1],
        [-1, 35, -1, -1, 13, -1],
        [-1, -1, -1, -1, -1, -1],
        [-1, 15, -1, -1, -1, -1]
    ]

    print_board(board)

    min_moves = snakes_and_ladders([row[:] for row in board])
    print(f"\nMinimum moves to reach end: {min_moves}\n")

    # Another example
    print("--- Example: Board with More Snakes/Ladders ---\n")
    board2 = [[-1] * 6 for _ in range(6)]
    # Add ladder: 2 -> 15
    board2[5][1] = 15
    # Add ladder: 5 -> 22
    board2[5][4] = 22
    # Add snake: 17 -> 4
    board2[3][4] = 4
    # Add ladder: 21 -> 33
    board2[2][2] = 33

    print_board(board2)

    min_moves2 = snakes_and_ladders([row[:] for row in board2])
    print(f"\nMinimum moves to reach end: {min_moves2}\n")

    # Simulate a game
    print("--- Simulating Game Play ---\n")
    simulate_game(board)


if __name__ == "__main__":
    main()
