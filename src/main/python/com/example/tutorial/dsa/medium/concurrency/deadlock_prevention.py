"""
DeadlockPrevention
----------------------------------
This program demonstrates deadlock scenarios and prevention techniques.
Shows how to identify, prevent, and resolve deadlock situations.

Problem Statement:
    Prevent threads from getting stuck waiting for each other indefinitely
    when acquiring multiple locks.

Real UseCase:
    In a credit card offers system:
    - Concurrent account transfers
    - Multi-resource booking systems
    - Order processing with inventory locks

Company Tags: Amazon, Google, Microsoft, Goldman Sachs
"""

import sys
import os
import threading
import time
from typing import Optional

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


class BankAccount:
    """Simple bank account for deadlock demos."""

    def __init__(self, account_id: int, name: str, balance: int):
        self.id = account_id
        self.name = name
        self.balance = balance
        self.lock = threading.Lock()


class SafeBankAccount:
    """
    Thread-safe bank account using lock ordering.

    LOGIC: Always acquire locks in a consistent order (by ID).
    This breaks the circular wait condition for deadlock.
    """

    def __init__(self, account_id: int, name: str, balance: int):
        self.id = account_id
        self.name = name
        self.balance = balance
        self.lock = threading.RLock()

    def transfer_to(self, target: 'SafeBankAccount', amount: int) -> bool:
        """Transfers amount to target account using lock ordering."""
        # Lock ordering by ID.
        first = self if self.id < target.id else target
        second = target if self.id < target.id else self

        with first.lock:
            with second.lock:
                if self.balance >= amount:
                    self.balance -= amount
                    target.balance += amount
                    print(f"    {threading.current_thread().name}: Transferred ${amount}")
                    return True
                return False

    def get_balance(self) -> int:
        with self.lock:
            return self.balance


class TryLockAccount:
    """Bank account using tryLock pattern for deadlock prevention."""

    def __init__(self, account_id: int, name: str, balance: int):
        self.id = account_id
        self.name = name
        self.balance = balance
        self.lock = threading.Lock()

    def transfer_to(self, target: 'TryLockAccount', amount: int,
                    max_retries: int = 5) -> bool:
        """Transfers using tryLock with timeout and retry."""
        for attempt in range(max_retries):
            got_self = self.lock.acquire(blocking=False)
            if got_self:
                try:
                    got_target = target.lock.acquire(blocking=False)
                    if got_target:
                        try:
                            if self.balance >= amount:
                                self.balance -= amount
                                target.balance += amount
                                print(f"    {threading.current_thread().name}: "
                                      f"Transferred ${amount}")
                                return True
                            return False
                        finally:
                            target.lock.release()
                finally:
                    self.lock.release()

            # Backoff before retry
            print(f"    {threading.current_thread().name}: "
                  f"Couldn't get both locks, retry {attempt + 1}")
            time.sleep(0.05)

        return False


def explain_deadlock():
    """Explains deadlock conditions and prevention strategies."""
    print("  Deadlock occurs when:")
    print("  1. Thread-1 holds Lock-A, waits for Lock-B")
    print("  2. Thread-2 holds Lock-B, waits for Lock-A")
    print("  -> Both threads wait forever!\n")

    print("  Four conditions for deadlock:")
    print("  1. Mutual Exclusion - resources can't be shared")
    print("  2. Hold and Wait - holding one, waiting for another")
    print("  3. No Preemption - can't force release of locks")
    print("  4. Circular Wait - circular chain of waiting\n")

    print("  Prevention strategies:")
    print("  * Lock ordering (always acquire in same order)")
    print("  * Lock timeout (tryLock with timeout)")
    print("  * Detect and recover")
    print("  * Single lock (coarse-grained locking)")


def demo_lock_ordering():
    """Demonstrates deadlock prevention using consistent lock ordering."""
    account1 = SafeBankAccount(1, "Account-1", 1000)
    account2 = SafeBankAccount(2, "Account-2", 1000)

    print(f"  Initial balances: {account1.name}=${account1.balance}, "
          f"{account2.name}=${account2.balance}")

    def transfer_1_to_2():
        account1.transfer_to(account2, 100)

    def transfer_2_to_1():
        account2.transfer_to(account1, 50)

    t1 = threading.Thread(target=transfer_1_to_2, name="Transfer-1-to-2")
    t2 = threading.Thread(target=transfer_2_to_1, name="Transfer-2-to-1")

    t1.start()
    t2.start()
    t1.join()
    t2.join()

    print(f"  Final balances: {account1.name}=${account1.balance}, "
          f"{account2.name}=${account2.balance}")
    print("  No deadlock - locks acquired in consistent order!")


def demo_try_lock():
    """Demonstrates deadlock prevention using tryLock with timeout."""
    account1 = TryLockAccount(1, "Account-1", 1000)
    account2 = TryLockAccount(2, "Account-2", 1000)

    print(f"  Initial balances: {account1.name}=${account1.balance}, "
          f"{account2.name}=${account2.balance}")

    def transfer_1_to_2():
        account1.transfer_to(account2, 100)

    def transfer_2_to_1():
        account2.transfer_to(account1, 50)

    t1 = threading.Thread(target=transfer_1_to_2, name="Transfer-1-to-2")
    t2 = threading.Thread(target=transfer_2_to_1, name="Transfer-2-to-1")

    t1.start()
    t2.start()
    t1.join()
    t2.join()

    print(f"  Final balances: {account1.name}=${account1.balance}, "
          f"{account2.name}=${account2.balance}")
    print("  Both threads completed without deadlock!")


def demo_safe_transfer():
    """Demonstrates safe concurrent transfers."""
    acc1 = SafeBankAccount(1, "Alice", 500)
    acc2 = SafeBankAccount(2, "Bob", 500)

    print(f"  Initial: Alice=${acc1.get_balance()}, Bob=${acc2.get_balance()}")

    threads = [
        threading.Thread(target=acc1.transfer_to, args=(acc2, 100), name="Alice->Bob"),
        threading.Thread(target=acc2.transfer_to, args=(acc1, 75), name="Bob->Alice"),
        threading.Thread(target=acc1.transfer_to, args=(acc2, 50), name="Alice->Bob-2"),
        threading.Thread(target=acc2.transfer_to, args=(acc1, 25), name="Bob->Alice-2"),
    ]

    for t in threads:
        t.start()
    for t in threads:
        t.join()

    print(f"  Final: Alice=${acc1.get_balance()}, Bob=${acc2.get_balance()}")
    print(f"  Total preserved: ${acc1.get_balance() + acc2.get_balance()}")


def main():
    """Main function to demonstrate the DeadlockPrevention."""
    print("=== DeadlockPrevention: Deadlock Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demo 1: Explain deadlock
    print("--- Demo 1: Deadlock Scenario (explained) ---\n")
    explain_deadlock()

    # Demo 2: Prevention using lock ordering
    print("\n--- Demo 2: Prevention - Lock Ordering ---\n")
    demo_lock_ordering()

    # Demo 3: Prevention using tryLock
    print("\n--- Demo 3: Prevention - TryLock with Retry ---\n")
    demo_try_lock()

    # Demo 4: Safe transfer example
    print("\n--- Demo 4: Safe Account Transfer ---\n")
    demo_safe_transfer()


if __name__ == "__main__":
    main()
