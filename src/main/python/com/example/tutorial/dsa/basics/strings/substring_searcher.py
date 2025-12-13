"""
SubstringSearcher
----------------------------------
This program demonstrates pattern matching to find the first occurrence of a substring.
The core problem solved here is finding needle in haystack (LeetCode #28).

Problem Statement:
    Given two strings haystack and needle, return the index of the first occurrence
    of needle in haystack, or -1 if needle is not part of haystack.

Real UseCase:
    In a credit card offers system:
    - Log searching - find specific error patterns in transaction logs
    - Text filtering - search for keywords in offer descriptions

Examples:
    - Input: haystack = "sadbutsad", needle = "sad" -> Output: 0
    - Input: haystack = "leetcode", needle = "leeto" -> Output: -1

Company Tags: Google

See: https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/
"""

import sys
import os

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def str_str(haystack: str, needle: str) -> int:
    """
    Finds the first occurrence of needle in haystack.

    LOGIC:
        1. Handle edge cases (empty needle returns 0)
        2. Slide a window of needle's length across haystack
        3. At each position, compare the window with needle
        4. Return index if match found, -1 otherwise

    Example Walkthrough:
        Input: haystack = "hello", needle = "ll"

        Step 1: Compare haystack[0..1] = "he" with "ll" -> no match
        Step 2: Compare haystack[1..2] = "el" with "ll" -> no match
        Step 3: Compare haystack[2..3] = "ll" with "ll" -> MATCH!

        Return index 2

    Time Complexity: O(n * m)
        Where n = haystack length, m = needle length.
        Like searching for a word in a book - you check each starting position
        and compare letter by letter.

    Space Complexity: O(1)
        We only use a few variables for indices.
        Like using your finger to track position - no extra paper needed.

    Args:
        haystack: The string to search in.
        needle: The pattern to search for.

    Returns:
        Index of first occurrence, or -1 if not found.
    """
    # Edge case: empty needle always matches at index 0.
    if needle is None or len(needle) == 0:
        return 0

    if haystack is None or len(haystack) < len(needle):
        return -1

    h_len = len(haystack)
    n_len = len(needle)

    # Slide a window of needle's length across haystack.
    # We only need to check positions where needle can fully fit.
    for i in range(h_len - n_len + 1):
        # Check if substring starting at i matches needle.
        # Compare character by character.
        j = 0
        while j < n_len and haystack[i + j] == needle[j]:
            j += 1

        # If we matched all characters of needle, we found it.
        if j == n_len:
            return i

    # No match found.
    return -1


def main():
    """Main function to demonstrate the SubstringSearcher."""
    print("=== SubstringSearcher: Pattern Matching Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate searching for keywords in offer titles
    print("--- Searching Keywords in Offer Titles ---\n")
    keywords = ["back", "$", "online", "flight"]

    for offer in offers:
        title = offer.title
        print(f"Offer {offer.offer_id}: \"{title}\"")

        for keyword in keywords:
            index = str_str(title.lower(), keyword.lower())
            if index != -1:
                print(f"  Found \"{keyword}\" at index {index}")
        print()


if __name__ == "__main__":
    main()
