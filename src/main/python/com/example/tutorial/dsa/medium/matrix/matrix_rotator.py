"""
MatrixRotator
----------------------------------
This program rotates a matrix 90 degrees clockwise in-place.
The core problem solved here is Rotate Image (LeetCode #48).

Problem Statement:
    Given an n x n 2D matrix, rotate it 90 degrees clockwise in-place.

Real UseCase:
    In a credit card offers system:
    - Rotate dashboard grid layouts
    - Transform data visualization orientations
    - Reorient heatmap displays

Examples:
    - Input: [[1,2,3],[4,5,6],[7,8,9]] -> Output: [[7,4,1],[8,5,2],[9,6,3]]

Company Tags: Microsoft (35% interview frequency!), Amazon, Google

See: https://leetcode.com/problems/rotate-image/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def rotate(matrix: List[List[int]]) -> None:
    """
    Rotates n x n matrix 90 degrees clockwise in-place.

    LOGIC (Transpose + Reverse Rows):
        1. Transpose the matrix (swap [i][j] with [j][i])
        2. Reverse each row

    Why this works:
        Original:    Transpose:    Reverse rows:
        1 2 3        1 4 7         7 4 1
        4 5 6   ->   2 5 8    ->   8 5 2
        7 8 9        3 6 9         9 6 3

    Time Complexity: O(n^2)
        We visit each element twice (transpose + reverse).
        Like flipping a photo twice - once diagonally, once horizontally.

    Space Complexity: O(1)
        In-place rotation using swaps, no extra matrix needed.

    Args:
        matrix: n x n matrix to rotate (modified in-place).
    """
    if not matrix:
        return

    n = len(matrix)

    # Step 1: Transpose (swap [i][j] with [j][i]).
    for i in range(n):
        for j in range(i + 1, n):
            matrix[i][j], matrix[j][i] = matrix[j][i], matrix[i][j]

    # Step 2: Reverse each row.
    for i in range(n):
        matrix[i].reverse()


def rotate_counter_clockwise(matrix: List[List[int]]) -> None:
    """
    Rotates 90 degrees counter-clockwise.
    Method: Transpose + Reverse columns.

    Args:
        matrix: n x n matrix to rotate.
    """
    if not matrix:
        return

    n = len(matrix)

    # Step 1: Transpose.
    for i in range(n):
        for j in range(i + 1, n):
            matrix[i][j], matrix[j][i] = matrix[j][i], matrix[i][j]

    # Step 2: Reverse each column.
    for j in range(n):
        top, bottom = 0, n - 1
        while top < bottom:
            matrix[top][j], matrix[bottom][j] = matrix[bottom][j], matrix[top][j]
            top += 1
            bottom -= 1


def print_matrix(matrix: List[List[int]]) -> None:
    """Prints matrix in a readable format."""
    for row in matrix:
        print(f"  {row}")
    print()


def main():
    """Main function to demonstrate the MatrixRotator."""
    print("=== MatrixRotator: 90° Clockwise Rotation Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate matrix rotation
    print("--- Rotating Dashboard Grid ---\n")
    dashboard = [
        [1, 2, 3],
        [4, 5, 6],
        [7, 8, 9]
    ]

    print("Original:")
    print_matrix(dashboard)

    rotate(dashboard)

    print("After 90° clockwise rotation:")
    print_matrix(dashboard)

    # 4x4 example
    print("--- 4x4 Matrix Example ---\n")
    matrix4 = [
        [1, 2, 3, 4],
        [5, 6, 7, 8],
        [9, 10, 11, 12],
        [13, 14, 15, 16]
    ]

    print("Original:")
    print_matrix(matrix4)

    rotate(matrix4)

    print("After 90° clockwise rotation:")
    print_matrix(matrix4)


if __name__ == "__main__":
    main()
