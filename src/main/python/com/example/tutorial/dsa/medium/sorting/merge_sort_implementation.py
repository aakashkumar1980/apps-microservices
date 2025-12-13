"""
MergeSortImplementation
----------------------------------
This program demonstrates Merge Sort using divide and conquer.
Also solves Sort List (LeetCode #148) conceptually.

Problem Statement:
    Implement merge sort algorithm to sort an array in O(n log n) time.

Real UseCase:
    In a credit card offers system:
    - Sort large transaction datasets efficiently
    - Stable sort for maintaining original order of equal elements
    - External sorting for data that doesn't fit in memory

Key Properties:
    - Stable sort (maintains relative order of equal elements)
    - Guaranteed O(n log n) time - no worst case degradation
    - Parallelizable - subarrays can be sorted independently

Company Tags: Amazon, Microsoft, Google

See: https://leetcode.com/problems/sort-list/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def merge_sort(arr: List[int]) -> None:
    """
    Sorts array using merge sort algorithm.

    LOGIC (Divide and Conquer):
        1. Divide: Split array into two halves
        2. Conquer: Recursively sort each half
        3. Combine: Merge two sorted halves

    Example Walkthrough:
        [38, 27, 43, 3]

        Divide: [38, 27] and [43, 3]

        [38, 27] -> [38] and [27] -> merge -> [27, 38]
        [43, 3]  -> [43] and [3]  -> merge -> [3, 43]

        Merge [27, 38] and [3, 43]:
          Compare 27 vs 3 -> take 3 -> [3]
          Compare 27 vs 43 -> take 27 -> [3, 27]
          Compare 38 vs 43 -> take 38 -> [3, 27, 38]
          Take remaining 43 -> [3, 27, 38, 43]

        Result: [3, 27, 38, 43]

    Time Complexity: O(n log n)
        log n levels of recursion, each level does O(n) work merging.
        Like sorting a deck of cards - split into piles (log n splits),
        then merge piles back (n comparisons per level). Total = n x log n.

    Space Complexity: O(n)
        Temporary array needed for merging.
        Like needing a temporary table to merge two sorted piles of papers.

    Args:
        arr: The array to sort (modified in-place).
    """
    if not arr or len(arr) <= 1:
        return

    def merge_sort_helper(left: int, right: int) -> None:
        if left >= right:
            return  # Base case: single element is sorted.

        mid = left + (right - left) // 2

        # Recursively sort left and right halves.
        merge_sort_helper(left, mid)
        merge_sort_helper(mid + 1, right)

        # Merge the sorted halves.
        merge(left, mid, right)

    def merge(left: int, mid: int, right: int) -> None:
        # Create temp arrays for left and right halves.
        left_arr = arr[left:mid + 1]
        right_arr = arr[mid + 1:right + 1]

        i = j = 0  # Pointers for left and right arrays.
        k = left   # Pointer for merged array.

        # Merge by comparing elements.
        while i < len(left_arr) and j < len(right_arr):
            if left_arr[i] <= right_arr[j]:
                arr[k] = left_arr[i]
                i += 1
            else:
                arr[k] = right_arr[j]
                j += 1
            k += 1

        # Copy remaining elements.
        while i < len(left_arr):
            arr[k] = left_arr[i]
            i += 1
            k += 1

        while j < len(right_arr):
            arr[k] = right_arr[j]
            j += 1
            k += 1

    merge_sort_helper(0, len(arr) - 1)


def main():
    """Main function to demonstrate the MergeSortImplementation."""
    print("=== MergeSortImplementation: Merge Sort Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate sorting transaction amounts
    print("--- Sorting Transaction Amounts ---\n")
    transactions = [350, 120, 500, 200, 90, 400, 180]
    print(f"Original: {transactions}")
    merge_sort(transactions)
    print(f"Sorted:   {transactions}\n")

    # Test cases
    print("--- Additional Examples ---\n")
    test_arrays = [
        [38, 27, 43, 3, 9, 82, 10],
        [5, 4, 3, 2, 1],
        [1]
    ]

    for arr in test_arrays:
        original = arr.copy()
        print(f"Original: {original}")
        merge_sort(arr)
        print(f"Sorted:   {arr}\n")


if __name__ == "__main__":
    main()
