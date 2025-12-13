"""
SubarrayTargetSum
----------------------------------
This program counts subarrays with sum equal to k using prefix sum + HashMap.
The core problem solved here is Subarray Sum Equals K (LeetCode #560).

Problem Statement:
    Given an array of integers nums and an integer k, return the total number of
    continuous subarrays whose sum equals to k.

Real UseCase:
    In a credit card offers system:
    - Find consecutive transactions that total a specific amount
    - Identify spending patterns matching budget thresholds
    - Count time windows where reward points hit target

Examples:
    - Input: nums = [1,1,1], k = 2 -> Output: 2 ([1,1] at index 0-1 and 1-2)
    - Input: nums = [1,2,3], k = 3 -> Output: 2 ([1,2] and [3])
    - Input: nums = [1,-1,0], k = 0 -> Output: 3

Company Tags: Facebook, Amazon, Google, Microsoft

See: https://leetcode.com/problems/subarray-sum-equals-k/
"""

import sys
import os
from typing import List
from collections import defaultdict

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def subarray_sum(nums: List[int], k: int) -> int:
    """
    Counts subarrays with sum equal to k using prefix sum + HashMap technique.

    LOGIC (Prefix Sum + HashMap):
        1. Maintain running prefix sum as we iterate
        2. If prefix_sum - k exists in dict, those positions form valid subarrays ending here
        3. Store frequency of each prefix sum in dictionary
        4. Key insight: sum(i,j) = prefix_sum[j] - prefix_sum[i-1]
        5. If prefix_sum[j] - k = prefix_sum[i-1], then sum(i,j) = k

    Example Walkthrough:
        nums = [1, 1, 1], k = 2
        prefix_sum_count = {0: 1}, prefix_sum = 0, count = 0

        i=0: prefix_sum = 0+1 = 1
             need: 1-2 = -1, not in dict
             prefix_sum_count = {0:1, 1:1}, count = 0

        i=1: prefix_sum = 1+1 = 2
             need: 2-2 = 0, IS in dict with count 1!
             prefix_sum_count = {0:1, 1:1, 2:1}, count = 1

        i=2: prefix_sum = 2+1 = 3
             need: 3-2 = 1, IS in dict with count 1!
             prefix_sum_count = {0:1, 1:1, 2:1, 3:1}, count = 2

        Result: 2

    Why initialize dict with {0: 1}?
        This handles the case when a subarray starting from index 0 sums to k.
        If prefix_sum at some point equals k, we need prefix_sum - k = 0 to be found.

    Time Complexity: O(n)
        Single pass through array with O(1) dict operations.
        Like tallying a cash register receipt - you go line by line,
        keeping a running total and checking if any segment matches your target.
        With n items, you check n times.

    Space Complexity: O(n)
        Dictionary may store up to n different prefix sums.
        Like keeping a ledger of all running totals seen - in worst case,
        every position has a unique total, so n entries.

    Args:
        nums: The array of integers.
        k: The target sum.

    Returns:
        Count of subarrays with sum equal to k.
    """
    # Edge case.
    if not nums:
        return 0

    # Dictionary: prefix_sum -> frequency.
    # Initialize with 0:1 to handle subarrays starting from index 0.
    prefix_sum_count = defaultdict(int)
    prefix_sum_count[0] = 1

    prefix_sum = 0
    count = 0

    for num in nums:
        # Update running prefix sum.
        prefix_sum += num

        # If (prefix_sum - k) exists, those subarrays end here with sum k.
        # Because: current_prefix_sum - previous_prefix_sum = k.
        if (prefix_sum - k) in prefix_sum_count:
            count += prefix_sum_count[prefix_sum - k]

        # Record this prefix sum.
        prefix_sum_count[prefix_sum] += 1

    return count


def main():
    """Main function to demonstrate the SubarrayTargetSum."""
    print("=== SubarrayTargetSum: Prefix Sum + HashMap Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate with transaction amounts
    print("--- Finding Transaction Sequences with Target Sum ---\n")
    transactions = [100, 50, -50, 100, 50, -100, 50]
    target_sum = 100
    print(f"Transactions: {transactions}")
    print(f"Target sum: {target_sum}")
    count = subarray_sum(transactions, target_sum)
    print(f"Number of subarrays with sum {target_sum}: {count}\n")

    # Test cases
    print("--- Additional Examples ---\n")
    test_cases = [
        ([1, 1, 1], 2),
        ([1, 2, 3], 3),
        ([1, -1, 0], 0),
        ([3, 4, 7, 2, -3, 1, 4, 2], 7)
    ]

    for nums, k in test_cases:
        result = subarray_sum(nums, k)
        print(f"Array: {nums}, k = {k}")
        print(f"Count of subarrays with sum k: {result}\n")


if __name__ == "__main__":
    main()
