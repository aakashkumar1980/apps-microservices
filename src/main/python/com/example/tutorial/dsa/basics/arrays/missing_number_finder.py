"""
MissingNumberFinder
----------------------------------
This program finds the missing number in a sequence using XOR or math.
The core problem solved here is Missing Number (LeetCode #268).

Problem Statement:
    Given an array nums containing n distinct numbers in the range [0, n],
    return the only number in the range that is missing from the array.

Real UseCase:
    In a credit card offers system:
    - Detect missing sequence numbers in batch processing
    - Find gaps in offer ID sequences
    - Validate completeness of data imports

Examples:
    - Input: [3,0,1] -> Output: 2 (range is 0-3, missing 2)
    - Input: [0,1] -> Output: 2 (range is 0-2, missing 2)
    - Input: [9,6,4,2,3,5,7,0,1] -> Output: 8

Company Tags: Amazon, Microsoft, Facebook

See: https://leetcode.com/problems/missing-number/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def missing_number(nums: List[int]) -> int:
    """
    Finds missing number using XOR trick (bit manipulation).

    LOGIC (XOR Properties):
        1. XOR of a number with itself is 0: a ^ a = 0
        2. XOR of a number with 0 is itself: a ^ 0 = a
        3. XOR is commutative and associative: order doesn't matter
        4. If we XOR all indices (0 to n) with all array elements,
           pairs will cancel out, leaving only the missing number!

    Example Walkthrough:
        nums = [3, 0, 1], n = 3

        XOR all indices 0 to n:  0 ^ 1 ^ 2 ^ 3 = some value X
        XOR all array elements:  3 ^ 0 ^ 1 = some value Y

        Combined: (0 ^ 1 ^ 2 ^ 3) ^ (3 ^ 0 ^ 1)
                = (0 ^ 0) ^ (1 ^ 1) ^ (3 ^ 3) ^ 2
                = 0 ^ 0 ^ 0 ^ 2
                = 2

        Missing number is 2!

    Time Complexity: O(n)
        Single pass through the array.
        Like checking attendance by calling roll numbers - you go through
        the list once, and XOR magic finds who's missing.

    Space Complexity: O(1)
        Only using one variable for XOR result.
        Like keeping a mental tally - no paper needed, just remember one number.

    Args:
        nums: Array containing n distinct numbers in range [0, n].

    Returns:
        The missing number.
    """
    n = len(nums)
    xor = n  # Start with n (the largest index not in array).

    # XOR each index with its value.
    # All paired numbers cancel out, leaving only the missing one.
    for i in range(n):
        xor ^= i ^ nums[i]

    return xor


def missing_number_sum(nums: List[int]) -> int:
    """
    Finds missing number using mathematical approach (sum formula).

    LOGIC (Gauss Formula):
        1. Expected sum of 0 to n = n * (n + 1) / 2
        2. Calculate actual sum of array elements
        3. Missing number = Expected sum - Actual sum

    Example Walkthrough:
        nums = [3, 0, 1], n = 3

        Expected sum: 3 * 4 / 2 = 6 (sum of 0+1+2+3)
        Actual sum: 3 + 0 + 1 = 4
        Missing: 6 - 4 = 2

    Time Complexity: O(n)
        Single pass to sum array elements.

    Space Complexity: O(1)
        Only using sum variables.

    Note: This approach may overflow for very large arrays.
        XOR method is safer for large inputs.

    Args:
        nums: Array containing n distinct numbers in range [0, n].

    Returns:
        The missing number.
    """
    n = len(nums)

    # Expected sum using Gauss formula: sum of 0 to n.
    expected_sum = n * (n + 1) // 2

    # Actual sum of array elements.
    actual_sum = sum(nums)

    # The difference is our missing number.
    return expected_sum - actual_sum


def main():
    """Main function to demonstrate the MissingNumberFinder."""
    print("=== MissingNumberFinder: Find Missing Number Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate finding missing sequence number
    print("--- Finding Missing Batch Sequence ---\n")
    batch_numbers = [0, 1, 2, 4, 5]  # Missing batch #3
    print(f"Batch numbers received: {batch_numbers}")
    print(f"Expected batches: 0 to {len(batch_numbers)}")
    missing_batch = missing_number(batch_numbers)
    print(f"Missing batch number: {missing_batch}\n")

    # Test cases
    print("--- Additional Examples ---\n")
    test_cases = [
        [3, 0, 1],
        [0, 1],
        [9, 6, 4, 2, 3, 5, 7, 0, 1],
        [0]
    ]

    for nums in test_cases:
        print(f"Array: {nums}")
        print(f"Range: [0, {len(nums)}]")
        print(f"Missing (XOR): {missing_number(nums)}")
        print(f"Missing (Sum): {missing_number_sum(nums)}\n")


if __name__ == "__main__":
    main()
