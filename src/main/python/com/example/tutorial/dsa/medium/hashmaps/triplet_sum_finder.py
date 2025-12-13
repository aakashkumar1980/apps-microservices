"""
TripletSumFinder
----------------------------------
This program finds all unique triplets that sum to zero using sorting + two-pointer.
The core problem solved here is 3Sum (LeetCode #15).

Problem Statement:
    Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]]
    such that i != j, i != k, j != k, and nums[i] + nums[j] + nums[k] == 0.
    The solution must not contain duplicate triplets.

Real UseCase:
    In a credit card offers system:
    - Find three transactions that balance to zero (refunds, charges, adjustments)
    - Identify offer combinations with net-zero cost impact
    - Reconcile multi-party transactions

Examples:
    - Input: [-1,0,1,2,-1,-4] -> Output: [[-1,-1,2],[-1,0,1]]
    - Input: [0,1,1] -> Output: [] (no triplets sum to 0)
    - Input: [0,0,0] -> Output: [[0,0,0]]

Company Tags: Amazon, Facebook, Microsoft, Bloomberg

See: https://leetcode.com/problems/3sum/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def three_sum(nums: List[int]) -> List[List[int]]:
    """
    Finds all unique triplets that sum to zero using sorting + two-pointer technique.

    LOGIC (Sort + Two Pointer):
        1. Sort the array - enables two-pointer technique and easy duplicate skipping
        2. Fix first element (nums[i]), then find pairs that sum to -nums[i]
        3. Use two pointers (left, right) on remaining elements
        4. If sum too small, move left pointer right
        5. If sum too big, move right pointer left
        6. Skip duplicates to avoid duplicate triplets

    Example Walkthrough:
        nums = [-1, 0, 1, 2, -1, -4]
        After sorting: [-4, -1, -1, 0, 1, 2]

        i=0: fix -4, need pairs summing to 4
             left=1 (-1), right=5 (2): sum = -1+2 = 1 < 4, move left
             (no pairs found for -4)

        i=1: fix -1, need pairs summing to 1
             left=2 (-1), right=5 (2): sum = -1+2 = 1 = 1, found! [-1,-1,2]
             skip duplicates...
             left=3 (0), right=4 (1): sum = 0+1 = 1 = 1, found! [-1,0,1]

        i=2: skip (same as i=1, duplicate)

        Result: [[-1,-1,2], [-1,0,1]]

    Time Complexity: O(n^2)
        Sorting is O(n log n), then we have n iterations with two-pointer O(n) each.
        Like organizing a team-building exercise - first arrange everyone by height
        (sorting), then for each person, find two others from opposite ends who balance.
        With 100 people, you'd do ~100 x 100 = 10,000 checks worst case.

    Space Complexity: O(1) or O(n)
        O(1) extra space if we ignore the output list.
        O(n) if we count the space needed for sorting (depending on algorithm).
        Like using a clipboard to track results - the clipboard size depends
        on how many valid triplets you find, but you don't need extra workspace.

    Args:
        nums: The array of integers.

    Returns:
        List of all unique triplets that sum to zero.
    """
    result = []

    # Edge case: need at least 3 elements.
    if not nums or len(nums) < 3:
        return result

    # Sort to enable two-pointer technique and duplicate handling.
    nums.sort()

    # Fix first element, then use two-pointer for remaining.
    for i in range(len(nums) - 2):
        # Skip duplicates for first element.
        if i > 0 and nums[i] == nums[i - 1]:
            continue

        # Early termination: if smallest is positive, no solution.
        if nums[i] > 0:
            break

        # Two pointers from both ends of remaining array.
        left = i + 1
        right = len(nums) - 1
        target = -nums[i]  # We need left + right = -nums[i].

        while left < right:
            current_sum = nums[left] + nums[right]

            if current_sum == target:
                # Found a triplet!
                result.append([nums[i], nums[left], nums[right]])

                # Skip duplicates.
                while left < right and nums[left] == nums[left + 1]:
                    left += 1
                while left < right and nums[right] == nums[right - 1]:
                    right -= 1

                # Move both pointers.
                left += 1
                right -= 1
            elif current_sum < target:
                # Sum too small, need larger numbers.
                left += 1
            else:
                # Sum too big, need smaller numbers.
                right -= 1

    return result


def main():
    """Main function to demonstrate the TripletSumFinder."""
    print("=== TripletSumFinder: 3Sum Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate with transaction adjustments (positive = charge, negative = refund)
    print("--- Finding Zero-Sum Transaction Triplets ---\n")
    transactions = [-100, 50, -50, 100, -75, 75, 25]
    print(f"Transaction adjustments: {transactions}")
    triplets = three_sum(transactions)
    print(f"Zero-sum triplets: {triplets}\n")

    # Test cases
    print("--- Additional Examples ---\n")
    test_cases = [
        [-1, 0, 1, 2, -1, -4],
        [0, 1, 1],
        [0, 0, 0]
    ]

    for nums in test_cases:
        nums_copy = nums.copy()
        result = three_sum(nums_copy)
        print(f"Array: {nums}")
        print(f"Triplets: {result}\n")


if __name__ == "__main__":
    main()
