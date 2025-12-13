"""
RangeSumQuery2D
----------------------------------
This program implements 2D range sum query using prefix sums.
The core problem solved here is Range Sum Query 2D - Immutable (LeetCode #304).

Problem Statement:
    Given a 2D matrix, handle multiple queries of the form: sum of elements inside
    the rectangle defined by upper left corner (row1, col1) and lower right corner (row2, col2).

Real UseCase:
    In a credit card offers system:
    - Sum transactions in a geographic region
    - Calculate total rewards for a merchant category range
    - Aggregate spending in time windows

Key Insight:
    Pre-compute prefix sums to answer any rectangular sum query in O(1) time.

Company Tags: Amazon, Google, Facebook

See: https://leetcode.com/problems/range-sum-query-2d-immutable/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


class NumMatrix:
    """
    NumMatrix class for 2D range sum queries using prefix sums.

    LOGIC (2D Prefix Sum):
        1. Build prefix sum matrix where prefix[i][j] = sum of all elements
           in rectangle (0,0) to (i-1,j-1)
        2. To get sum of rectangle (r1,c1) to (r2,c2):
        3. Use inclusion-exclusion:
           prefix[r2+1][c2+1] - prefix[r1][c2+1] - prefix[r2+1][c1] + prefix[r1][c1]

    Visual Explanation:
        To find sum of shaded region:
        +---+---+---+
        | A |   B   |
        +---+-------+
        |   |///////|
        | C |/QUERY/|
        |   |///////|
        +---+-------+

        Query = Total - B - C + A
              = prefix[r2+1][c2+1] - prefix[r1][c2+1] - prefix[r2+1][c1] + prefix[r1][c1]

        We add A back because it was subtracted twice (once in B, once in C).

    Time Complexity:
        Constructor: O(m x n) - build prefix sum matrix
        sumRegion: O(1) - constant time lookup
        Like pre-calculating running totals for a spreadsheet - initial work,
        but then any sum becomes instant.

    Space Complexity: O(m x n)
        For the prefix sum matrix.
    """

    def __init__(self, matrix: List[List[int]]):
        """
        Constructs the prefix sum matrix.

        Args:
            matrix: The input matrix.
        """
        rows = len(matrix)
        cols = len(matrix[0])

        # prefix[i][j] = sum of all elements in rectangle (0,0) to (i-1,j-1).
        # We use (rows+1) x (cols+1) to handle edge cases cleanly.
        self.prefix = [[0] * (cols + 1) for _ in range(rows + 1)]

        for r in range(1, rows + 1):
            for c in range(1, cols + 1):
                # Sum = current element + left prefix + top prefix - overlap (top-left)
                self.prefix[r][c] = (
                    matrix[r - 1][c - 1]
                    + self.prefix[r - 1][c]
                    + self.prefix[r][c - 1]
                    - self.prefix[r - 1][c - 1]
                )

    def sum_region(self, row1: int, col1: int, row2: int, col2: int) -> int:
        """
        Returns the sum of elements in the rectangle (row1, col1) to (row2, col2).

        Args:
            row1: Top-left row.
            col1: Top-left column.
            row2: Bottom-right row.
            col2: Bottom-right column.

        Returns:
            Sum of elements in the rectangle.
        """
        # Inclusion-exclusion principle.
        return (
            self.prefix[row2 + 1][col2 + 1]
            - self.prefix[row1][col2 + 1]
            - self.prefix[row2 + 1][col1]
            + self.prefix[row1][col1]
        )

    def print_prefix_sum(self) -> None:
        """Helper to print the prefix sum matrix."""
        print("Prefix Sum Matrix (1-indexed):")
        for row in self.prefix:
            print("  " + " ".join(f"{val:3d}" for val in row))


def main():
    """Main function to demonstrate the RangeSumQuery2D."""
    print("=== RangeSumQuery2D: 2D Prefix Sum Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Create sample matrix representing transaction amounts by region/time
    matrix = [
        [3, 0, 1, 4, 2],
        [5, 6, 3, 2, 1],
        [1, 2, 0, 1, 5],
        [4, 1, 0, 1, 7],
        [1, 0, 3, 0, 5]
    ]

    print("Transaction Matrix:")
    for row in matrix:
        print(f"  {row}")
    print()

    # Build the NumMatrix object
    num_matrix = NumMatrix(matrix)

    # Demonstrate range sum queries
    print("--- Range Sum Queries ---\n")

    # Query 1: Sum of region (2,1) to (4,3)
    sum1 = num_matrix.sum_region(2, 1, 4, 3)
    print(f"sum_region(2,1,4,3) = {sum1}")
    print("  Region: rows 2-4, cols 1-3")
    print(f"  Elements: 2+0+1 + 1+0+1 + 0+3+0 = {sum1}\n")

    # Query 2: Sum of region (1,1) to (2,2)
    sum2 = num_matrix.sum_region(1, 1, 2, 2)
    print(f"sum_region(1,1,2,2) = {sum2}")
    print("  Region: rows 1-2, cols 1-2")
    print(f"  Elements: 6+3 + 2+0 = {sum2}\n")

    # Query 3: Sum of region (1,2) to (2,4)
    sum3 = num_matrix.sum_region(1, 2, 2, 4)
    print(f"sum_region(1,2,2,4) = {sum3}")
    print("  Region: rows 1-2, cols 2-4")
    print(f"  Elements: 3+2+1 + 0+1+5 = {sum3}\n")

    # Show the prefix sum matrix
    print("--- Prefix Sum Matrix (Internal) ---\n")
    num_matrix.print_prefix_sum()


if __name__ == "__main__":
    main()
