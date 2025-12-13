"""
FizzBuzzBatchProcessor
----------------------------------
This program demonstrates conditional logic and modulo operations.
The core problem solved here is FizzBuzz (LeetCode #412).

Problem Statement:
    Given an integer n, return a string array where:
    - answer[i] == "FizzBuzz" if i is divisible by 3 and 5
    - answer[i] == "Fizz" if i is divisible by 3
    - answer[i] == "Buzz" if i is divisible by 5
    - answer[i] == i (as string) otherwise

Real UseCase:
    In a credit card offers system:
    - Batch job categorization - route every 3rd offer to Marketing, every 5th to Finance

Examples:
    - Input: n = 3 -> Output: ["1","2","Fizz"]
    - Input: n = 15 -> Output: [...,"FizzBuzz"]

Company Tags: LinkedIn

See: https://leetcode.com/problems/fizz-buzz/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def fizz_buzz(n: int) -> List[str]:
    """
    Generates FizzBuzz sequence from 1 to n.

    LOGIC:
        1. Check divisibility by both 3 AND 5 first (order matters!)
        2. Then check divisibility by 3
        3. Then check divisibility by 5
        4. Otherwise, use the number itself

    Example Walkthrough:
        n = 15:

        i=1:  1%3!=0, 1%5!=0  -> "1"
        i=3:  3%3=0           -> "Fizz"
        i=5:  5%5=0           -> "Buzz"
        i=15: 15%3=0, 15%5=0  -> "FizzBuzz"

    Time Complexity: O(n)
        We iterate from 1 to n once.
        Like counting items one by one - more items = more time.

    Space Complexity: O(n)
        We store n strings in the result list.
        Like writing n labels - more batches = more labels.

    Args:
        n: The number of elements to generate.

    Returns:
        List of FizzBuzz strings.
    """
    result = []

    for i in range(1, n + 1):
        # Check divisibility using modulo operator (%).
        # If remainder is 0, the number is divisible.
        divisible_by_3 = (i % 3 == 0)
        divisible_by_5 = (i % 5 == 0)

        # Check both conditions first (order matters!).
        if divisible_by_3 and divisible_by_5:
            result.append("FizzBuzz")
        elif divisible_by_3:
            result.append("Fizz")
        elif divisible_by_5:
            result.append("Buzz")
        else:
            result.append(str(i))

    return result


def main():
    """Main function to demonstrate the FizzBuzzBatchProcessor."""
    print("=== FizzBuzzBatchProcessor: Conditional Logic Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate FizzBuzz with offer batch processing simulation
    print("--- Batch Processing Simulation (15 batches) ---\n")
    results = fizz_buzz(15)

    for i, result in enumerate(results):
        if result == "Fizz":
            action = "-> Route to Marketing Team"
        elif result == "Buzz":
            action = "-> Route to Finance Team"
        elif result == "FizzBuzz":
            action = "-> Route to Both Teams"
        else:
            action = "-> Process normally"
        print(f"Batch {i + 1:2d}: {result:8s} {action}")


if __name__ == "__main__":
    main()
