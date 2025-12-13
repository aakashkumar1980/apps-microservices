"""
RuleValidatorEngine
----------------------------------
This program demonstrates bracket matching using a Stack data structure.
The core problem solved here is validating parentheses (LeetCode #20).

Problem Statement:
    Given a string s containing just the characters '(', ')', '{', '}', '[' and ']',
    determine if the input string is valid.

Real UseCase:
    In a credit card offers system:
    - JSON/XML validation - ensure config files have matching braces
    - Rule expression parsing - validate offer eligibility rules

Examples:
    - Input: "()" -> Output: True
    - Input: "()[]{}" -> Output: True
    - Input: "(]" -> Output: False

Company Tags: Amazon (⭐⭐), Facebook

See: https://leetcode.com/problems/valid-parentheses/
"""

import sys
import os

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def is_valid(s: str) -> bool:
    """
    Validates if a string has valid bracket matching using a Stack.

    LOGIC:
        1. Use a stack to track opening brackets
        2. When we see an opening bracket, push it onto the stack
        3. When we see a closing bracket, check if it matches the top of stack
        4. If stack is empty at the end, all brackets are matched

    Example Walkthrough:
        Input: "{[()]}"

        Step 1: '{' -> push to stack -> Stack: ['{']
        Step 2: '[' -> push to stack -> Stack: ['{', '[']
        Step 3: '(' -> push to stack -> Stack: ['{', '[', '(']
        Step 4: ')' -> matches '(' -> pop -> Stack: ['{', '[']
        Step 5: ']' -> matches '[' -> pop -> Stack: ['{']
        Step 6: '}' -> matches '{' -> pop -> Stack: []

        Stack is empty -> return True

    Time Complexity: O(n)
        We traverse the string once, and each push/pop operation is O(1).
        Like checking each card in a deck once - more cards = more checks.

    Space Complexity: O(n)
        In the worst case (all opening brackets), we store all n characters in the stack.
        Like stacking plates - if all brackets are open, we stack them all.

    Args:
        s: The input string containing brackets and other characters.

    Returns:
        True if all brackets are properly matched, False otherwise.
    """
    if s is None or len(s) == 0:
        return True

    # Map closing brackets to their corresponding opening brackets.
    bracket_pairs = {
        ')': '(',
        ']': '[',
        '}': '{'
    }

    # Use a list as a stack to track opening brackets.
    stack = []

    # Iterate through each character in the string.
    for c in s:
        # If it's a closing bracket, check if it matches the top of stack.
        if c in bracket_pairs:
            # If stack is empty or top doesn't match, invalid.
            if not stack or stack.pop() != bracket_pairs[c]:
                return False
        # If it's an opening bracket, push to stack.
        elif c in '([{':
            stack.append(c)
        # Ignore other characters (letters, numbers, spaces, etc.)

    # If stack is empty, all brackets matched.
    return len(stack) == 0


def main():
    """Main function to demonstrate the RuleValidatorEngine."""
    print("=== RuleValidatorEngine: Bracket Matching Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate with rule expressions
    print("--- Validating Offer Eligibility Rules ---\n")
    rules = [
        "(age >= 18) AND (income > 50000)",
        "((category == 'DINING') OR (category == 'TRAVEL'))",
        "(minSpend > 20) AND ((channel == 'ONLINE') OR (channel == 'IN_STORE'))",
        "((invalid rule with (missing bracket)",
        "{merchants: [M-STARBUCKS, M-TARGET]}"
    ]

    for rule in rules:
        is_valid_result = is_valid(rule)
        print(f"Rule: \"{rule}\"")
        print(f"Valid brackets: {is_valid_result}\n")


if __name__ == "__main__":
    main()
