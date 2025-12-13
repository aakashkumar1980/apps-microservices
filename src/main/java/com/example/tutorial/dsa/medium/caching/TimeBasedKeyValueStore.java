package com.example.tutorial.dsa.medium.caching;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * TimeBasedKeyValueStore
 * ----------------------------------
 * <p>This program implements a time-based key-value store.
 * The core problem solved here is Time Based Key-Value Store (LeetCode #981).
 *
 * <p><b>Problem Statement:</b>
 * Design a time-based key-value data structure that can store multiple values
 * for the same key at different timestamps and retrieve the value at a certain timestamp.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Track offer price history over time</li>
 *   <li>Store versioned merchant configurations</li>
 *   <li>Maintain audit trail of transaction states</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Google, Amazon, Netflix, Lyft
 *
 * @see <a href="https://leetcode.com/problems/time-based-key-value-store/">LeetCode 981 - Time Based Key-Value Store</a>
 */
@Component
public class TimeBasedKeyValueStore implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(TimeBasedKeyValueStore.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== TimeBasedKeyValueStore: Time-Based Cache Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Create TimeMap
    System.out.println("--- Time-Based Key-Value Store ---\n");
    TimeMap timeMap = new TimeMap();

    // Store values at different timestamps
    System.out.println("set('offer1', '5% cashback', 1)");
    timeMap.set("offer1", "5% cashback", 1);

    System.out.println("set('offer1', '7% cashback', 5)");
    timeMap.set("offer1", "7% cashback", 5);

    System.out.println("set('offer1', '10% cashback', 10)");
    timeMap.set("offer1", "10% cashback", 10);

    // Query at different timestamps
    System.out.println("\n--- Querying at Different Timestamps ---\n");

    System.out.println("get('offer1', 0) = '" + timeMap.get("offer1", 0) + "'");
    System.out.println("  (no value at or before timestamp 0)");

    System.out.println("\nget('offer1', 1) = '" + timeMap.get("offer1", 1) + "'");
    System.out.println("  (exact match at timestamp 1)");

    System.out.println("\nget('offer1', 3) = '" + timeMap.get("offer1", 3) + "'");
    System.out.println("  (returns value from timestamp 1, closest <= 3)");

    System.out.println("\nget('offer1', 5) = '" + timeMap.get("offer1", 5) + "'");
    System.out.println("  (exact match at timestamp 5)");

    System.out.println("\nget('offer1', 7) = '" + timeMap.get("offer1", 7) + "'");
    System.out.println("  (returns value from timestamp 5, closest <= 7)");

    System.out.println("\nget('offer1', 10) = '" + timeMap.get("offer1", 10) + "'");
    System.out.println("  (exact match at timestamp 10)");

    System.out.println("\nget('offer1', 15) = '" + timeMap.get("offer1", 15) + "'");
    System.out.println("  (returns value from timestamp 10, closest <= 15)");

    // Multiple keys
    System.out.println("\n--- Multiple Keys ---\n");
    timeMap.set("merchant1", "Active", 1);
    timeMap.set("merchant1", "Premium", 5);
    timeMap.set("merchant2", "Basic", 3);

    System.out.println("get('merchant1', 4) = '" + timeMap.get("merchant1", 4) + "'");
    System.out.println("get('merchant1', 6) = '" + timeMap.get("merchant1", 6) + "'");
    System.out.println("get('merchant2', 4) = '" + timeMap.get("merchant2", 4) + "'");
    System.out.println("get('nonexistent', 5) = '" + timeMap.get("nonexistent", 5) + "'");

    // Print internal state
    System.out.println("\n--- Internal State ---");
    timeMap.printState();
  }

  /**
   * TimeMap class for time-based key-value storage.
   *
   * <p><b>LOGIC (HashMap + TreeMap/Binary Search):</b>
   * <ol>
   *   <li>Outer HashMap: key → list of (timestamp, value) pairs</li>
   *   <li>List is sorted by timestamp (assumed ascending inserts)</li>
   *   <li>On get: binary search for largest timestamp <= query</li>
   * </ol>
   *
   * <p><b>Why Binary Search Works:</b>
   * <pre>
   * Timestamps are inserted in increasing order (problem constraint).
   * So the list is already sorted.
   *
   * For get(key, timestamp):
   *   Find rightmost entry where entry.timestamp <= timestamp
   *   This is the "floor" operation.
   * </pre>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * set("foo", "bar", 1) → foo: [(1, "bar")]
   * set("foo", "baz", 5) → foo: [(1, "bar"), (5, "baz")]
   *
   * get("foo", 3):
   *   Binary search for largest timestamp <= 3
   *   Returns "bar" (timestamp 1)
   *
   * get("foo", 5):
   *   Binary search for largest timestamp <= 5
   *   Returns "baz" (timestamp 5)
   * </pre>
   *
   * <p><b>Time Complexity:</b>
   * <ul>
   *   <li>set: O(1) amortized (append to list)</li>
   *   <li>get: O(log n) where n = entries for that key</li>
   * </ul>
   * <br><i>Like looking up historical stock prices - find the most recent
   * price at or before your query date.</i>
   *
   * <p><b>Space Complexity: O(total entries)</b>
   */
  public static class TimeMap {
    private final Map<String, List<Entry>> store;

    private static class Entry {
      int timestamp;
      String value;

      Entry(int timestamp, String value) {
        this.timestamp = timestamp;
        this.value = value;
      }
    }

    public TimeMap() {
      store = new HashMap<>();
    }

    /**
     * Stores the key-value pair at the given timestamp.
     */
    public void set(String key, String value, int timestamp) {
      store.computeIfAbsent(key, k -> new ArrayList<>())
          .add(new Entry(timestamp, value));
    }

    /**
     * Returns the value at the largest timestamp <= given timestamp.
     * Returns empty string if no such timestamp exists.
     */
    public String get(String key, int timestamp) {
      List<Entry> entries = store.get(key);
      if (entries == null || entries.isEmpty()) {
        return "";
      }

      // Binary search for largest timestamp <= target.
      int left = 0;
      int right = entries.size() - 1;
      int result = -1;

      while (left <= right) {
        int mid = left + (right - left) / 2;
        if (entries.get(mid).timestamp <= timestamp) {
          result = mid;
          left = mid + 1;  // Look for larger valid timestamp.
        } else {
          right = mid - 1;
        }
      }

      return result >= 0 ? entries.get(result).value : "";
    }

    /**
     * Prints current store state.
     */
    public void printState() {
      for (Map.Entry<String, List<Entry>> entry : store.entrySet()) {
        System.out.print("  " + entry.getKey() + ": ");
        List<String> parts = new ArrayList<>();
        for (Entry e : entry.getValue()) {
          parts.add("(" + e.timestamp + ", \"" + e.value + "\")");
        }
        System.out.println(String.join(" → ", parts));
      }
    }
  }
}
