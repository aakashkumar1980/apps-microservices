"""
SetMatrixZeroes
----------------------------------
This program sets entire row and column to zero if an element is zero.
The core problem solved here is Set Matrix Zeroes (LeetCode #73).

Problem Statement:
    Given an m x n matrix, if an element is 0, set its entire row and column to 0.
    Do it in-place.

Real UseCase:
    In a credit card offers system:
    - Nullify related data when invalid entry detected
    - Cascade data normalization in grids
    - Reset correlated metrics in dashboards

Company Tags: Facebook, Amazon, Microsoft

See: https://leetcode.com/problems/set-matrix-zeroes/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def set_zeroes(matrix: List[List[int]]) -> None:
    """
    Sets rows and columns to zero using first row/column as markers.

    LOGIC (O(1) Space with Markers):
        1. Use first row and column as markers for which rows/cols to zero
        2. Track if first row/column themselves need zeroing (separate flags)
        3. Mark rows/columns by setting markers to 0
        4. Zero out cells based on markers
        5. Finally, zero first row/column if needed

    Time Complexity: O(m x n)
        Two passes through the matrix.

    Space Complexity: O(1)
        Using first row/column as markers instead of extra arrays.

    Args:
        matrix: The matrix to modify (modified in-place).
    """
    if not matrix or not matrix[0]:
        return

    m, n = len(matrix), len(matrix[0])
    first_row_zero = any(matrix[0][j] == 0 for j in range(n))
    first_col_zero = any(matrix[i][0] == 0 for i in range(m))

    # Use first row/column as markers.
    for i in range(1, m):
        for j in range(1, n):
            if matrix[i][j] == 0:
                matrix[i][0] = 0  # Mark row.
                matrix[0][j] = 0  # Mark column.

    # Zero out cells based on markers.
    for i in range(1, m):
        for j in range(1, n):
            if matrix[i][0] == 0 or matrix[0][j] == 0:
                matrix[i][j] = 0

    # Zero first row if needed.
    if first_row_zero:
        for j in range(n):
            matrix[0][j] = 0

    # Zero first column if needed.
    if first_col_zero:
        for i in range(m):
            matrix[i][0] = 0


def main():
    """Main function to demonstrate the SetMatrixZeroes."""
    print("=== SetMatrixZeroes: In-place Zero Setting Demo ===\n")

    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    matrix = [[1, 1, 1], [1, 0, 1], [1, 1, 1]]
    print("Original:")
    for row in matrix:
        print(f"  {row}")
    print()

    set_zeroes(matrix)

    print("After setting zeroes:")
    for row in matrix:
        print(f"  {row}")


if __name__ == "__main__":
    main()
