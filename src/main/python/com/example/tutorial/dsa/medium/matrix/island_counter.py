"""
IslandCounter
----------------------------------
This program counts the number of islands in a 2D grid.
The core problem solved here is Number of Islands (LeetCode #200).

Problem Statement:
    Given an m x n 2D binary grid which represents a map of '1's (land) and '0's (water),
    return the number of islands. An island is surrounded by water and is formed by
    connecting adjacent lands horizontally or vertically.

Real UseCase:
    In a credit card offers system:
    - Identify clusters of related transactions
    - Group connected merchant locations
    - Find connected fraud patterns in transaction matrix

Company Tags: Amazon, Microsoft, Facebook, Google

See: https://leetcode.com/problems/number-of-islands/
"""

import sys
import os
from typing import List
from collections import deque

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers

# Direction arrays for 4-directional movement (up, down, left, right).
DIRECTIONS = [(-1, 0), (1, 0), (0, -1), (0, 1)]


def num_islands_dfs(grid: List[List[str]]) -> int:
    """
    Counts islands using DFS (Depth-First Search).

    LOGIC (DFS Flood Fill):
        1. Scan grid cell by cell
        2. When '1' found, increment count and "sink" the entire island
        3. Sinking = DFS to mark all connected land as visited ('0')
        4. Continue scanning for next unvisited island

    Example Walkthrough:
        Grid:          After sinking island 1:
        1 1 0          0 0 0
        1 0 0    ->    0 0 0
        0 0 1          0 0 1

        Found '1' at (0,0), sink it (DFS marks all connected as '0')
        Continue scan, find '1' at (2,2), sink it
        Total islands: 2

    Time Complexity: O(m x n)
        Each cell is visited at most once.
        Like searching a map for countries - once you've colored a country,
        you don't need to visit those cells again.

    Space Complexity: O(m x n)
        Worst case recursion depth if entire grid is one island.
        Can be O(min(m, n)) with iterative BFS approach.

    Args:
        grid: The 2D grid of '1's (land) and '0's (water).

    Returns:
        Number of islands.
    """
    if not grid or not grid[0]:
        return 0

    rows, cols = len(grid), len(grid[0])
    islands = 0

    def dfs(r: int, c: int) -> None:
        """DFS helper to sink an island by marking all connected land as water."""
        # Boundary and water check.
        if r < 0 or r >= rows or c < 0 or c >= cols or grid[r][c] == '0':
            return

        # Mark as visited (sink the land).
        grid[r][c] = '0'

        # Explore all 4 directions.
        for dr, dc in DIRECTIONS:
            dfs(r + dr, c + dc)

    for r in range(rows):
        for c in range(cols):
            if grid[r][c] == '1':
                islands += 1
                dfs(r, c)  # Sink the island.

    return islands


def num_islands_bfs(grid: List[List[str]]) -> int:
    """
    Counts islands using BFS (Breadth-First Search).

    LOGIC (BFS Flood Fill):
        1. Scan grid cell by cell
        2. When '1' found, increment count and BFS to sink entire island
        3. BFS uses queue to visit neighbors level by level
        4. Continue scanning for next unvisited island

    Time Complexity: O(m x n)
        Each cell visited at most once.

    Space Complexity: O(min(m, n))
        Queue size bounded by the smaller dimension.

    Args:
        grid: The 2D grid of '1's (land) and '0's (water).

    Returns:
        Number of islands.
    """
    if not grid or not grid[0]:
        return 0

    rows, cols = len(grid), len(grid[0])
    islands = 0

    def bfs(start_r: int, start_c: int) -> None:
        """BFS helper to sink an island by marking all connected land as water."""
        queue = deque([(start_r, start_c)])
        grid[start_r][start_c] = '0'  # Mark as visited.

        while queue:
            r, c = queue.popleft()

            # Explore all 4 directions.
            for dr, dc in DIRECTIONS:
                nr, nc = r + dr, c + dc

                # Check bounds and if it's land.
                if 0 <= nr < rows and 0 <= nc < cols and grid[nr][nc] == '1':
                    grid[nr][nc] = '0'  # Mark as visited.
                    queue.append((nr, nc))

    for r in range(rows):
        for c in range(cols):
            if grid[r][c] == '1':
                islands += 1
                bfs(r, c)  # Sink the island.

    return islands


def print_grid(grid: List[List[str]]) -> None:
    """Helper method to print a grid."""
    print("Grid:")
    for row in grid:
        print("  " + " ".join(row))


def main():
    """Main function to demonstrate the IslandCounter."""
    print("=== IslandCounter: Connected Components Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate island counting
    print("--- Counting Islands (DFS approach) ---\n")

    grid1 = [
        ['1', '1', '1', '1', '0'],
        ['1', '1', '0', '1', '0'],
        ['1', '1', '0', '0', '0'],
        ['0', '0', '0', '0', '0']
    ]
    print_grid(grid1)
    print(f"Number of islands (DFS): {num_islands_dfs([row[:] for row in grid1])}\n")

    grid2 = [
        ['1', '1', '0', '0', '0'],
        ['1', '1', '0', '0', '0'],
        ['0', '0', '1', '0', '0'],
        ['0', '0', '0', '1', '1']
    ]
    print_grid(grid2)
    print(f"Number of islands (DFS): {num_islands_dfs([row[:] for row in grid2])}\n")

    # BFS approach
    print("--- Counting Islands (BFS approach) ---\n")
    grid3 = [
        ['1', '0', '1', '0', '1'],
        ['0', '1', '0', '1', '0'],
        ['1', '0', '1', '0', '1']
    ]
    print_grid(grid3)
    print(f"Number of islands (BFS): {num_islands_bfs([row[:] for row in grid3])}\n")


if __name__ == "__main__":
    main()
