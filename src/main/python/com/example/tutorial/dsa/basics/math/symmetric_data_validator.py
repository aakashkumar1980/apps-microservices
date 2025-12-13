"""
SymmetricDataValidator
----------------------------------
This program demonstrates palindrome checking for integers.
The core problem solved here is determining if a number is a palindrome (LeetCode #9).

Problem Statement:
    Given an integer x, return true if x is a palindrome, and false otherwise.
    A palindrome reads the same forwards and backwards.

Real UseCase:
    In a credit card offers system:
    - Transaction ID validation - check if IDs follow symmetric patterns
    - Data integrity checks - validate checksums with palindrome properties

Examples:
    - Input: 121 -> Output: True
    - Input: -121 -> Output: False
    - Input: 10 -> Output: False

Company Tags: Facebook, Bloomberg

See: https://leetcode.com/problems/palindrome-number/
"""

import sys
import os

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def is_palindrome(x: int) -> bool:
    """
    Checks if an integer is a palindrome without converting to string.

    LOGIC:
        1. Negative numbers are not palindromes
        2. Reverse only the second half of the number
        3. Compare first half with reversed second half

    Example Walkthrough:
        Input: x = 12321

        Step 1: x = 12321, reversed = 0
        Step 2: x = 1232,  reversed = 1  (extracted 1)
        Step 3: x = 123,   reversed = 12 (extracted 2)
        Step 4: x = 12,    reversed = 123 (extracted 3)

        Now x (12) <= reversed (123), stop!
        Check: x == reversed//10? -> 12 == 12? -> True (odd length, ignore middle)

    Time Complexity: O(log n)
        We process half the digits.
        Like checking if a word is a palindrome from both ends.

    Space Complexity: O(1)
        We only use a few variables.
        No extra storage needed - just comparing numbers.

    Args:
        x: The integer to check.

    Returns:
        True if x is a palindrome, False otherwise.
    """
    # Negative numbers are not palindromes.
    # Numbers ending in 0 (except 0 itself) are not palindromes.
    if x < 0 or (x % 10 == 0 and x != 0):
        return False

    reversed_half = 0

    # Reverse only half of the number.
    # We stop when the reversed half is >= the remaining half.
    while x > reversed_half:
        # Extract last digit and add to reversed.
        reversed_half = reversed_half * 10 + x % 10
        # Remove last digit from x.
        x = x // 10

    # For even length: x == reversed (e.g., 1221 -> x=12, reversed=12)
    # For odd length: x == reversed//10 (e.g., 12321 -> x=12, reversed=123)
    return x == reversed_half or x == reversed_half // 10


def main():
    """Main function to demonstrate the SymmetricDataValidator."""
    print("=== SymmetricDataValidator: Palindrome Check Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate palindrome checking
    print("--- Checking Symmetric Transaction IDs ---\n")
    test_cases = [121, -121, 12321, 10, 1234321, 123, 0, 1]

    for num in test_cases:
        is_palin = is_palindrome(num)
        status = "✓ Palindrome" if is_palin else "✗ Not palindrome"
        print(f"{num:8d} -> {status}")


if __name__ == "__main__":
    main()
