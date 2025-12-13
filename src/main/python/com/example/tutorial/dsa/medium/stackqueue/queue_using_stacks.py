"""
QueueUsingStacks
----------------------------------
This program implements a FIFO queue using two stacks.
The core problem solved here is Implement Queue using Stacks (LeetCode #232).

Problem Statement:
    Implement a first in first out (FIFO) queue using only two stacks. The queue
    should support all functions: push, pop, peek, and empty.

Real UseCase:
    In a credit card offers system:
    - Process offer redemption requests in order received
    - Handle customer service tickets FIFO when only stack-based storage available
    - Manage batch processing jobs in order

Operations:
    - push(x) - Push element to back of queue
    - pop() - Remove element from front of queue
    - peek() - Get front element
    - empty() - Check if queue is empty

Company Tags: Amazon, Microsoft, Apple, Bloomberg

See: https://leetcode.com/problems/implement-queue-using-stacks/
"""

import sys
import os

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


class MyQueue:
    """
    MyQueue - Queue implemented using two stacks.

    DESIGN (Two-Stack Reversal):
        1. input_stack: receives all push operations
        2. output_stack: used for pop/peek operations
        3. When output_stack is empty and we need pop/peek:
           transfer all elements from input_stack to output_stack
        4. Transfer reverses order, giving us FIFO behavior!

    Why does this work?
        Stack is LIFO (Last In First Out). By moving elements from one stack to another,
        we reverse the order. So the first element pushed into input_stack becomes the top
        of output_stack - exactly what we need for a queue!

    Example Walkthrough:
        push(1): input_stack=[1], output_stack=[]
        push(2): input_stack=[1,2], output_stack=[]
        push(3): input_stack=[1,2,3], output_stack=[]

        pop(): output_stack empty, transfer!
               input_stack=[], output_stack=[3,2,1]
               pop from output_stack -> returns 1
               output_stack=[3,2]

        push(4): input_stack=[4], output_stack=[3,2]

        pop(): output_stack not empty, pop directly -> returns 2
               output_stack=[3]

    Time Complexity:
        - Push: O(1) always
        - Pop/Peek: Amortized O(1) - each element transferred at most once
        Like moving papers between two trays - each paper is moved at most twice
        in its lifetime (once in, once to output tray). Average cost per operation = constant.

    Space Complexity: O(n)
        Total elements stored across both stacks = n.
        Like having two in-trays - papers are in one or the other, never both.
    """

    def __init__(self):
        """Initialize the queue."""
        # Stack for push operations.
        self.input_stack = []

        # Stack for pop/peek operations (reversed order).
        self.output_stack = []

    def push(self, x: int) -> None:
        """
        Pushes element to the back of queue.

        Args:
            x: The element to push.
        """
        self.input_stack.append(x)

    def pop(self) -> int:
        """
        Removes and returns the front element.

        Returns:
            The front element.
        """
        # Ensure output_stack has elements.
        self._transfer_if_needed()
        return self.output_stack.pop()

    def peek(self) -> int:
        """
        Gets the front element without removing it.

        Returns:
            The front element.
        """
        self._transfer_if_needed()
        return self.output_stack[-1]

    def empty(self) -> bool:
        """
        Checks if queue is empty.

        Returns:
            True if empty.
        """
        return not self.input_stack and not self.output_stack

    def _transfer_if_needed(self) -> None:
        """
        Transfers elements from input_stack to output_stack if output_stack is empty.
        This reverses the order, converting LIFO to FIFO.
        """
        if not self.output_stack:
            while self.input_stack:
                self.output_stack.append(self.input_stack.pop())


def main():
    """Main function to demonstrate the QueueUsingStacks."""
    print("=== QueueUsingStacks: Queue Implementation Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate with customer service tickets
    print("--- Processing Service Tickets (FIFO) ---\n")
    ticket_queue = MyQueue()

    tickets = ["Ticket-101", "Ticket-102", "Ticket-103", "Ticket-104"]
    print("Submitting tickets:")
    for ticket in tickets:
        ticket_queue.push(hash(ticket))
        print(f"  Submitted: {ticket}")

    print("\nProcessing tickets (FIFO order):")
    while not ticket_queue.empty():
        print(f"  Processing ticket with hash: {ticket_queue.pop()}")

    # Standard LeetCode example
    print("\n--- LeetCode Example ---\n")
    queue = MyQueue()
    queue.push(1)
    print("push(1)")
    queue.push(2)
    print("push(2)")
    print(f"peek() -> {queue.peek()}")    # 1
    print(f"pop() -> {queue.pop()}")      # 1
    print(f"empty() -> {queue.empty()}")  # False


if __name__ == "__main__":
    main()
