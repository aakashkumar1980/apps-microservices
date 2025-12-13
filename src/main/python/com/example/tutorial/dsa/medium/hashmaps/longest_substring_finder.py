"""
LongestSubstringFinder
----------------------------------
This program finds the longest substring without repeating characters using sliding window.
The core problem solved here is Longest Substring Without Repeating Characters (LeetCode #3).

Problem Statement:
    Given a string s, find the length of the longest substring without repeating characters.

Real UseCase:
    In a credit card offers system:
    - Find longest sequence of unique offer categories used
    - Analyze user engagement patterns without repeated actions
    - Identify longest streak of distinct merchant visits

Examples:
    - Input: "abcabcbb" -> Output: 3 (substring "abc")
    - Input: "bbbbb" -> Output: 1 (substring "b")
    - Input: "pwwkew" -> Output: 3 (substring "wke")

Company Tags: Amazon, Google, Facebook, Microsoft, Bloomberg

See: https://leetcode.com/problems/longest-substring-without-repeating-characters/
"""

import sys
import os

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def length_of_longest_substring(s: str) -> int:
    """
    Finds length of longest substring without repeating characters using sliding window + dict.

    LOGIC (Sliding Window):
        1. Maintain a window [left, right] containing unique characters
        2. Expand window by moving right pointer
        3. If duplicate found, shrink window from left until duplicate removed
        4. Use dict to track last seen index of each character
        5. Track maximum window size seen

    Example Walkthrough:
        s = "abcabcbb"
        char_index = {}, left = 0, max_len = 0

        right=0: 'a' not in dict, char_index={'a':0}, window="a", max_len=1
        right=1: 'b' not in dict, char_index={'a':0,'b':1}, window="ab", max_len=2
        right=2: 'c' not in dict, char_index={'a':0,'b':1,'c':2}, window="abc", max_len=3
        right=3: 'a' in dict at 0, left=max(0,0+1)=1, char_index={'a':3,'b':1,'c':2}, window="bca", max_len=3
        right=4: 'b' in dict at 1, left=max(1,1+1)=2, char_index={'a':3,'b':4,'c':2}, window="cab", max_len=3
        right=5: 'c' in dict at 2, left=max(2,2+1)=3, char_index={'a':3,'b':4,'c':5}, window="abc", max_len=3
        right=6: 'b' in dict at 4, left=max(3,4+1)=5, char_index={'a':3,'b':6,'c':5}, window="cb", max_len=3
        right=7: 'b' in dict at 6, left=max(5,6+1)=7, char_index={'a':3,'b':7,'c':5}, window="b", max_len=3

        Result: 3

    Time Complexity: O(n)
        Each character is visited at most twice (once by right, once by left).
        Like reading a book with a highlighter - you move forward character by character,
        occasionally going back to clear duplicates. Total work is still proportional to book length.

    Space Complexity: O(min(m, n))
        Where m = alphabet size, n = string length. Dict stores at most unique characters.
        Like keeping a guest list for a party - you only need space for unique guests.
        If 26 letters possible, at most 26 entries. If string shorter, even fewer.

    Args:
        s: The input string.

    Returns:
        Length of longest substring without repeating characters.
    """
    # Edge case: empty string.
    if not s:
        return 0

    # Dict: character -> last seen index.
    char_index = {}

    max_len = 0
    left = 0  # Left boundary of sliding window.

    # Expand window with right pointer.
    for right, c in enumerate(s):
        # If character seen before AND it's within current window,
        # move left pointer past the previous occurrence.
        if c in char_index and char_index[c] >= left:
            left = char_index[c] + 1

        # Update last seen index.
        char_index[c] = right

        # Update max length.
        max_len = max(max_len, right - left + 1)

    return max_len


def main():
    """Main function to demonstrate the LongestSubstringFinder."""
    print("=== LongestSubstringFinder: Sliding Window Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate with offer title analysis
    print("--- Analyzing Offer Titles ---\n")
    for offer in offers:
        title = offer.title
        longest_unique = length_of_longest_substring(title)
        print(f"Title: \"{title}\"")
        print(f"Longest unique substring length: {longest_unique}\n")

    # Test cases
    print("--- Additional Examples ---\n")
    test_cases = ["abcabcbb", "bbbbb", "pwwkew", "", " ", "dvdf"]

    for s in test_cases:
        result = length_of_longest_substring(s)
        print(f"Input: \"{s}\"")
        print(f"Longest unique substring length: {result}\n")


if __name__ == "__main__":
    main()
