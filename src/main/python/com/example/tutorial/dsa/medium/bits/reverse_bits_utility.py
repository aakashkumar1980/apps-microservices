"""
ReverseBitsUtility
----------------------------------
This program reverses bits of a 32-bit unsigned integer.
The core problem solved here is Reverse Bits (LeetCode #190).

Problem Statement:
    Reverse bits of a given 32 bits unsigned integer.

Real UseCase:
    In a credit card offers system:
    - Data encoding/decoding transformations
    - Hash function implementations
    - Protocol conversions

Company Tags: Amazon, Apple, Google

See: https://leetcode.com/problems/reverse-bits/
"""

import sys
import os

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def reverse_bits(n: int) -> int:
    """
    Reverses bits of a 32-bit unsigned integer.

    LOGIC (Bit-by-Bit Reversal):
        1. Extract each bit from right (LSB) of input
        2. Shift result left and add the extracted bit
        3. Repeat 32 times

    Example Walkthrough:
        n = 1011 (4 bits for simplicity)

        i=0: result=0, bit=1 -> result = 0|1 = 1, n=101
        i=1: result=10, bit=1 -> result = 10|1 = 11, n=10
        i=2: result=110, bit=0 -> result = 110|0 = 110, n=1
        i=3: result=1100, bit=1 -> result = 1100|1 = 1101, n=0

        Result: 1011 -> 1101

    Time Complexity: O(1)
        Fixed 32 iterations.
        Like reading a word backwards - extract each letter
        from the end and build a new word from the start.

    Space Complexity: O(1)

    Args:
        n: The 32-bit unsigned integer.

    Returns:
        Integer with reversed bits.
    """
    result = 0

    for _ in range(32):
        result <<= 1          # Shift result left.
        result |= (n & 1)     # Add rightmost bit of n.
        n >>= 1               # Shift n right.

    return result


def reverse_bits_optimized(n: int) -> int:
    """
    Optimized reverse using divide and conquer.

    LOGIC: Swap adjacent bits, then pairs, then nibbles, etc.
    Like mergesort in reverse.
    """
    # Swap adjacent bits.
    n = ((n & 0x55555555) << 1) | ((n >> 1) & 0x55555555)
    # Swap adjacent pairs.
    n = ((n & 0x33333333) << 2) | ((n >> 2) & 0x33333333)
    # Swap adjacent nibbles.
    n = ((n & 0x0f0f0f0f) << 4) | ((n >> 4) & 0x0f0f0f0f)
    # Swap adjacent bytes.
    n = ((n & 0x00ff00ff) << 8) | ((n >> 8) & 0x00ff00ff)
    # Swap 16-bit halves.
    n = (n << 16) | (n >> 16)

    return n & 0xffffffff  # Ensure 32-bit


def reverse_significant_bits(n: int) -> int:
    """Reverses only the significant bits (not full 32)."""
    if n == 0:
        return 0

    # Find number of significant bits.
    bits = n.bit_length()

    result = 0
    for _ in range(bits):
        result <<= 1
        result |= (n & 1)
        n >>= 1

    return result


def swap_bits(n: int, i: int, j: int) -> int:
    """Swaps bits at two positions."""
    # Check if bits at positions i and j are different.
    if ((n >> i) & 1) != ((n >> j) & 1):
        # Toggle both bits using XOR.
        n ^= (1 << i) | (1 << j)
    return n


def demo_bit_operations():
    """Demonstrates various bit operations."""
    n = 0b10110100
    print(f"  n = {pad_binary8(n)} ({n})")

    # Get bit at position
    pos = 4
    bit = (n >> pos) & 1
    print(f"  Bit at position {pos}: {bit}")

    # Set bit at position
    set_result = n | (1 << 3)
    print(f"  Set bit 3: {pad_binary8(set_result)}")

    # Clear bit at position
    clear_result = n & ~(1 << 4)
    print(f"  Clear bit 4: {pad_binary8(clear_result)}")

    # Toggle bit at position
    toggle_result = n ^ (1 << 2)
    print(f"  Toggle bit 2: {pad_binary8(toggle_result)}")

    # Count leading zeros (Python doesn't have direct method)
    leading = 32 - n.bit_length() if n > 0 else 32
    print(f"  Leading zeros (32-bit): {leading}")

    # Count trailing zeros
    trailing = (n & -n).bit_length() - 1 if n > 0 else 32
    print(f"  Trailing zeros: {trailing}")


def pad_binary32(n: int) -> str:
    """Pads to 32 bits."""
    return bin(n & 0xffffffff)[2:].zfill(32)


def pad_binary8(n: int) -> str:
    """Pads to 8 bits."""
    return bin(n & 0xff)[2:].zfill(8)


def main():
    """Main function to demonstrate the ReverseBitsUtility."""
    print("=== ReverseBitsUtility: Bit Reversal Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demo 1: Reverse Bits
    print("--- Demo 1: Reverse Bits (LeetCode #190) ---\n")
    test_nums = [43261596, 4294967293]
    for n in test_nums:
        reversed_n = reverse_bits(n)
        print(f"  Original: {pad_binary32(n)}")
        print(f"  Reversed: {pad_binary32(reversed_n)}")
        print(f"  Decimal:  {n} -> {reversed_n}\n")

    # Demo 2: Reverse significant bits
    print("--- Demo 2: Reverse Significant Bits ---\n")
    num = 13  # 1101 in binary
    print(f"  Number: {num} ({bin(num)})")
    reversed_sig = reverse_significant_bits(num)
    print(f"  Reversed significant bits: {reversed_sig} ({bin(reversed_sig)})")

    # Demo 3: Bit operations
    print("\n--- Demo 3: Bit Operations Toolkit ---\n")
    demo_bit_operations()

    # Demo 4: Swap bits
    print("\n--- Demo 4: Swap Bits at Positions ---\n")
    original = 0b10110010
    print(f"  Original: {pad_binary8(original)} ({original})")

    swapped = swap_bits(original, 1, 5)
    print(f"  Swap positions 1 and 5: {pad_binary8(swapped)} ({swapped})")


if __name__ == "__main__":
    main()
