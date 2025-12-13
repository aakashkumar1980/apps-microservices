"""
StringCompressor
----------------------------------
This program demonstrates run-length encoding (RLE) compression.
The core problem solved here is string compression (LeetCode #443).

Problem Statement:
    Given an array of characters, compress it in-place using run-length encoding.
    After compression, return the new length.

Real UseCase:
    In a credit card offers system:
    - Data compression - reduce storage for repetitive offer codes
    - API response optimization - compress repeated status flags

Examples:
    - Input: ["a","a","b","b","c","c","c"] -> Output: ["a","2","b","2","c","3"], return 6
    - Input: ["a"] -> Output: ["a"], return 1

Company Tags: Microsoft

See: https://leetcode.com/problems/string-compression/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def compress(chars: List[str]) -> int:
    """
    Compresses a character array in-place using run-length encoding.

    LOGIC:
        1. Use two pointers: read (to scan) and write (to place compressed result)
        2. Count consecutive characters
        3. Write character and count (if > 1) at write position
        4. Return the final write position (new length)

    Example Walkthrough:
        Input: ['a','a','b','b','b','c']

        Step 1: Count 'a' = 2 -> write 'a','2' -> result: ['a','2',...]
        Step 2: Count 'b' = 3 -> write 'b','3' -> result: ['a','2','b','3',...]
        Step 3: Count 'c' = 1 -> write 'c' (no count for 1) -> result: ['a','2','b','3','c',...]

        Return length = 5

    Time Complexity: O(n)
        We traverse the array once with the read pointer.
        Like reading through a book once and taking notes.

    Space Complexity: O(1)
        We compress in-place, only using a few variables.
        Like editing a document in place without making a copy.

    Args:
        chars: The character list to compress.

    Returns:
        The new length of the compressed array.
    """
    if not chars:
        return 0

    write = 0  # Position to write compressed result
    read = 0   # Position to read from

    while read < len(chars):
        current_char = chars[read]
        count = 0

        # Count consecutive occurrences of current_char.
        while read < len(chars) and chars[read] == current_char:
            read += 1
            count += 1

        # Write the character at write position.
        chars[write] = current_char
        write += 1

        # Write the count if greater than 1.
        # For counts >= 10, we need to write each digit separately.
        if count > 1:
            for digit in str(count):
                chars[write] = digit
                write += 1

    return write


def compress_to_string(s: str) -> str:
    """
    Helper function that returns compressed string (for demonstration).

    Args:
        s: The input string.

    Returns:
        The compressed string.
    """
    if not s:
        return s

    chars = list(s)
    new_length = compress(chars)
    return ''.join(chars[:new_length])


def main():
    """Main function to demonstrate the StringCompressor."""
    print("=== StringCompressor: Run-Length Encoding Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate compression with sample strings
    print("--- Compressing Strings ---\n")
    test_cases = [
        "aabbbcccc",
        "abcdef",
        "aaaaaaaaaaaab",
        "AAABBBCCC"
    ]

    for test in test_cases:
        compressed = compress_to_string(test)
        print(f"Original: \"{test}\" (length: {len(test)})")
        print(f"Compressed: \"{compressed}\" (length: {len(compressed)})\n")


if __name__ == "__main__":
    main()
