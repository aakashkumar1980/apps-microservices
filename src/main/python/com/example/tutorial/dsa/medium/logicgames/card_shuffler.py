"""
CardShuffler
----------------------------------
This program implements the Fisher-Yates shuffle algorithm for cards.
The core problem solved here is Shuffle an Array (LeetCode #384).

Problem Statement:
    Given an integer array nums, design an algorithm to randomly shuffle the array.
    All permutations should be equally likely.

Real UseCase:
    In a credit card offers system:
    - Randomize offer presentation order
    - Fair selection of promotional rewards
    - Random sampling of transactions for audit

Company Tags: Google, Facebook, Amazon, Apple

See: https://leetcode.com/problems/shuffle-an-array/
"""

import sys
import os
import random
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


class Solution:
    """
    Solution class for LeetCode #384.

    LOGIC (Fisher-Yates Shuffle):
        1. Iterate from last element to first
        2. For each position i, pick random index j from 0 to i
        3. Swap elements at i and j
        4. This ensures each permutation is equally likely

    Why It Works:
        For array of n elements:
        - Element at position n-1 has 1/n chance of staying (picking n-1)
        - Element at position n-2 has 1/(n-1) x (n-1)/n = 1/n chance
        - ... each position has exactly 1/n! chance of any permutation

    Time Complexity: O(n)
        Single pass through the array.
        Like shuffling a deck of cards by picking one card at a time
        from the unshuffled portion and placing it in the shuffled portion.

    Space Complexity: O(n)
        For storing the original array.
    """

    def __init__(self, nums: List[int]):
        """
        Initialize with array.

        Args:
            nums: The array to shuffle.
        """
        self.original = nums[:]
        self.current = nums[:]

    def reset(self) -> List[int]:
        """Resets the array to its original configuration."""
        self.current = self.original[:]
        return self.current[:]

    def shuffle(self) -> List[int]:
        """Returns a random shuffling of the array."""
        # Fisher-Yates shuffle.
        for i in range(len(self.current) - 1, 0, -1):
            j = random.randint(0, i)  # Random index from 0 to i.
            # Swap.
            self.current[i], self.current[j] = self.current[j], self.current[i]
        return self.current[:]


class CardDeck:
    """CardDeck class for simulating a deck of playing cards."""

    SUITS = ["♠", "♥", "♦", "♣"]
    RANKS = ["A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"]

    def __init__(self):
        """Initialize a new deck of cards."""
        self.deck: List[str] = []
        self._initialize_deck()

    def _initialize_deck(self) -> None:
        """Creates a new ordered deck."""
        self.deck = [rank + suit for suit in self.SUITS for rank in self.RANKS]

    def shuffle(self) -> None:
        """Shuffles the deck using Fisher-Yates algorithm."""
        for i in range(len(self.deck) - 1, 0, -1):
            j = random.randint(0, i)
            self.deck[i], self.deck[j] = self.deck[j], self.deck[i]

    def deal(self, n: int) -> List[str]:
        """Deals n cards from the deck."""
        hand = []
        for _ in range(min(n, len(self.deck))):
            hand.append(self.deck.pop(0))
        return hand

    def remaining_cards(self) -> int:
        """Returns number of cards remaining."""
        return len(self.deck)

    def print_top_cards(self, n: int) -> None:
        """Prints the top n cards."""
        print(" ".join(self.deck[:min(n, len(self.deck))]))


def test_shuffle_fairness() -> None:
    """Tests the fairness of the shuffle by counting position frequencies."""
    nums = [1, 2, 3]
    counts = [[0] * 3 for _ in range(3)]  # counts[value][position]
    trials = 30000

    for _ in range(trials):
        # Fisher-Yates shuffle.
        arr = nums[:]
        for i in range(len(arr) - 1, 0, -1):
            j = random.randint(0, i)
            arr[i], arr[j] = arr[j], arr[i]

        # Count positions.
        for i, val in enumerate(arr):
            counts[val - 1][i] += 1

    print(f"Position distribution after {trials} shuffles:")
    print(f"(Expected: ~{trials // 3} each = 33.3%)\n")
    print("        Pos 0   Pos 1   Pos 2")
    for v in range(3):
        print(f"Val {v + 1}:  {counts[v][0]:5d}   {counts[v][1]:5d}   {counts[v][2]:5d}")


def main():
    """Main function to demonstrate the CardShuffler."""
    print("=== CardShuffler: Fisher-Yates Shuffle Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate array shuffling
    print("--- Array Shuffle (LeetCode #384) ---\n")
    nums = [1, 2, 3, 4, 5]
    solution = Solution(nums)

    print(f"Original: {nums}")
    print(f"Shuffle 1: {solution.shuffle()}")
    print(f"Shuffle 2: {solution.shuffle()}")
    print(f"Reset: {solution.reset()}")
    print(f"Shuffle 3: {solution.shuffle()}")

    # Demonstrate card deck shuffling
    print("\n--- Card Deck Shuffling ---\n")
    deck = CardDeck()
    print("New deck (first 13 cards):")
    deck.print_top_cards(13)

    deck.shuffle()
    print("\nAfter shuffle (first 13 cards):")
    deck.print_top_cards(13)

    # Deal some cards
    print("\n--- Dealing Cards ---\n")
    print("Dealing 5 cards:")
    hand = deck.deal(5)
    for card in hand:
        print(f"  {card}")

    print(f"\nCards remaining: {deck.remaining_cards()}")

    # Test fairness of shuffle
    print("\n--- Shuffle Fairness Test ---\n")
    test_shuffle_fairness()


if __name__ == "__main__":
    main()
