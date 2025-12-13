"""
TextParserUtility
----------------------------------
This program demonstrates fundamental string operations: parsing, trimming, and length calculation.
The core problem solved here is finding the length of the last word in a string (LeetCode #58).

Problem Statement:
    Given a string s consisting of words and spaces, return the length of the last word in the string.
    A word is a maximal substring consisting of non-space characters only.

Real UseCase:
    In a credit card offers system, we often need to parse and analyze offer text:
    - Extract the last keyword from offer titles (e.g., "beverages" from "Spend $20, get $5 back on handcrafted beverages")
    - Parse merchant names to extract business type (e.g., "Lines" from "Delta Air Lines")
    - Analyze offer descriptions for categorization and search indexing

Examples:
    - Input: "Spend $20, get $5 back on handcrafted beverages" -> Output: 9 (length of "beverages")
    - Input: "Delta Air Lines" -> Output: 5 (length of "Lines")
    - Input: "   Valid in-store and mobile order   " -> Output: 5 (length of "order")

Company Tags: Amazon, Microsoft

See: https://leetcode.com/problems/length-of-last-word/
"""

import sys
import os

# Add the python source root to path for imports
sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def length_of_last_word(s: str) -> int:
    """
    Finds the length of the last word in a string.

    LOGIC:
        1. Start from the end of the string
        2. Skip any trailing spaces
        3. Count characters until we hit a space or beginning of string

    Example Walkthrough:
        Input: "   hello world   "

        Step 1: Skip trailing spaces (right to left)
                "   hello world   "
                               <--  (skip 3 spaces)
                         ^
                      Stop at 'd'

        Step 2: Count letters until we hit a space
                "   hello world   "
                      <----  (count: w-o-r-l-d = 5 letters)
                     ^
                  Stop at space

        Result: 5

    Time Complexity: O(n)
        O(n) means the work grows proportionally with input size.
        Like reading every page of a book - more pages = more work.
        Worst case: "word" (no spaces) - we walk through all n characters.

    Space Complexity: O(1)
        O(1) means constant space - we use the same amount of memory regardless of input size.
        Here, the number of variable instances does not grow proportionally with the input size.
        Like opening a book to page 50 - book size doesn't matter.
        We only use 2 variables: `length` (counter) and `i` (position pointer).

    Args:
        s: The input string containing words separated by spaces.

    Returns:
        The length of the last word.
    """
    if s is None or len(s) == 0:
        return 0

    length = 0
    i = len(s) - 1  # Start from the end of the string

    # Step 1: Skip trailing spaces by moving left.
    # Here using a loop we decrement index until we find a non-space character.
    while i >= 0 and s[i] == ' ':
        i -= 1

    # Step 2: Count characters of the last word.
    # In the second loop, we continue moving left and count characters until we hit a space or the start of the string.
    while i >= 0 and s[i] != ' ':
        length += 1
        i -= 1

    return length


def main():
    """Main function to demonstrate the TextParserUtility."""
    print("=== TextParserUtility: String Parsing & Length Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate parsing offer titles
    print("--- Analyzing Offer Titles (Last Word Extraction) ---\n")
    for offer in offers:
        title = offer.title
        last_word_length = length_of_last_word(title)

        print(f"Offer: {offer.offer_id}")
        print(f"  Title: \"{title}\"")
        print(f"  Last Word Length: {last_word_length}\n")


if __name__ == "__main__":
    main()
