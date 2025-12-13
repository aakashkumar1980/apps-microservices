"""
FilePathSimplifier
----------------------------------
This program simplifies Unix-style file paths using Stack.
The core problem solved here is Simplify Path (LeetCode #71).

Problem Statement:
    Given a string path representing an absolute Unix file path, simplify it
    by converting it to the canonical path. The canonical path should:
    - Start with '/'
    - Have a single '/' between directories
    - Not end with '/' (unless it's the root)
    - Not contain '.' or '..' as directory names

Real UseCase:
    In a credit card offers system:
    - Normalize API endpoint paths for routing
    - Clean up resource URLs in offer configurations
    - Process navigation paths in admin dashboards

Examples:
    - Input: "/home/" -> Output: "/home"
    - Input: "/../" -> Output: "/" (can't go above root)
    - Input: "/home//foo/" -> Output: "/home/foo"
    - Input: "/a/./b/../../c/" -> Output: "/c"

Company Tags: Facebook, Microsoft, Google

See: https://leetcode.com/problems/simplify-path/
"""

import sys
import os

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def simplify_path(path: str) -> str:
    """
    Simplifies Unix file path using Stack to track directory hierarchy.

    LOGIC (Stack for Directory Tracking):
        1. Split path by '/' to get individual components
        2. Use stack (list) to maintain current directory hierarchy
        3. For each component:
           - Empty or "." -> skip (current directory)
           - ".." -> pop from stack if not empty (go up one level)
           - Otherwise -> push directory name to stack
        4. Build result by joining stack elements with '/'

    Example Walkthrough:
        path = "/a/./b/../../c/"
        Split: ["", "a", ".", "b", "..", "..", "c", ""]
        stack = []

        "" -> skip (empty)
        "a" -> push -> stack = ["a"]
        "." -> skip (current dir)
        "b" -> push -> stack = ["a", "b"]
        ".." -> pop -> stack = ["a"]
        ".." -> pop -> stack = []
        "c" -> push -> stack = ["c"]
        "" -> skip (empty)

        Result: "/" + "c" = "/c"

    Time Complexity: O(n)
        We process each character once during split and each component once.
        Like walking through a file system step by step - if the path has
        n characters, you take about n steps to navigate and clean it up.

    Space Complexity: O(n)
        Stack and split array may hold up to n/2 directories.
        Like keeping breadcrumbs as you walk - worst case every other
        character is a directory name, so n/2 breadcrumbs needed.

    Args:
        path: The Unix file path to simplify.

    Returns:
        The canonical (simplified) path.
    """
    # Edge case.
    if not path:
        return "/"

    # Stack to track directory hierarchy (use list as stack).
    stack = []

    # Split by '/' to get path components.
    components = path.split("/")

    for component in components:
        # Skip empty strings and current directory markers.
        if not component or component == ".":
            continue

        # Go up one directory level.
        if component == "..":
            if stack:
                stack.pop()
            # If stack is empty, we're at root - can't go higher.
        else:
            # Valid directory name - push to stack.
            stack.append(component)

    # Build canonical path.
    return "/" + "/".join(stack)


def main():
    """Main function to demonstrate the FilePathSimplifier."""
    print("=== FilePathSimplifier: Path Normalization Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate with API endpoint paths
    print("--- Simplifying API Endpoint Paths ---\n")
    api_paths = [
        "/api/v1/../v2/offers/",
        "/api/offers/./category/../merchant//details",
        "/api/../../public/"
    ]

    for path in api_paths:
        simplified = simplify_path(path)
        print(f"Original:   \"{path}\"")
        print(f"Simplified: \"{simplified}\"\n")

    # Test cases
    print("--- Additional Examples ---\n")
    test_cases = [
        "/home/",
        "/../",
        "/home//foo/",
        "/a/./b/../../c/",
        "/a/b/c/../../../.."
    ]

    for path in test_cases:
        result = simplify_path(path)
        print(f"Input:  \"{path}\"")
        print(f"Output: \"{result}\"\n")


if __name__ == "__main__":
    main()
