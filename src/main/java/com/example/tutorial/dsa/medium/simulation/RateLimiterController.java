package com.example.tutorial.dsa.medium.simulation;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * RateLimiterController
 * ----------------------------------
 * <p>This program implements rate limiting algorithms for API throttling.
 * The core problem solved here is Logger Rate Limiter (LeetCode #359).
 *
 * <p><b>Problem Statement:</b>
 * Design a logger system that receives a stream of messages and timestamps,
 * and returns true if the message should be printed (not printed in the last 10 seconds).
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Rate limit API requests per user/merchant</li>
 *   <li>Throttle duplicate transaction alerts</li>
 *   <li>Control notification frequency to users</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Google, Amazon, Facebook
 *
 * @see <a href="https://leetcode.com/problems/logger-rate-limiter/">LeetCode 359 - Logger Rate Limiter</a>
 */
@Component
public class RateLimiterController implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(RateLimiterController.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== RateLimiterController: Rate Limiting Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate Logger Rate Limiter
    System.out.println("--- Logger Rate Limiter (10 second window) ---\n");
    Logger logger = new Logger();

    String[] messages = {"foo", "bar", "foo", "bar", "foo", "foo"};
    int[] timestamps = {1, 2, 3, 8, 10, 11};

    for (int i = 0; i < messages.length; i++) {
      boolean result = logger.shouldPrintMessage(timestamps[i], messages[i]);
      System.out.println("timestamp=" + timestamps[i] + ", message=\"" + messages[i]
          + "\" → " + (result ? "PRINT" : "SKIP"));
    }

    // Demonstrate Sliding Window Rate Limiter
    System.out.println("\n--- Sliding Window Rate Limiter (3 requests per 5 seconds) ---\n");
    SlidingWindowRateLimiter rateLimiter = new SlidingWindowRateLimiter(3, 5);

    String userId = "user123";
    int[] requestTimes = {1, 2, 3, 4, 5, 6, 7, 10, 11, 12};

    for (int time : requestTimes) {
      boolean allowed = rateLimiter.allowRequest(userId, time);
      System.out.println("time=" + time + ", user=" + userId
          + " → " + (allowed ? "ALLOWED" : "RATE LIMITED"));
    }

    // Demonstrate Token Bucket
    System.out.println("\n--- Token Bucket Rate Limiter (5 tokens, 1 token/sec refill) ---\n");
    TokenBucket tokenBucket = new TokenBucket(5, 1.0);

    for (int time = 0; time <= 15; time += 2) {
      tokenBucket.refill(time);
      boolean allowed = tokenBucket.tryConsume(1);
      System.out.println("time=" + time + " → " + (allowed ? "ALLOWED" : "RATE LIMITED")
          + " (tokens: " + String.format("%.1f", tokenBucket.getTokens()) + ")");
    }
  }

  /**
   * Logger class implementing rate limiting for messages.
   *
   * <p><b>LOGIC (HashMap with Timestamp Tracking):</b>
   * <ol>
   *   <li>Store last printed timestamp for each message</li>
   *   <li>On new request, check if 10+ seconds have passed</li>
   *   <li>If yes, update timestamp and return true (print)</li>
   *   <li>If no, return false (skip)</li>
   * </ol>
   *
   * <p><b>Time Complexity: O(1)</b> per operation.
   * <p><b>Space Complexity: O(n)</b> where n = unique messages.
   */
  public static class Logger {
    private final Map<String, Integer> messageTimestamps;

    public Logger() {
      messageTimestamps = new HashMap<>();
    }

    /**
     * Returns true if the message should be printed (not printed in last 10 seconds).
     *
     * @param timestamp current timestamp in seconds
     * @param message the message to log
     * @return true if should print, false otherwise
     */
    public boolean shouldPrintMessage(int timestamp, String message) {
      if (!messageTimestamps.containsKey(message)
          || timestamp - messageTimestamps.get(message) >= 10) {
        messageTimestamps.put(message, timestamp);
        return true;
      }
      return false;
    }
  }

  /**
   * Sliding Window Rate Limiter using timestamp queue.
   *
   * <p><b>LOGIC (Queue of Timestamps):</b>
   * <ol>
   *   <li>For each user, maintain a queue of request timestamps</li>
   *   <li>On new request, remove expired timestamps (outside window)</li>
   *   <li>If queue size < limit, allow request and add timestamp</li>
   *   <li>Otherwise, reject request</li>
   * </ol>
   *
   * <p><b>Time Complexity: O(w)</b> where w = window size (cleanup).
   * <p><b>Space Complexity: O(n × w)</b> where n = users, w = max requests per window.
   */
  public static class SlidingWindowRateLimiter {
    private final int maxRequests;
    private final int windowSeconds;
    private final Map<String, Deque<Integer>> userRequests;

    public SlidingWindowRateLimiter(int maxRequests, int windowSeconds) {
      this.maxRequests = maxRequests;
      this.windowSeconds = windowSeconds;
      this.userRequests = new HashMap<>();
    }

    /**
     * Checks if request is allowed and records it if so.
     *
     * @param userId the user making the request
     * @param timestamp current timestamp in seconds
     * @return true if request is allowed
     */
    public boolean allowRequest(String userId, int timestamp) {
      userRequests.putIfAbsent(userId, new LinkedList<>());
      Deque<Integer> requests = userRequests.get(userId);

      // Remove expired timestamps.
      while (!requests.isEmpty() && timestamp - requests.peekFirst() >= windowSeconds) {
        requests.pollFirst();
      }

      // Check if under limit.
      if (requests.size() < maxRequests) {
        requests.addLast(timestamp);
        return true;
      }
      return false;
    }
  }

  /**
   * Token Bucket Rate Limiter.
   *
   * <p><b>LOGIC (Token Bucket Algorithm):</b>
   * <ol>
   *   <li>Bucket starts with max tokens</li>
   *   <li>Tokens refill at constant rate up to max</li>
   *   <li>Each request consumes tokens</li>
   *   <li>Request allowed only if enough tokens available</li>
   * </ol>
   *
   * <p><b>Time Complexity: O(1)</b> per operation.
   * <p><b>Space Complexity: O(1)</b>.
   */
  public static class TokenBucket {
    private final int maxTokens;
    private final double refillRate;  // Tokens per second.
    private double tokens;
    private int lastRefillTime;

    public TokenBucket(int maxTokens, double refillRate) {
      this.maxTokens = maxTokens;
      this.refillRate = refillRate;
      this.tokens = maxTokens;
      this.lastRefillTime = 0;
    }

    /**
     * Refills tokens based on elapsed time.
     *
     * @param currentTime current timestamp in seconds
     */
    public void refill(int currentTime) {
      int elapsed = currentTime - lastRefillTime;
      tokens = Math.min(maxTokens, tokens + elapsed * refillRate);
      lastRefillTime = currentTime;
    }

    /**
     * Attempts to consume tokens.
     *
     * @param count number of tokens to consume
     * @return true if tokens were consumed successfully
     */
    public boolean tryConsume(int count) {
      if (tokens >= count) {
        tokens -= count;
        return true;
      }
      return false;
    }

    public double getTokens() {
      return tokens;
    }
  }
}
