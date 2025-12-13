"""
DuplicateDataDetector
----------------------------------
This program detects duplicate elements in an array using HashSet.
The core problem solved here is Contains Duplicate (LeetCode #217).

Problem Statement:
    Given an integer array nums, return true if any value appears at least twice
    in the array, and return false if every element is distinct.

Real UseCase:
    In a credit card offers system:
    - Detect duplicate offer IDs in a batch upload
    - Validate unique campaign assignments
    - Check for duplicate merchant entries

Examples:
    - Input: [1,2,3,1] -> Output: True (1 appears twice)
    - Input: [1,2,3,4] -> Output: False (all distinct)
    - Input: [1,1,1,3,3,4,3,2,4,2] -> Output: True

Company Tags: Amazon, Apple, Google

See: https://leetcode.com/problems/contains-duplicate/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def contains_duplicate(nums: List[int]) -> bool:
    """
    Detects if array contains any duplicate elements using HashSet.

    LOGIC:
        1. Create a set to store seen elements
        2. Iterate through each element in the array
        3. For each element, check if it's already in the set
        4. If yes, we found a duplicate - return True
        5. If no, add element to set and continue
        6. If we finish without finding duplicate, return False

    Example Walkthrough:
        nums = [1, 2, 3, 1]

        Step 1: num=1, seen={}, 1 not in seen -> add 1, seen={1}
        Step 2: num=2, seen={1}, 2 not in seen -> add 2, seen={1,2}
        Step 3: num=3, seen={1,2}, 3 not in seen -> add 3, seen={1,2,3}
        Step 4: num=1, seen={1,2,3}, 1 IS in seen -> return True!

        Result: True (duplicate found)

    Time Complexity: O(n)
        We visit each element once, and set operations (add/in) are O(1).
        Like checking names off a guest list - each lookup is instant because
        the list is organized alphabetically. With n guests, we do n quick lookups.

    Space Complexity: O(n)
        In worst case (no duplicates), we store all n elements in the set.
        Like writing down each guest's name - if all 100 guests are unique,
        you need paper for 100 names. The space grows with input size.

    Args:
        nums: The array of integers to check.

    Returns:
        True if any value appears at least twice, False otherwise.
    """
    # Edge case: empty or single element array can't have duplicates.
    if not nums or len(nums) <= 1:
        return False

    # Set for O(1) lookup - like an indexed phonebook.
    seen = set()

    # Check each element.
    for num in nums:
        # If already seen, it's a duplicate!
        if num in seen:
            return True
        # Otherwise, remember we've seen this number.
        seen.add(num)

    # No duplicates found.
    return False


def contains_duplicate_strings(strings: List[str]) -> bool:
    """
    String version for detecting duplicate strings (like offer IDs).

    Args:
        strings: The array of strings to check.

    Returns:
        True if any string appears at least twice.
    """
    if not strings or len(strings) <= 1:
        return False

    seen = set()

    for s in strings:
        if s in seen:
            return True
        seen.add(s)

    return False


def main():
    """Main function to demonstrate the DuplicateDataDetector."""
    print("=== DuplicateDataDetector: Duplicate Detection Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate duplicate detection with offer IDs
    print("--- Checking for Duplicate Offer IDs ---\n")
    offer_ids = [offer.offer_id for offer in offers]

    has_duplicate_offers = contains_duplicate_strings(offer_ids)
    print(f"Offer IDs: {offer_ids}")
    print(f"Contains duplicates: {has_duplicate_offers}\n")

    # Demonstrate with numeric arrays
    print("--- Numeric Array Examples ---\n")
    test_cases = [
        [1, 2, 3, 1],
        [1, 2, 3, 4],
        [1, 1, 1, 3, 3, 4, 3, 2, 4, 2]
    ]

    for nums in test_cases:
        has_duplicate = contains_duplicate(nums)
        print(f"Array: {nums}")
        print(f"Contains duplicate: {has_duplicate}\n")


if __name__ == "__main__":
    main()
