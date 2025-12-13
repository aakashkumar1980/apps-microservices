package com.example.tutorial.dsa.medium.caching;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LRUCacheImplementation
 * ----------------------------------
 * <p>This program implements an LRU (Least Recently Used) cache.
 * The core problem solved here is LRU Cache (LeetCode #146).
 *
 * <p><b>Problem Statement:</b>
 * Design a data structure that follows the constraints of a Least Recently Used cache.
 * Implement get and put operations in O(1) time complexity.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Cache frequently accessed offer details</li>
 *   <li>Store recent transaction lookups</li>
 *   <li>Maintain session data for active users</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Microsoft, Facebook, Google, Bloomberg
 *
 * @see <a href="https://leetcode.com/problems/lru-cache/">LeetCode 146 - LRU Cache</a>
 */
@Component
public class LRUCacheImplementation implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(LRUCacheImplementation.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== LRUCacheImplementation: LRU Cache Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Create LRU Cache with capacity 3
    System.out.println("--- LRU Cache (capacity=3) ---\n");
    LRUCache cache = new LRUCache(3);

    // Demonstrate operations
    System.out.println("put(1, 'Offer-A')");
    cache.put(1, "Offer-A");
    cache.printState();

    System.out.println("\nput(2, 'Offer-B')");
    cache.put(2, "Offer-B");
    cache.printState();

    System.out.println("\nput(3, 'Offer-C')");
    cache.put(3, "Offer-C");
    cache.printState();

    System.out.println("\nget(1) = " + cache.get(1) + " (moves to most recent)");
    cache.printState();

    System.out.println("\nput(4, 'Offer-D') - exceeds capacity, evicts LRU (key 2)");
    cache.put(4, "Offer-D");
    cache.printState();

    System.out.println("\nget(2) = " + cache.get(2) + " (was evicted)");

    System.out.println("\nput(5, 'Offer-E') - evicts LRU (key 3)");
    cache.put(5, "Offer-E");
    cache.printState();

    // Update existing key
    System.out.println("\nput(1, 'Offer-A-Updated') - update existing key");
    cache.put(1, "Offer-A-Updated");
    cache.printState();
  }

  /**
   * LRUCache using HashMap + Doubly Linked List.
   *
   * <p><b>LOGIC (HashMap + Doubly Linked List):</b>
   * <ol>
   *   <li>HashMap provides O(1) lookup by key</li>
   *   <li>Doubly linked list maintains order (most recent at head)</li>
   *   <li>On access: move node to head (O(1) with direct node reference)</li>
   *   <li>On eviction: remove from tail (O(1))</li>
   * </ol>
   *
   * <p><b>Data Structure:</b>
   * <pre>
   * HashMap: key → Node (for O(1) lookup)
   *
   * Doubly Linked List: HEAD ↔ [MRU] ↔ ... ↔ [LRU] ↔ TAIL
   *
   * HEAD and TAIL are dummy nodes to simplify edge cases.
   * </pre>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * Capacity: 2
   *
   * put(1,A): HEAD ↔ (1,A) ↔ TAIL
   * put(2,B): HEAD ↔ (2,B) ↔ (1,A) ↔ TAIL
   * get(1):   HEAD ↔ (1,A) ↔ (2,B) ↔ TAIL  (1 moved to front)
   * put(3,C): HEAD ↔ (3,C) ↔ (1,A) ↔ TAIL  (2 evicted)
   * </pre>
   *
   * <p><b>Time Complexity: O(1)</b> for both get and put.
   * <br><i>Like a VIP line at a club - regulars get moved to the front,
   * and those who haven't visited recently get removed from the back.</i>
   *
   * <p><b>Space Complexity: O(capacity)</b>
   * <br>HashMap and linked list both store at most 'capacity' entries.
   */
  public static class LRUCache {
    private final int capacity;
    private final Map<Integer, Node> cache;
    private final Node head;  // Dummy head (MRU side).
    private final Node tail;  // Dummy tail (LRU side).

    private static class Node {
      int key;
      String value;
      Node prev;
      Node next;

      Node(int key, String value) {
        this.key = key;
        this.value = value;
      }
    }

    public LRUCache(int capacity) {
      this.capacity = capacity;
      this.cache = new HashMap<>();

      // Initialize dummy head and tail.
      this.head = new Node(0, null);
      this.tail = new Node(0, null);
      head.next = tail;
      tail.prev = head;
    }

    /**
     * Gets value by key. Returns null if not found.
     */
    public String get(int key) {
      Node node = cache.get(key);
      if (node == null) {
        return null;
      }

      // Move to head (most recently used).
      moveToHead(node);
      return node.value;
    }

    /**
     * Puts key-value pair. Evicts LRU if at capacity.
     */
    public void put(int key, String value) {
      Node node = cache.get(key);

      if (node != null) {
        // Update existing.
        node.value = value;
        moveToHead(node);
      } else {
        // Add new.
        Node newNode = new Node(key, value);

        cache.put(key, newNode);
        addToHead(newNode);

        if (cache.size() > capacity) {
          // Evict LRU (node before tail).
          Node lru = tail.prev;
          removeNode(lru);
          cache.remove(lru.key);
        }
      }
    }

    private void addToHead(Node node) {
      node.prev = head;
      node.next = head.next;
      head.next.prev = node;
      head.next = node;
    }

    private void removeNode(Node node) {
      node.prev.next = node.next;
      node.next.prev = node.prev;
    }

    private void moveToHead(Node node) {
      removeNode(node);
      addToHead(node);
    }

    /**
     * Prints current cache state.
     */
    public void printState() {
      System.out.print("  Cache [MRU → LRU]: ");
      Node current = head.next;
      while (current != tail) {
        System.out.print("(" + current.key + ":" + current.value + ")");
        if (current.next != tail) {
          System.out.print(" → ");
        }
        current = current.next;
      }
      System.out.println();
    }
  }
}
