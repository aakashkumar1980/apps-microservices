"""
ReverseDataSanitizer
----------------------------------
This program demonstrates digit/integer reversal.
The core problem solved here is reversing an integer (LeetCode #7).

Problem Statement:
    Given a signed 32-bit integer x, return x with its digits reversed.
    If reversing causes overflow, return 0.

Real UseCase:
    In a credit card offers system:
    - Data masking - obfuscate offer IDs or transaction amounts
    - ID generation - create reversed IDs for internal tracking

Examples:
    - Input: 123 -> Output: 321
    - Input: -123 -> Output: -321
    - Input: 120 -> Output: 21

Company Tags: Facebook

See: https://leetcode.com/problems/reverse-integer/
"""

import sys
import os

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers

INT_MAX = 2**31 - 1
INT_MIN = -2**31


def reverse(x: int) -> int:
    """
    Reverses the digits of an integer.

    LOGIC:
        1. Extract the last digit using modulo (x % 10)
        2. Add it to the result after shifting result left (result * 10)
        3. Remove the last digit from x (x // 10)
        4. Check for overflow before each operation

    Example Walkthrough:
        Input: x = 123

        Step 1: digit = 123 % 10 = 3, result = 0*10 + 3 = 3,   x = 123//10 = 12
        Step 2: digit = 12 % 10 = 2,  result = 3*10 + 2 = 32,  x = 12//10 = 1
        Step 3: digit = 1 % 10 = 1,   result = 32*10 + 1 = 321, x = 1//10 = 0

        Return 321

    Time Complexity: O(log n)
        We process each digit once. Number of digits = log10(n).
        Like reading digits of a number - more digits = more work.

    Space Complexity: O(1)
        We only use a few variables.
        Like using your fingers to count - no extra paper needed.

    Args:
        x: The integer to reverse.

    Returns:
        The reversed integer, or 0 if overflow occurs.
    """
    result = 0
    sign = 1 if x >= 0 else -1
    x = abs(x)

    while x != 0:
        # Extract the last digit.
        digit = x % 10

        # Check for overflow BEFORE multiplying.
        if result > INT_MAX // 10:
            return 0

        # Build the reversed number.
        result = result * 10 + digit

        # Remove the last digit from x.
        x = x // 10

    result = sign * result

    # Final overflow check.
    if result > INT_MAX or result < INT_MIN:
        return 0

    return result


def main():
    """Main function to demonstrate the ReverseDataSanitizer."""
    print("=== ReverseDataSanitizer: Integer Reversal Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate reversing integers
    print("--- Reversing Transaction IDs (simulated) ---\n")
    test_cases = [123, -456, 1200, 2147483647, -2147483648, 0]

    for num in test_cases:
        reversed_num = reverse(num)
        print(f"Original: {num:12d} -> Reversed: {reversed_num:12d}")


if __name__ == "__main__":
    main()
