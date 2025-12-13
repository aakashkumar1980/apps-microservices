"""
RateLimiterController
----------------------------------
This program implements rate limiting algorithms for API throttling.
The core problem solved here is Logger Rate Limiter (LeetCode #359).

Problem Statement:
    Design a logger system that receives a stream of messages and timestamps,
    and returns true if the message should be printed (not printed in the last 10 seconds).

Real UseCase:
    In a credit card offers system:
    - Rate limit API requests per user/merchant
    - Throttle duplicate transaction alerts
    - Control notification frequency to users

Company Tags: Google, Amazon, Facebook

See: https://leetcode.com/problems/logger-rate-limiter/
"""

import sys
import os
from collections import deque
from typing import Dict

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


class Logger:
    """
    Logger class implementing rate limiting for messages.

    LOGIC (HashMap with Timestamp Tracking):
        1. Store last printed timestamp for each message
        2. On new request, check if 10+ seconds have passed
        3. If yes, update timestamp and return true (print)
        4. If no, return false (skip)

    Time Complexity: O(1) per operation.
    Space Complexity: O(n) where n = unique messages.
    """

    def __init__(self):
        """Initialize the logger."""
        self.message_timestamps: Dict[str, int] = {}

    def should_print_message(self, timestamp: int, message: str) -> bool:
        """
        Returns true if the message should be printed (not printed in last 10 seconds).

        Args:
            timestamp: Current timestamp in seconds.
            message: The message to log.

        Returns:
            True if should print, False otherwise.
        """
        if message not in self.message_timestamps or \
           timestamp - self.message_timestamps[message] >= 10:
            self.message_timestamps[message] = timestamp
            return True
        return False


class SlidingWindowRateLimiter:
    """
    Sliding Window Rate Limiter using timestamp queue.

    LOGIC (Queue of Timestamps):
        1. For each user, maintain a queue of request timestamps
        2. On new request, remove expired timestamps (outside window)
        3. If queue size < limit, allow request and add timestamp
        4. Otherwise, reject request

    Time Complexity: O(w) where w = window size (cleanup).
    Space Complexity: O(n x w) where n = users, w = max requests per window.
    """

    def __init__(self, max_requests: int, window_seconds: int):
        """
        Initialize the rate limiter.

        Args:
            max_requests: Maximum requests allowed per window.
            window_seconds: Size of the sliding window in seconds.
        """
        self.max_requests = max_requests
        self.window_seconds = window_seconds
        self.user_requests: Dict[str, deque] = {}

    def allow_request(self, user_id: str, timestamp: int) -> bool:
        """
        Checks if request is allowed and records it if so.

        Args:
            user_id: The user making the request.
            timestamp: Current timestamp in seconds.

        Returns:
            True if request is allowed.
        """
        if user_id not in self.user_requests:
            self.user_requests[user_id] = deque()
        requests = self.user_requests[user_id]

        # Remove expired timestamps.
        while requests and timestamp - requests[0] >= self.window_seconds:
            requests.popleft()

        # Check if under limit.
        if len(requests) < self.max_requests:
            requests.append(timestamp)
            return True
        return False


class TokenBucket:
    """
    Token Bucket Rate Limiter.

    LOGIC (Token Bucket Algorithm):
        1. Bucket starts with max tokens
        2. Tokens refill at constant rate up to max
        3. Each request consumes tokens
        4. Request allowed only if enough tokens available

    Time Complexity: O(1) per operation.
    Space Complexity: O(1).
    """

    def __init__(self, max_tokens: int, refill_rate: float):
        """
        Initialize the token bucket.

        Args:
            max_tokens: Maximum capacity of the bucket.
            refill_rate: Tokens added per second.
        """
        self.max_tokens = max_tokens
        self.refill_rate = refill_rate
        self.tokens = float(max_tokens)
        self.last_refill_time = 0

    def refill(self, current_time: int) -> None:
        """
        Refills tokens based on elapsed time.

        Args:
            current_time: Current timestamp in seconds.
        """
        elapsed = current_time - self.last_refill_time
        self.tokens = min(self.max_tokens, self.tokens + elapsed * self.refill_rate)
        self.last_refill_time = current_time

    def try_consume(self, count: int) -> bool:
        """
        Attempts to consume tokens.

        Args:
            count: Number of tokens to consume.

        Returns:
            True if tokens were consumed successfully.
        """
        if self.tokens >= count:
            self.tokens -= count
            return True
        return False

    def get_tokens(self) -> float:
        """Returns current token count."""
        return self.tokens


def main():
    """Main function to demonstrate the RateLimiterController."""
    print("=== RateLimiterController: Rate Limiting Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate Logger Rate Limiter
    print("--- Logger Rate Limiter (10 second window) ---\n")
    logger = Logger()

    messages = ["foo", "bar", "foo", "bar", "foo", "foo"]
    timestamps = [1, 2, 3, 8, 10, 11]

    for i in range(len(messages)):
        result = logger.should_print_message(timestamps[i], messages[i])
        status = "PRINT" if result else "SKIP"
        print(f"timestamp={timestamps[i]}, message=\"{messages[i]}\" -> {status}")

    # Demonstrate Sliding Window Rate Limiter
    print("\n--- Sliding Window Rate Limiter (3 requests per 5 seconds) ---\n")
    rate_limiter = SlidingWindowRateLimiter(3, 5)

    user_id = "user123"
    request_times = [1, 2, 3, 4, 5, 6, 7, 10, 11, 12]

    for time in request_times:
        allowed = rate_limiter.allow_request(user_id, time)
        status = "ALLOWED" if allowed else "RATE LIMITED"
        print(f"time={time}, user={user_id} -> {status}")

    # Demonstrate Token Bucket
    print("\n--- Token Bucket Rate Limiter (5 tokens, 1 token/sec refill) ---\n")
    token_bucket = TokenBucket(5, 1.0)

    for time in range(0, 16, 2):
        token_bucket.refill(time)
        allowed = token_bucket.try_consume(1)
        status = "ALLOWED" if allowed else "RATE LIMITED"
        print(f"time={time} -> {status} (tokens: {token_bucket.get_tokens():.1f})")


if __name__ == "__main__":
    main()
