package com.example.tutorial.dsa.medium.caching;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * LFUCacheImplementation
 * ----------------------------------
 * <p>This program implements an LFU (Least Frequently Used) cache.
 * The core problem solved here is LFU Cache (LeetCode #460).
 *
 * <p><b>Problem Statement:</b>
 * Design a data structure that follows the constraints of a Least Frequently Used cache.
 * When cache is full, remove the least frequently used key. If there's a tie,
 * remove the least recently used among them.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Cache popular offers based on access frequency</li>
 *   <li>Optimize hot data for merchant lookups</li>
 *   <li>Store frequently accessed user preferences</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Google, Facebook, Bloomberg
 *
 * @see <a href="https://leetcode.com/problems/lfu-cache/">LeetCode 460 - LFU Cache</a>
 */
@Component
public class LFUCacheImplementation implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(LFUCacheImplementation.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== LFUCacheImplementation: LFU Cache Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Create LFU Cache with capacity 3
    System.out.println("--- LFU Cache (capacity=3) ---\n");
    LFUCache cache = new LFUCache(3);

    // Demonstrate operations
    System.out.println("put(1, 'Offer-A')");
    cache.put(1, "Offer-A");
    cache.printState();

    System.out.println("\nput(2, 'Offer-B')");
    cache.put(2, "Offer-B");
    cache.printState();

    System.out.println("\nget(1) - increases frequency of key 1");
    System.out.println("  Result: " + cache.get(1));
    cache.printState();

    System.out.println("\nget(1) - increases frequency of key 1 again");
    System.out.println("  Result: " + cache.get(1));
    cache.printState();

    System.out.println("\nput(3, 'Offer-C')");
    cache.put(3, "Offer-C");
    cache.printState();

    System.out.println("\nput(4, 'Offer-D') - evicts key with lowest freq (key 2, freq=1)");
    cache.put(4, "Offer-D");
    cache.printState();

    System.out.println("\nget(2) = " + cache.get(2) + " (was evicted)");

    System.out.println("\nget(3) - increases frequency of key 3");
    System.out.println("  Result: " + cache.get(3));
    cache.printState();

    System.out.println("\nput(5, 'Offer-E') - evicts LFU with LRU tiebreaker (key 4)");
    cache.put(5, "Offer-E");
    cache.printState();
  }

  /**
   * LFUCache using multiple data structures.
   *
   * <p><b>LOGIC (HashMap + Frequency Map + LRU Lists per Frequency):</b>
   * <ol>
   *   <li>keyToNode: HashMap for O(1) key lookup</li>
   *   <li>freqToList: HashMap mapping frequency to doubly linked list (LRU order)</li>
   *   <li>Track minFreq to know which list to evict from</li>
   *   <li>On access: move node to next frequency's list</li>
   * </ol>
   *
   * <p><b>Data Structure Visualization:</b>
   * <pre>
   * keyToNode: { 1 → Node(1), 2 → Node(2), 3 → Node(3) }
   *
   * freqToList:
   *   freq=1: HEAD ↔ (newest) ↔ ... ↔ (oldest) ↔ TAIL
   *   freq=2: HEAD ↔ (newest) ↔ ... ↔ (oldest) ↔ TAIL
   *   freq=3: ...
   *
   * minFreq: 1 (points to lowest non-empty frequency)
   * </pre>
   *
   * <p><b>Time Complexity: O(1)</b> for both get and put.
   * <br><i>Like a library tracking how often books are borrowed -
   * least borrowed books get removed when shelf space runs out.</i>
   *
   * <p><b>Space Complexity: O(capacity)</b>
   */
  public static class LFUCache {
    private final int capacity;
    private int minFreq;
    private final Map<Integer, Node> keyToNode;
    private final Map<Integer, DoublyLinkedList> freqToList;

    private static class Node {
      int key;
      String value;
      int freq;
      Node prev;
      Node next;

      Node(int key, String value) {
        this.key = key;
        this.value = value;
        this.freq = 1;
      }
    }

    private static class DoublyLinkedList {
      Node head;  // Most recent.
      Node tail;  // Least recent.
      int size;

      DoublyLinkedList() {
        head = new Node(0, null);
        tail = new Node(0, null);
        head.next = tail;
        tail.prev = head;
        size = 0;
      }

      void addFirst(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
        size++;
      }

      void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
      }

      Node removeLast() {
        if (size > 0) {
          Node lru = tail.prev;
          remove(lru);
          return lru;
        }
        return null;
      }

      boolean isEmpty() {
        return size == 0;
      }
    }

    public LFUCache(int capacity) {
      this.capacity = capacity;
      this.minFreq = 0;
      this.keyToNode = new HashMap<>();
      this.freqToList = new HashMap<>();
    }

    public String get(int key) {
      Node node = keyToNode.get(key);
      if (node == null) {
        return null;
      }
      updateFreq(node);
      return node.value;
    }

    public void put(int key, String value) {
      if (capacity == 0) {
        return;
      }

      Node node = keyToNode.get(key);

      if (node != null) {
        // Update existing.
        node.value = value;
        updateFreq(node);
      } else {
        // Evict if at capacity.
        if (keyToNode.size() >= capacity) {
          DoublyLinkedList minFreqList = freqToList.get(minFreq);
          Node evicted = minFreqList.removeLast();
          keyToNode.remove(evicted.key);
        }

        // Add new node.
        Node newNode = new Node(key, value);
        keyToNode.put(key, newNode);
        freqToList.computeIfAbsent(1, k -> new DoublyLinkedList()).addFirst(newNode);
        minFreq = 1;
      }
    }

    private void updateFreq(Node node) {
      int oldFreq = node.freq;
      int newFreq = oldFreq + 1;

      // Remove from old frequency list.
      DoublyLinkedList oldList = freqToList.get(oldFreq);
      oldList.remove(node);

      // Update minFreq if needed.
      if (oldFreq == minFreq && oldList.isEmpty()) {
        minFreq++;
      }

      // Add to new frequency list.
      node.freq = newFreq;
      freqToList.computeIfAbsent(newFreq, k -> new DoublyLinkedList()).addFirst(node);
    }

    public void printState() {
      System.out.println("  Cache state (minFreq=" + minFreq + "):");
      for (Map.Entry<Integer, DoublyLinkedList> entry : freqToList.entrySet()) {
        if (!entry.getValue().isEmpty()) {
          System.out.print("    freq=" + entry.getKey() + ": ");
          Node current = entry.getValue().head.next;
          while (current != entry.getValue().tail) {
            System.out.print("(" + current.key + ":" + current.value + ") ");
            current = current.next;
          }
          System.out.println();
        }
      }
    }
  }
}
