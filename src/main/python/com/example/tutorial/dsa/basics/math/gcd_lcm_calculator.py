"""
GCDLCMCalculator
----------------------------------
This program demonstrates GCD (Greatest Common Divisor) and LCM (Least Common Multiple).
These are fundamental number theory concepts using Euclidean algorithm.

Problem Statement:
    Implement functions to calculate GCD and LCM of two numbers.

Real UseCase:
    In a credit card offers system:
    - Scheduling problems - find common intervals for offer refreshes
    - Timing calculations - synchronize batch jobs

Examples:
    - GCD(12, 18) = 6
    - LCM(4, 6) = 12
    - GCD(17, 13) = 1 (coprime)

Company Tags: Google
"""

import sys
import os

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def gcd(a: int, b: int) -> int:
    """
    Calculates the Greatest Common Divisor using Euclidean algorithm.

    LOGIC:
        1. If b is 0, return a (base case)
        2. Otherwise, return GCD(b, a % b)
        3. The key insight: GCD(a, b) = GCD(b, a mod b)

    Example Walkthrough:
        GCD(48, 18):

        Step 1: GCD(48, 18) -> 48 % 18 = 12 -> GCD(18, 12)
        Step 2: GCD(18, 12) -> 18 % 12 = 6  -> GCD(12, 6)
        Step 3: GCD(12, 6)  -> 12 % 6 = 0   -> GCD(6, 0)
        Step 4: GCD(6, 0)   -> b = 0, return 6

        Result: 6

    Time Complexity: O(log(min(a, b)))
        Each step reduces the problem size by at least half.
        Like repeatedly halving - very fast convergence.

    Space Complexity: O(1)
        Iterative version uses constant space.

    Args:
        a: First number.
        b: Second number.

    Returns:
        The greatest common divisor.
    """
    # Make sure we work with positive numbers.
    a = abs(a)
    b = abs(b)

    # Euclidean algorithm: GCD(a, b) = GCD(b, a % b).
    # Base case: when b becomes 0, a is the GCD.
    while b != 0:
        a, b = b, a % b

    return a


def lcm(a: int, b: int) -> int:
    """
    Calculates the Least Common Multiple using the formula: LCM(a, b) = (a * b) / GCD(a, b).

    LOGIC:
        1. LCM(a, b) * GCD(a, b) = a * b (mathematical property)
        2. Therefore: LCM(a, b) = (a * b) / GCD(a, b)
        3. To avoid overflow: LCM(a, b) = (a / GCD(a, b)) * b

    Time Complexity: O(log(min(a, b)))
        Dominated by GCD calculation.

    Space Complexity: O(1)

    Args:
        a: First number.
        b: Second number.

    Returns:
        The least common multiple.
    """
    a = abs(a)
    b = abs(b)

    if a == 0 or b == 0:
        return 0

    # Divide before multiply to reduce overflow risk.
    return (a // gcd(a, b)) * b


def main():
    """Main function to demonstrate the GCDLCMCalculator."""
    print("=== GCDLCMCalculator: Number Theory Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate GCD and LCM
    print("--- GCD and LCM Examples ---\n")
    test_cases = [(12, 18), (4, 6), (17, 13), (100, 25), (7, 3)]

    for a, b in test_cases:
        gcd_result = gcd(a, b)
        lcm_result = lcm(a, b)

        print(f"GCD({a}, {b}) = {gcd_result}")
        print(f"LCM({a}, {b}) = {lcm_result}\n")

    # Real-world example: scheduling
    print("--- Scheduling Example ---")
    print("Job A runs every 6 hours, Job B runs every 8 hours.")
    print(f"They will coincide every {lcm(6, 8)} hours (LCM).")


if __name__ == "__main__":
    main()
