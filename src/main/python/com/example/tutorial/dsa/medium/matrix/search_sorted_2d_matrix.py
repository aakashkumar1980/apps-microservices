"""
SearchSorted2DMatrix
----------------------------------
This program searches for a target in a sorted 2D matrix.
The core problem solved here is Search a 2D Matrix (LeetCode #74).

Problem Statement:
    Search for a target in an m x n matrix where each row is sorted and the first
    integer of each row is greater than the last integer of the previous row.

Company Tags: Amazon, Microsoft, Facebook

See: https://leetcode.com/problems/search-a-2d-matrix/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def search_matrix(matrix: List[List[int]], target: int) -> bool:
    """
    Searches for target using binary search treating matrix as 1D array.

    LOGIC (Flattened Binary Search):
        1. Treat m x n matrix as sorted 1D array of size m*n
        2. Convert 1D index to 2D: row = idx // n, col = idx % n
        3. Standard binary search

    Time Complexity: O(log(m x n))
    Space Complexity: O(1)
    """
    if not matrix or not matrix[0]:
        return False

    m, n = len(matrix), len(matrix[0])
    left, right = 0, m * n - 1

    while left <= right:
        mid = left + (right - left) // 2
        mid_value = matrix[mid // n][mid % n]

        if mid_value == target:
            return True
        elif mid_value < target:
            left = mid + 1
        else:
            right = mid - 1

    return False


def main():
    print("=== SearchSorted2DMatrix: Binary Search in 2D Demo ===\n")

    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    matrix = [[1, 3, 5, 7], [10, 11, 16, 20], [23, 30, 34, 60]]

    targets = [3, 13, 60, 1]
    for target in targets:
        found = search_matrix(matrix, target)
        print(f"Search {target}: {'Found' if found else 'Not found'}")


if __name__ == "__main__":
    main()
