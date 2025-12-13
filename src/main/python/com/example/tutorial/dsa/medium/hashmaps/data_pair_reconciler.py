"""
DataPairReconciler
----------------------------------
This program finds two numbers that add up to a target using HashMap.
The core problem solved here is Two Sum (LeetCode #1).

Problem Statement:
    Given an array of integers nums and an integer target, return indices of the
    two numbers such that they add up to target. Each input has exactly one solution.

Real UseCase:
    In a credit card offers system:
    - Find two offers whose combined discount equals a target amount
    - Reconcile pairs of transactions that net to a specific value
    - Match complementary reward point redemptions

Examples:
    - Input: nums = [2,7,11,15], target = 9 -> Output: [0,1] (2+7=9)
    - Input: nums = [3,2,4], target = 6 -> Output: [1,2] (2+4=6)
    - Input: nums = [3,3], target = 6 -> Output: [0,1] (3+3=6)

Company Tags: Amazon, Google, Facebook, Apple, Microsoft (Most Asked!)

See: https://leetcode.com/problems/two-sum/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def two_sum(nums: List[int], target: int) -> List[int]:
    """
    Finds two indices whose values sum to target using HashMap for O(1) lookup.

    LOGIC (HashMap Complement Search):
        1. For each number, we need its "complement" (target - current number)
        2. Use dictionary to store {value: index} as we iterate
        3. For each num, check if complement exists in dict
        4. If yes, we found our pair! Return both indices
        5. If no, add current {num: index} to dict for future lookups

    Example Walkthrough:
        nums = [2, 7, 11, 15], target = 9
        num_to_index = {}

        i=0: num=2, complement=9-2=7, 7 not in dict
             -> add {2: 0}, num_to_index = {2: 0}

        i=1: num=7, complement=9-7=2, 2 IS in dict at index 0!
             -> return [0, 1]

        Result: [0, 1] because nums[0] + nums[1] = 2 + 7 = 9

    Time Complexity: O(n)
        Single pass through the array, dict operations are O(1).
        Like looking for a dance partner at a party - you check your list
        of "looking for partner" people (dict) for each new person.
        If their complement is on the list, you found a match!

    Space Complexity: O(n)
        Dictionary may store up to n-1 elements.
        Like keeping a guest registry - worst case everyone signs in
        before finding a match. The registry grows with number of guests.

    Args:
        nums: The array of integers.
        target: The target sum.

    Returns:
        List of two indices, or empty list if no solution.
    """
    # Dictionary: value -> index for O(1) complement lookup.
    num_to_index = {}

    for i, num in enumerate(nums):
        # What number do we need to pair with nums[i]?
        complement = target - num

        # If complement exists, we found our answer!
        if complement in num_to_index:
            return [num_to_index[complement], i]

        # Otherwise, remember this number for future lookups.
        num_to_index[num] = i

    # No solution found (shouldn't happen per problem constraints).
    return []


def main():
    """Main function to demonstrate the DataPairReconciler."""
    print("=== DataPairReconciler: Two Sum Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate finding pair that sums to target
    print("--- Finding Transaction Pair for Target Sum ---\n")
    transactions = [150, 200, 350, 400, 250]
    target_sum = 600
    print(f"Transactions: {transactions}")
    print(f"Target sum: {target_sum}")
    result = two_sum(transactions, target_sum)
    if result:
        print(f"Indices: {result}")
        print(f"Values: {transactions[result[0]]} + {transactions[result[1]]} = {target_sum}\n")

    # Test cases
    print("--- Additional Examples ---\n")
    test_cases = [
        ([2, 7, 11, 15], 9),
        ([3, 2, 4], 6),
        ([3, 3], 6)
    ]

    for nums, target in test_cases:
        indices = two_sum(nums, target)
        print(f"Array: {nums}, Target: {target}")
        print(f"Result: {indices}")
        if len(indices) == 2:
            print(f"Sum: {nums[indices[0]]} + {nums[indices[1]]} = {target}\n")


if __name__ == "__main__":
    main()
