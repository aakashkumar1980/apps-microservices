"""
RotatedArraySearch
----------------------------------
This program searches in a rotated sorted array using modified binary search.
The core problem solved here is Search in Rotated Sorted Array (LeetCode #33).

Problem Statement:
    Given a rotated sorted array and a target, return its index or -1 if not found.
    The array was originally sorted in ascending order, then rotated at some pivot.

Real UseCase:
    In a credit card offers system:
    - Search circular buffer of recent transactions
    - Find offer in a rotated schedule
    - Locate data in cyclic sorted structures

Examples:
    - Input: nums = [4,5,6,7,0,1,2], target = 0 -> Output: 4
    - Input: nums = [4,5,6,7,0,1,2], target = 3 -> Output: -1
    - Input: nums = [1], target = 0 -> Output: -1

Company Tags: Facebook, Amazon, Microsoft, LinkedIn (Very Popular!)

See: https://leetcode.com/problems/search-in-rotated-sorted-array/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def search(nums: List[int], target: int) -> int:
    """
    Searches for target in rotated sorted array using modified binary search.

    LOGIC (Modified Binary Search):
        1. Find mid, check if mid is target
        2. Determine which half is sorted (left or right)
        3. If target is in the sorted half, search there
        4. Otherwise, search the other half

    Key Insight:
        In a rotated array, at least one half (left or right of mid) is always sorted.
        We can easily check if target is in the sorted half.

    Example Walkthrough:
        nums = [4,5,6,7,0,1,2], target = 0

        left=0, right=6, mid=3, nums[mid]=7
        Left half [4,5,6,7] is sorted (nums[left]=4 <= nums[mid]=7)
        Target 0 not in [4,7], so search right half

        left=4, right=6, mid=5, nums[mid]=1
        Left half [0,1] is sorted
        Target 0 in [0,1]? Yes! Search left half

        left=4, right=4, mid=4, nums[mid]=0 = target!

        Return 4

    Time Complexity: O(log n)
        Binary search - we eliminate half the array each iteration.
        Like finding a page in a scrambled book - even if pages are out of order,
        you can still halve the search space each time.

    Space Complexity: O(1)
        Only using pointer variables.

    Args:
        nums: Rotated sorted array.
        target: Value to find.

    Returns:
        Index of target, or -1 if not found.
    """
    if not nums:
        return -1

    left, right = 0, len(nums) - 1

    while left <= right:
        mid = left + (right - left) // 2

        # Found target!
        if nums[mid] == target:
            return mid

        # Determine which half is sorted.
        if nums[left] <= nums[mid]:
            # Left half [left, mid] is sorted.
            if nums[left] <= target < nums[mid]:
                # Target is in sorted left half.
                right = mid - 1
            else:
                # Target is in right half.
                left = mid + 1
        else:
            # Right half [mid, right] is sorted.
            if nums[mid] < target <= nums[right]:
                # Target is in sorted right half.
                left = mid + 1
            else:
                # Target is in left half.
                right = mid - 1

    return -1


def main():
    """Main function to demonstrate the RotatedArraySearch."""
    print("=== RotatedArraySearch: Search in Rotated Array Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate with rotated transaction IDs
    print("--- Searching Rotated Transaction Buffer ---\n")
    transaction_ids = [400, 500, 600, 100, 200, 300]
    target_id = 200
    print(f"Rotated buffer: {transaction_ids}")
    print(f"Looking for ID: {target_id}")
    index = search(transaction_ids, target_id)
    print(f"Found at index: {index}\n")

    # Test cases
    print("--- Additional Examples ---\n")
    test_cases = [
        ([4, 5, 6, 7, 0, 1, 2], 0),
        ([4, 5, 6, 7, 0, 1, 2], 3),
        ([1], 0)
    ]

    for nums, target in test_cases:
        result = search(nums, target)
        print(f"Array: {nums}")
        print(f"Target: {target}")
        print(f"Index: {result}\n")


if __name__ == "__main__":
    main()
