"""
SimpleAnagramMatcher
----------------------------------
This program demonstrates frequency counting using HashMap/dict to check if two strings are anagrams.
The core problem solved here is determining if two strings are valid anagrams (LeetCode #242).

Problem Statement:
    Given two strings s and t, return true if t is an anagram of s, and false otherwise.
    An anagram is a word formed by rearranging the letters of another word using all original letters exactly once.

Real UseCase:
    In a credit card offers system:
    - Tag matching - verify if two category tags contain same keywords
    - Duplicate detection - check if two offer codes are permutations of each other
    - Data validation - ensure rearranged merchant codes still match

Examples:
    - Input: s = "anagram", t = "nagaram" -> Output: True
    - Input: s = "rat", t = "car" -> Output: False
    - Input: s = "listen", t = "silent" -> Output: True

Company Tags: Facebook, Google

See: https://leetcode.com/problems/valid-anagram/
"""

import sys
import os
from typing import Dict

# Add python source root to path for imports
sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def is_anagram(s: str, t: str) -> bool:
    """
    Checks if two strings are anagrams of each other using frequency counting with dict.

    LOGIC:
        1. If lengths differ, they cannot be anagrams
        2. Count frequency of each character in first string (increment)
        3. Subtract frequency for each character in second string (decrement)
        4. If all counts are zero, strings are anagrams

    Example Walkthrough:
        Input: s = "anagram", t = "nagaram"

        Step 1: Build frequency map from s = "anagram"
                {a: 3, n: 1, g: 1, r: 1, m: 1}

        Step 2: Subtract frequencies using t = "nagaram"
                n: 1->0, a: 3->2, g: 1->0, a: 2->1, r: 1->0, a: 1->0, m: 1->0
                Final: {a: 0, n: 0, g: 0, r: 0, m: 0}

        Step 3: All counts are 0 -> return True

    Time Complexity: O(n)
        O(n) means we process each character once.
        Like counting items in two shopping bags - more items = more counting.
        We iterate through both strings once, so total work is proportional to string length.

    Space Complexity: O(1)
        O(1) because the dict size is bounded by the character set (26 letters for lowercase).
        Like having 26 labeled boxes for letters - no matter how long the string,
        we never need more than 26 boxes.

    Args:
        s: First string.
        t: Second string.

    Returns:
        True if t is an anagram of s, False otherwise.
    """
    # Step 1: If lengths differ, they cannot be anagrams.
    if s is None or t is None or len(s) != len(t):
        return False

    # Create a dict to store character frequencies.
    # Key = character, Value = count of occurrences.
    char_count: Dict[str, int] = {}

    # Step 2: Count frequency of each character in first string (increment).
    # For each character in s, we add 1 to its count.
    for c in s:
        char_count[c] = char_count.get(c, 0) + 1

    # Step 3: Subtract frequency for each character in second string (decrement).
    # For each character in t, we subtract 1 from its count.
    for c in t:
        char_count[c] = char_count.get(c, 0) - 1

    # Step 4: If all counts are zero, strings are anagrams.
    # If any count is non-zero, the strings have different character frequencies.
    for count in char_count.values():
        if count != 0:
            return False

    return True


def main():
    """Main function to demonstrate the SimpleAnagramMatcher."""
    print("=== SimpleAnagramMatcher: Anagram Detection Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate anagram checking with offer category tags
    print("--- Checking Category Tag Anagrams ---\n")
    for i in range(len(offers)):
        for j in range(i + 1, len(offers)):
            categories1 = "".join(offers[i].eligibility.categories) if offers[i].eligibility else ""
            categories2 = "".join(offers[j].eligibility.categories) if offers[j].eligibility else ""

            result = is_anagram(categories1, categories2)

            print(f"Offer {offers[i].offer_id} categories: \"{categories1}\"")
            print(f"Offer {offers[j].offer_id} categories: \"{categories2}\"")
            print(f"Are anagrams: {result}\n")

    # Demonstrate with simple examples
    print("--- Classic Anagram Examples ---\n")
    test_cases = [
        ("anagram", "nagaram"),
        ("rat", "car"),
        ("listen", "silent")
    ]

    for s, t in test_cases:
        result = is_anagram(s, t)
        print(f"\"{s}\" vs \"{t}\" -> {result}")


if __name__ == "__main__":
    main()
