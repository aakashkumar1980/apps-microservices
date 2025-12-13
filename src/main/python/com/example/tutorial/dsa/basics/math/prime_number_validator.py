"""
PrimeNumberValidator
----------------------------------
This program demonstrates prime number validation and the Sieve of Eratosthenes.
The core problem solved here is counting primes (LeetCode #204).

Problem Statement:
    Given an integer n, return the number of prime numbers that are strictly less than n.

Real UseCase:
    In a credit card offers system:
    - Hashing algorithms - prime numbers are used in hash functions
    - Cryptography basics - prime factorization for security

Examples:
    - Input: n = 10 -> Output: 4 (primes: 2, 3, 5, 7)
    - Input: n = 0 -> Output: 0

Company Tags: Amazon

See: https://leetcode.com/problems/count-primes/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def count_primes(n: int) -> int:
    """
    Counts the number of primes less than n using Sieve of Eratosthenes.

    LOGIC:
        1. Create a boolean array to mark composite (non-prime) numbers
        2. Start with 2 (first prime)
        3. Mark all multiples of 2 as composite
        4. Move to next unmarked number (3), mark its multiples
        5. Continue until sqrt(n)
        6. Count remaining unmarked numbers

    Example Walkthrough:
        n = 10, initially all marked as prime: [F,F,T,T,T,T,T,T,T,T]
                                                0 1 2 3 4 5 6 7 8 9

        Step 1: i=2, mark multiples 4,6,8: [F,F,T,T,F,T,F,T,F,T]
        Step 2: i=3, mark multiples 6,9:   [F,F,T,T,F,T,F,T,F,F]

        Count T's from index 2: 2,3,5,7 = 4 primes

    Time Complexity: O(n log log n)
        The Sieve of Eratosthenes is highly optimized for counting primes.
        Like crossing out numbers in a grid - very efficient.

    Space Complexity: O(n)
        We use a boolean array of size n.
        Like having a checklist with n items.

    Args:
        n: The upper limit (exclusive).

    Returns:
        Count of primes less than n.
    """
    if n <= 2:
        return 0

    # Boolean array where is_prime[i] indicates if i is prime.
    # Initially assume all numbers are prime (True).
    is_prime = [True] * n

    # 0 and 1 are not prime.
    is_prime[0] = False
    is_prime[1] = False

    # Sieve: mark multiples of each prime as composite.
    # We only need to check up to sqrt(n).
    i = 2
    while i * i < n:
        if is_prime[i]:
            # Mark all multiples of i as not prime.
            # Start from i*i because smaller multiples were already marked.
            for j in range(i * i, n, i):
                is_prime[j] = False
        i += 1

    # Count primes.
    return sum(is_prime)


def list_primes(n: int) -> List[int]:
    """
    Returns a list of all primes less than n.

    Args:
        n: The upper limit (exclusive).

    Returns:
        List of primes.
    """
    if n <= 2:
        return []

    is_prime = [True] * n
    is_prime[0] = False
    is_prime[1] = False

    i = 2
    while i * i < n:
        if is_prime[i]:
            for j in range(i * i, n, i):
                is_prime[j] = False
        i += 1

    return [i for i in range(n) if is_prime[i]]


def main():
    """Main function to demonstrate the PrimeNumberValidator."""
    print("=== PrimeNumberValidator: Prime Number Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate prime counting
    print("--- Counting Primes ---\n")
    test_cases = [10, 20, 50, 100]

    for n in test_cases:
        count = count_primes(n)
        primes = list_primes(n)
        print(f"Primes less than {n}: {count}")
        print(f"List: {primes}\n")


if __name__ == "__main__":
    main()
