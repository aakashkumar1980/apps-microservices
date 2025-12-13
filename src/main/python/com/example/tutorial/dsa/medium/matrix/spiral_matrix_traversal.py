"""
SpiralMatrixTraversal
----------------------------------
This program traverses a matrix in spiral order.
The core problem solved here is Spiral Matrix (LeetCode #54).

Problem Statement:
    Given an m x n matrix, return all elements in spiral order.

Real UseCase:
    In a credit card offers system:
    - Generate dashboard display sequence
    - Traverse data grids for reporting
    - Create animation paths for UI elements

Examples:
    - Input: [[1,2,3],[4,5,6],[7,8,9]] -> Output: [1,2,3,6,9,8,7,4,5]

Company Tags: Amazon, Microsoft, Google

See: https://leetcode.com/problems/spiral-matrix/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def spiral_order(matrix: List[List[int]]) -> List[int]:
    """
    Returns elements in spiral order using boundary tracking.

    LOGIC (Four Boundaries):
        1. Maintain top, bottom, left, right boundaries
        2. Traverse right along top row, then shrink top
        3. Traverse down along right column, then shrink right
        4. Traverse left along bottom row, then shrink bottom
        5. Traverse up along left column, then shrink left
        6. Repeat until boundaries cross

    Time Complexity: O(m x n)
        Visit each element exactly once.

    Space Complexity: O(1)
        Only using boundary variables (output list not counted).

    Args:
        matrix: The input matrix.

    Returns:
        Elements in spiral order.
    """
    if not matrix or not matrix[0]:
        return []

    result = []
    top, bottom = 0, len(matrix) - 1
    left, right = 0, len(matrix[0]) - 1

    while top <= bottom and left <= right:
        # Traverse right along top row.
        for j in range(left, right + 1):
            result.append(matrix[top][j])
        top += 1

        # Traverse down along right column.
        for i in range(top, bottom + 1):
            result.append(matrix[i][right])
        right -= 1

        # Traverse left along bottom row (if still valid).
        if top <= bottom:
            for j in range(right, left - 1, -1):
                result.append(matrix[bottom][j])
            bottom -= 1

        # Traverse up along left column (if still valid).
        if left <= right:
            for i in range(bottom, top - 1, -1):
                result.append(matrix[i][left])
            left += 1

    return result


def main():
    """Main function to demonstrate the SpiralMatrixTraversal."""
    print("=== SpiralMatrixTraversal: Spiral Order Demo ===\n")

    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demo with 3x3 matrix
    print("--- 3x3 Matrix Spiral ---\n")
    matrix3 = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
    for row in matrix3:
        print(f"  {row}")
    print(f"\nSpiral order: {spiral_order(matrix3)}\n")

    # Demo with 3x4 matrix
    print("--- 3x4 Matrix Spiral ---\n")
    matrix34 = [[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]]
    for row in matrix34:
        print(f"  {row}")
    print(f"\nSpiral order: {spiral_order(matrix34)}")


if __name__ == "__main__":
    main()
