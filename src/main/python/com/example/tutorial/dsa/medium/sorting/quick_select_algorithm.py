"""
QuickSelectAlgorithm
----------------------------------
This program finds the kth smallest/largest element using QuickSelect.
An efficient alternative to full sorting when you only need one element.

Problem Statement:
    Find the kth smallest element in an unsorted array in O(n) average time.

Real UseCase:
    In a credit card offers system:
    - Find median transaction without full sorting
    - Quickly identify kth highest spender
    - Select percentile values for analytics

Key Properties:
    - O(n) average time vs O(n log n) for full sort
    - In-place algorithm - modifies input array
    - Related to QuickSort's partitioning

Company Tags: Facebook, Amazon, Google
"""

import sys
import os
import random
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def quick_select(arr: List[int], k: int) -> int:
    """
    Finds the kth smallest element using QuickSelect algorithm.

    LOGIC (Partition-based Selection):
        1. Choose a pivot (randomly for better average case)
        2. Partition array: elements < pivot go left, > pivot go right
        3. If pivot is at position k-1, we found our answer
        4. Otherwise, recurse into the appropriate half

    Key Insight:
        After partitioning, we know exactly how many elements are smaller than pivot.
        We only need to recurse into ONE half, not both (unlike QuickSort).

    Example Walkthrough:
        arr = [7, 2, 1, 8, 6, 3], k = 3 (find 3rd smallest)

        Partition with pivot 3:
          Elements < 3: [2, 1]
          Pivot: 3 at index 2
          Elements > 3: [8, 6, 7]

        Pivot at index 2 = k-1? Yes! (k=3, index=2)

        Return 3

    Time Complexity: O(n) average, O(n^2) worst
        Each partition reduces problem by roughly half (average).
        Like finding a specific person in a crowd - divide crowd, check which side,
        repeat. Usually n + n/2 + n/4... = ~2n = O(n).

    Space Complexity: O(1)
        In-place partitioning, but input array is modified.

    Args:
        arr: The array to search (will be modified).
        k: Which smallest element to find (1-indexed).

    Returns:
        The kth smallest element.
    """
    if not arr or k < 1 or k > len(arr):
        raise ValueError("Invalid input")

    def partition(left: int, right: int, pivot_index: int) -> int:
        """Partitions array around pivot using Lomuto scheme."""
        pivot_value = arr[pivot_index]

        # Move pivot to end.
        arr[pivot_index], arr[right] = arr[right], arr[pivot_index]

        # Partition elements.
        store_index = left
        for i in range(left, right):
            if arr[i] < pivot_value:
                arr[i], arr[store_index] = arr[store_index], arr[i]
                store_index += 1

        # Move pivot to its final position.
        arr[store_index], arr[right] = arr[right], arr[store_index]

        return store_index

    def quick_select_helper(left: int, right: int, k_index: int) -> int:
        """Recursive helper for quick select."""
        # Base case: single element.
        if left == right:
            return arr[left]

        # Randomized pivot selection for better average case.
        pivot_index = random.randint(left, right)
        pivot_index = partition(left, right, pivot_index)

        # Check where pivot ended up.
        if k_index == pivot_index:
            return arr[k_index]
        elif k_index < pivot_index:
            return quick_select_helper(left, pivot_index - 1, k_index)
        else:
            return quick_select_helper(pivot_index + 1, right, k_index)

    return quick_select_helper(0, len(arr) - 1, k - 1)  # Convert to 0-indexed.


def main():
    """Main function to demonstrate the QuickSelectAlgorithm."""
    print("=== QuickSelectAlgorithm: Selection Algorithm Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate finding kth element
    print("--- Finding Kth Smallest Transaction ---\n")
    transactions = [350, 120, 500, 200, 90, 400, 180]
    print(f"Transactions: {transactions}")

    for k in range(1, 4):
        copy = transactions.copy()
        kth_smallest = quick_select(copy, k)
        print(f"  {k}th smallest: ${kth_smallest}")

    # Find median
    print("\n--- Finding Median ---\n")
    data = [7, 2, 1, 8, 6, 3, 5, 4]
    n = len(data)
    if n % 2 == 1:
        median = quick_select(data.copy(), (n + 1) // 2)
    else:
        left = quick_select(data.copy(), n // 2)
        right = quick_select(data.copy(), n // 2 + 1)
        median = (left + right) // 2
    print(f"Data: {data}")
    print(f"Median: {median}")


if __name__ == "__main__":
    main()
