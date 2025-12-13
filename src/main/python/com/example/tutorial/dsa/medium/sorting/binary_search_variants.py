"""
BinarySearchVariants
----------------------------------
This program demonstrates binary search variations for finding boundaries.
The core problem solved here is Find First and Last Position (LeetCode #34).

Problem Statement:
    Given a sorted array of integers and a target value, find the starting and
    ending position of the target. If not found, return [-1, -1].

Real UseCase:
    In a credit card offers system:
    - Find range of offers within a price bracket
    - Locate all transactions within a date range
    - Search for reward tier boundaries

Examples:
    - Input: nums = [5,7,7,8,8,10], target = 8 -> Output: [3,4]
    - Input: nums = [5,7,7,8,8,10], target = 6 -> Output: [-1,-1]
    - Input: nums = [], target = 0 -> Output: [-1,-1]

Company Tags: Facebook, Amazon, LinkedIn, Microsoft

See: https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def search_range(nums: List[int], target: int) -> List[int]:
    """
    Finds first and last position of target using two binary searches.

    LOGIC (Modified Binary Search):
        1. First binary search: find leftmost (first) occurrence
        2. Second binary search: find rightmost (last) occurrence
        3. Key modification: when target found, continue searching to find boundary

    Example Walkthrough (Finding First 8):
        nums = [5, 7, 7, 8, 8, 10], target = 8

        left=0, right=5, mid=2, nums[2]=7 < 8 -> left=3
        left=3, right=5, mid=4, nums[4]=8 = 8 -> found! but continue left
                                                 right=3, result=4
        left=3, right=3, mid=3, nums[3]=8 = 8 -> found! continue left
                                                 right=2, result=3
        left=3, right=2 -> exit

        First position: 3

    Time Complexity: O(log n)
        Two binary searches, each O(log n).
        Like finding a word in a dictionary - you flip to the middle,
        decide which half to search, repeat. With n pages, ~log(n) flips.

    Space Complexity: O(1)
        Only using a few pointer variables.
        Like using bookmarks - just a few fingers to track position.

    Args:
        nums: Sorted array of integers.
        target: Value to find.

    Returns:
        List of [first position, last position], or [-1,-1] if not found.
    """
    if not nums:
        return [-1, -1]

    def find_first() -> int:
        """Finds the first (leftmost) occurrence of target."""
        left, right = 0, len(nums) - 1
        result = -1

        while left <= right:
            mid = left + (right - left) // 2

            if nums[mid] == target:
                result = mid           # Found, but keep searching left.
                right = mid - 1        # Look for earlier occurrence.
            elif nums[mid] < target:
                left = mid + 1
            else:
                right = mid - 1

        return result

    def find_last() -> int:
        """Finds the last (rightmost) occurrence of target."""
        left, right = 0, len(nums) - 1
        result = -1

        while left <= right:
            mid = left + (right - left) // 2

            if nums[mid] == target:
                result = mid           # Found, but keep searching right.
                left = mid + 1         # Look for later occurrence.
            elif nums[mid] < target:
                left = mid + 1
            else:
                right = mid - 1

        return result

    # Find first occurrence.
    first = find_first()

    # If first not found, target doesn't exist.
    if first == -1:
        return [-1, -1]

    # Find last occurrence.
    last = find_last()

    return [first, last]


def binary_search(nums: List[int], target: int) -> int:
    """
    Standard binary search - returns index if found, -1 otherwise.

    Args:
        nums: Sorted array.
        target: Value to find.

    Returns:
        Index of target, or -1.
    """
    left, right = 0, len(nums) - 1

    while left <= right:
        mid = left + (right - left) // 2  # Avoids integer overflow.

        if nums[mid] == target:
            return mid
        elif nums[mid] < target:
            left = mid + 1
        else:
            right = mid - 1

    return -1


def main():
    """Main function to demonstrate the BinarySearchVariants."""
    print("=== BinarySearchVariants: Binary Search Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate finding offer range in sorted price list
    print("--- Finding Price Range in Sorted Offers ---\n")
    prices = [50, 100, 100, 100, 150, 200, 200, 250]
    target_price = 100
    print(f"Sorted prices: {prices}")
    print(f"Target price: ${target_price}")
    range_result = search_range(prices, target_price)
    print(f"Range of ${target_price} offers: {range_result}\n")

    # Test cases
    print("--- Additional Examples ---\n")
    test_cases = [
        ([5, 7, 7, 8, 8, 10], 8),
        ([5, 7, 7, 8, 8, 10], 6),
        ([], 0)
    ]

    for nums, target in test_cases:
        result = search_range(nums, target)
        print(f"Array: {nums}")
        print(f"Target: {target}")
        print(f"Range: {result}\n")


if __name__ == "__main__":
    main()
