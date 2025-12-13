"""
PowerCalculator
----------------------------------
This program demonstrates binary exponentiation (fast power calculation).
The core problem solved here is Pow(x, n) (LeetCode #50).

Problem Statement:
    Implement pow(x, n), which calculates x raised to the power n.

Real UseCase:
    In a credit card offers system:
    - Rate calculations - compound interest (1 + r)^n
    - Reward projections - exponential growth calculations

Examples:
    - Input: x = 2.0, n = 10 -> Output: 1024.0
    - Input: x = 2.1, n = 3 -> Output: 9.261
    - Input: x = 2.0, n = -2 -> Output: 0.25

Company Tags: Facebook, Amazon (⭐)

See: https://leetcode.com/problems/powx-n/
"""

import sys
import os

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def my_pow(x: float, n: int) -> float:
    """
    Calculates x raised to the power n using binary exponentiation.

    LOGIC:
        1. Handle negative exponent by using 1/x and positive n
        2. Use binary exponentiation: x^n = (x^2)^(n/2) if n is even
        3. If n is odd: x^n = x * x^(n-1)
        4. This reduces O(n) multiplications to O(log n)

    Example Walkthrough:
        x = 2, n = 10 (binary: 1010)

        Step 1: n=10 (even), x=2, result=1
                -> x = 2*2 = 4, n = 5
        Step 2: n=5 (odd), x=4, result=1
                -> result = 1*4 = 4, x = 4*4 = 16, n = 2
        Step 3: n=2 (even), x=16, result=4
                -> x = 16*16 = 256, n = 1
        Step 4: n=1 (odd), x=256, result=4
                -> result = 4*256 = 1024, n = 0

        Return 1024

    Time Complexity: O(log n)
        We halve n in each iteration.
        Like binary search - cutting problem in half each step.

    Space Complexity: O(1)
        Only using a few variables (iterative version).

    Args:
        x: The base.
        n: The exponent (can be negative).

    Returns:
        x raised to the power n.
    """
    # Handle edge cases.
    if n == 0:
        return 1.0

    # Handle negative exponent: x^(-n) = 1 / x^n.
    exp = n
    if exp < 0:
        x = 1 / x
        exp = -exp

    result = 1.0

    # Binary exponentiation.
    while exp > 0:
        # If current bit is 1 (exp is odd), multiply result by x.
        if exp % 2 == 1:
            result *= x

        # Square x for the next bit.
        x *= x

        # Move to the next bit.
        exp //= 2

    return result


def main():
    """Main function to demonstrate the PowerCalculator."""
    print("=== PowerCalculator: Binary Exponentiation Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate power calculations
    print("--- Power Calculations ---\n")
    test_cases = [(2.0, 10), (2.1, 3), (2.0, -2), (1.5, 4), (3.0, 5)]

    for x, n in test_cases:
        result = my_pow(x, n)
        print(f"{x:.2f} ^ {n} = {result:.6f}")

    # Real-world example: compound interest
    print("\n--- Compound Interest Example ---")
    principal = 1000
    rate = 0.05  # 5% annual rate
    years = 10
    final_amount = principal * my_pow(1 + rate, years)
    print(f"${principal:.2f} at {rate * 100:.1f}% for {years} years = ${final_amount:.2f}")


if __name__ == "__main__":
    main()
