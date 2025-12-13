"""
MinStackImplementation
----------------------------------
This program implements a stack that supports retrieving minimum element in O(1).
The core problem solved here is Min Stack (LeetCode #155).

Problem Statement:
    Design a stack that supports push, pop, top, and retrieving the minimum element
    in constant time.

Real UseCase:
    In a credit card offers system:
    - Track minimum transaction amount in a processing queue
    - Monitor lowest offer price in a dynamic list
    - Keep track of minimum wait time in customer service queue

Operations:
    - push(val) - Push element onto stack
    - pop() - Remove top element
    - top() - Get top element
    - get_min() - Get minimum element in O(1)

Company Tags: Amazon, Microsoft, Bloomberg, Apple

See: https://leetcode.com/problems/min-stack/
"""

import sys
import os

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


class MinStack:
    """
    MinStack - Stack with O(1) minimum retrieval.

    DESIGN (Two-Stack Approach):
        1. Main stack: stores all elements normally
        2. Min stack: stores minimum at each level
        3. When pushing: push to main, push min(val, current_min) to min_stack
        4. When popping: pop from both stacks
        5. get_min(): simply peek min_stack

    Example Walkthrough:
        Operations: push(5), push(3), push(7), push(2), pop(), pop()

        push(5): stack=[5], min_stack=[5]
        push(3): stack=[5,3], min_stack=[5,3] (3 < 5)
        push(7): stack=[5,3,7], min_stack=[5,3,3] (3 < 7)
        push(2): stack=[5,3,7,2], min_stack=[5,3,3,2] (2 < 3)

        get_min() -> peek min_stack -> 2

        pop(): stack=[5,3,7], min_stack=[5,3,3]
        get_min() -> 3

        pop(): stack=[5,3], min_stack=[5,3]
        get_min() -> 3

    Time Complexity: O(1) for all operations
        Push, pop, top, get_min are all constant time.
        Like having two stacks of plates - one regular, one tracking "smallest so far".
        Every operation just looks at the top plate - instant!

    Space Complexity: O(n)
        We use two stacks, each potentially holding n elements.
        Like keeping two identical-height stacks - double the storage,
        but still proportional to number of elements.
    """

    def __init__(self):
        """Initialize the MinStack."""
        # Main stack for all elements.
        self.stack = []

        # Parallel stack tracking minimum at each level.
        self.min_stack = []

    def push(self, val: int) -> None:
        """
        Pushes element onto the stack.

        Args:
            val: The value to push.
        """
        self.stack.append(val)

        # Push current minimum to min_stack.
        if not self.min_stack:
            self.min_stack.append(val)
        else:
            self.min_stack.append(min(val, self.min_stack[-1]))

    def pop(self) -> None:
        """Removes the top element from the stack."""
        if self.stack:
            self.stack.pop()
            self.min_stack.pop()

    def top(self) -> int:
        """
        Gets the top element.

        Returns:
            The top element.
        """
        return self.stack[-1]

    def get_min(self) -> int:
        """
        Retrieves the minimum element in the stack in O(1) time.

        Returns:
            The minimum element.
        """
        return self.min_stack[-1]

    def is_empty(self) -> bool:
        """
        Checks if stack is empty.

        Returns:
            True if empty.
        """
        return len(self.stack) == 0


def main():
    """Main function to demonstrate the MinStackImplementation."""
    print("=== MinStackImplementation: Min Stack Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate MinStack with transaction amounts
    print("--- Tracking Minimum Transaction Amount ---\n")
    min_stack = MinStack()

    transactions = [500, 200, 300, 100, 400]
    print("Processing transactions:")

    for amount in transactions:
        min_stack.push(amount)
        print(f"  Push {amount} -> Min so far: {min_stack.get_min()}")

    print("\nPopping elements:")
    while not min_stack.is_empty():
        print(f"  Top: {min_stack.top()}, Min: {min_stack.get_min()}")
        min_stack.pop()

    # Standard test case from LeetCode
    print("\n--- LeetCode Example ---\n")
    stack = MinStack()
    stack.push(-2)
    print("push(-2)")
    stack.push(0)
    print("push(0)")
    stack.push(-3)
    print("push(-3)")
    print(f"get_min() -> {stack.get_min()}")  # -3
    stack.pop()
    print("pop()")
    print(f"top() -> {stack.top()}")          # 0
    print(f"get_min() -> {stack.get_min()}")  # -2


if __name__ == "__main__":
    main()
