"""
CustomComparatorSorter
----------------------------------
This program demonstrates custom sorting with Comparators.
The core problem solved here is Custom Sort String (LeetCode #791).

Problem Statement:
    Given a custom order string, sort another string according to this custom order.

Real UseCase:
    In a credit card offers system:
    - Sort offers by custom priority (VIP, Premium, Standard)
    - Order transactions by custom category ranking
    - Arrange merchants by partnership tier

Examples:
    - order = "cba", s = "abcd" -> Output: "cbad"
    - order = "cbafg", s = "abcd" -> Output: "cbad"

Company Tags: Facebook, Amazon

See: https://leetcode.com/problems/custom-sort-string/
"""

import sys
import os
from collections import Counter

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def custom_sort_string(order: str, s: str) -> str:
    """
    Sorts string s according to custom character order.

    LOGIC (Frequency Count + Rebuild):
        1. Count frequency of each character in s
        2. For each character in order, append it freq times
        3. Append remaining characters (not in order) at the end

    Example Walkthrough:
        order = "cba", s = "abcd"

        Count: {a:1, b:1, c:1, d:1}

        Process order "cba":
          c: append 'c' 1 time -> "c", count[c]=0
          b: append 'b' 1 time -> "cb", count[b]=0
          a: append 'a' 1 time -> "cba", count[a]=0

        Remaining (not in order):
          d: append 'd' 1 time -> "cbad"

        Result: "cbad"

    Time Complexity: O(n)
        Where n = len(s). We scan s twice (count and build).
        Like organizing books by custom shelf order - count books first,
        then place by the custom order, finally place remaining books.

    Space Complexity: O(n)
        For the result string and frequency counter.

    Args:
        order: The custom character order.
        s: The string to sort.

    Returns:
        String sorted according to custom order.
    """
    # Count frequency of each character in s.
    count = Counter(s)

    result = []

    # First, add characters in the custom order.
    for c in order:
        if c in count:
            result.append(c * count[c])
            del count[c]

    # Then, add remaining characters (not in order).
    for c, freq in count.items():
        result.append(c * freq)

    return ''.join(result)


def custom_sort_string_with_comparator(order: str, s: str) -> str:
    """
    Alternative approach using sorted() with custom key function.

    Args:
        order: The custom character order.
        s: The string to sort.

    Returns:
        String sorted according to custom order.
    """
    # Build order map: character -> priority index.
    order_map = {c: i for i, c in enumerate(order)}

    # Sort with custom key (characters not in order go to end).
    sorted_chars = sorted(s, key=lambda c: order_map.get(c, len(order)))

    return ''.join(sorted_chars)


def main():
    """Main function to demonstrate the CustomComparatorSorter."""
    print("=== CustomComparatorSorter: Custom Sorting Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate custom string sorting
    print("--- Custom String Sort ---\n")
    order = "cba"
    s = "abcd"
    print(f"Custom order: \"{order}\"")
    print(f"Input string: \"{s}\"")
    print(f"Sorted: \"{custom_sort_string(order, s)}\"\n")

    # Demonstrate sorting objects with custom comparators
    print("--- Sorting Offers by Custom Priority ---\n")

    # Create sample offers with different priorities
    offer_data = [
        ("Offer-A", "Standard"),
        ("Offer-B", "VIP"),
        ("Offer-C", "Premium"),
        ("Offer-D", "VIP"),
        ("Offer-E", "Standard")
    ]

    # Custom priority order: VIP > Premium > Standard
    priority_order = ["VIP", "Premium", "Standard"]

    print("Original order:")
    for offer in offer_data:
        print(f"  {offer[0]} ({offer[1]})")

    # Sort by custom priority
    sorted_offers = sorted(offer_data, key=lambda x: priority_order.index(x[1]))

    print("\nSorted by priority (VIP > Premium > Standard):")
    for offer in sorted_offers:
        print(f"  {offer[0]} ({offer[1]})")

    # Additional test cases
    print("\n--- Additional Examples ---\n")
    test_cases = [
        ("cba", "abcd"),
        ("cbafg", "abcd"),
        ("xyz", "abcdef")
    ]

    for order, s in test_cases:
        result = custom_sort_string(order, s)
        print(f"order=\"{order}\", s=\"{s}\" -> \"{result}\"")


if __name__ == "__main__":
    main()
