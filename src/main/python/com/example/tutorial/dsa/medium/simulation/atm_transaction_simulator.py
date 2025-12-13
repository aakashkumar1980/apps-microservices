"""
ATMTransactionSimulator
----------------------------------
This program simulates an ATM machine handling deposits and withdrawals.
The core problem solved here is Design an ATM Machine (LeetCode #2241).

Problem Statement:
    Design an ATM machine that can deposit and withdraw bills of denominations
    $20, $50, $100, $200, and $500. Withdraw should use the greedy approach,
    taking as many larger bills as possible first.

Real UseCase:
    In a credit card offers system:
    - Cash advance transactions from credit cards
    - Rewards redemption in cash
    - Currency exchange simulations

Company Tags: Amazon, Bloomberg, Goldman Sachs

See: https://leetcode.com/problems/design-an-atm-machine/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


class ATM:
    """
    ATM class that handles deposits and withdrawals.

    LOGIC (Greedy Bill Selection):
        1. For withdrawals, process from largest to smallest denomination
        2. Take as many bills as possible without exceeding the amount
        3. If exact amount can't be achieved, return failure (rollback)

    Example Walkthrough:
        ATM has: 0x$20, 0x$50, 1x$100, 2x$200, 1x$500
        Withdraw $600:

        Try $500: need 600/500=1 bill, have 1 -> take 1, remaining = 100
        Try $200: need 100/200=0 bills -> take 0, remaining = 100
        Try $100: need 100/100=1 bill, have 1 -> take 1, remaining = 0

        Success! Return [0, 0, 1, 0, 1] (0x$20, 0x$50, 1x$100, 0x$200, 1x$500)

    Time Complexity: O(1)
        Fixed 5 denominations, constant operations.

    Space Complexity: O(1)
        Fixed size arrays for bill counts.
    """

    DENOMINATIONS = [20, 50, 100, 200, 500]

    def __init__(self):
        """Initializes an empty ATM."""
        self.bill_count = [0] * 5

    def deposit(self, banknotes_count: List[int]) -> None:
        """
        Deposits bills into the ATM.

        Args:
            banknotes_count: Array of counts: [$20, $50, $100, $200, $500]
        """
        for i in range(5):
            self.bill_count[i] += banknotes_count[i]

    def withdraw(self, amount: int) -> List[int]:
        """
        Attempts to withdraw the specified amount.

        Args:
            amount: The amount to withdraw.

        Returns:
            Array of bill counts if successful, [-1] if not possible.
        """
        result = [0] * 5

        # Greedy: try from largest denomination to smallest.
        remaining = amount
        for i in range(4, -1, -1):
            # How many bills of this denomination can we use?
            needed = remaining // self.DENOMINATIONS[i]
            can_use = min(needed, self.bill_count[i])

            result[i] = can_use
            remaining -= can_use * self.DENOMINATIONS[i]

        # Check if we achieved exact amount.
        if remaining != 0:
            return [-1]  # Cannot dispense exact amount.

        # Deduct bills from ATM.
        for i in range(5):
            self.bill_count[i] -= result[i]

        return result

    def print_status(self) -> None:
        """Prints current ATM status."""
        print("ATM Status:")
        total = 0
        for i in range(5):
            total += self.bill_count[i] * self.DENOMINATIONS[i]
            print(f"  ${self.DENOMINATIONS[i]}: {self.bill_count[i]} bills")
        print(f"  Total: ${total}")


def print_withdraw_result(amount: int, result: List[int]) -> None:
    """Helper to print withdrawal result."""
    if result[0] == -1:
        print(f"Withdraw ${amount}: FAILED (cannot dispense exact amount)\n")
    else:
        print(f"Withdraw ${amount}: SUCCESS")
        print(f"  Bills: {result[0]}x$20, {result[1]}x$50, "
              f"{result[2]}x$100, {result[3]}x$200, {result[4]}x$500\n")


def main():
    """Main function to demonstrate the ATMTransactionSimulator."""
    print("=== ATMTransactionSimulator: ATM Machine Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Create ATM instance
    atm = ATM()

    # Demonstrate deposits
    print("--- Depositing Cash ---\n")
    atm.deposit([0, 0, 1, 2, 1])  # 1x$100 + 2x$200 + 1x$500 = $1000
    print("Deposited: 1x$100, 2x$200, 1x$500 = $1000")
    atm.print_status()

    # Demonstrate withdrawals
    print("\n--- Withdrawing Cash ---\n")

    result1 = atm.withdraw(600)
    print_withdraw_result(600, result1)
    atm.print_status()

    # Deposit more
    print("\n--- More Deposits ---\n")
    atm.deposit([0, 1, 0, 1, 1])  # 1x$50 + 1x$200 + 1x$500 = $750
    print("Deposited: 1x$50, 1x$200, 1x$500 = $750")
    atm.print_status()

    # Try various withdrawals
    print("\n--- Various Withdrawals ---\n")

    result2 = atm.withdraw(550)
    print_withdraw_result(550, result2)
    atm.print_status()

    result3 = atm.withdraw(100)
    print_withdraw_result(100, result3)

    # Try impossible withdrawal
    result4 = atm.withdraw(30)
    print_withdraw_result(30, result4)


if __name__ == "__main__":
    main()
