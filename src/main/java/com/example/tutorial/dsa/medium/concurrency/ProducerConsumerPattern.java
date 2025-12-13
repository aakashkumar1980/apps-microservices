package com.example.tutorial.dsa.medium.concurrency;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * ProducerConsumerPattern
 * ----------------------------------
 * <p>This program demonstrates the Producer-Consumer pattern using blocking queues.
 * The core problem solved here is Print in Order / Print FooBar Alternately (LeetCode #1114, #1115).
 *
 * <p><b>Problem Statement:</b>
 * Implement thread-safe producer-consumer communication where producers add items
 * to a shared buffer and consumers remove items, with proper synchronization.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Transaction processing pipeline</li>
 *   <li>Offer notification queue</li>
 *   <li>Batch job scheduling</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Google, Microsoft, Apple
 *
 * @see <a href="https://leetcode.com/problems/print-in-order/">LeetCode 1114 - Print in Order</a>
 */
@Component
public class ProducerConsumerPattern implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(ProducerConsumerPattern.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== ProducerConsumerPattern: Thread Communication Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demo 1: Using BlockingQueue
    System.out.println("--- Demo 1: BlockingQueue Producer-Consumer ---\n");
    demoBlockingQueue();

    // Demo 2: Custom implementation with wait/notify
    System.out.println("\n--- Demo 2: Custom Buffer with wait/notify ---\n");
    demoCustomBuffer();

    // Demo 3: Print in Order simulation
    System.out.println("\n--- Demo 3: Print In Order (LeetCode #1114) ---\n");
    demoPrintInOrder();
  }

  private void demoBlockingQueue() throws InterruptedException {
    BlockingQueue<String> queue = new LinkedBlockingQueue<>(3);

    // Producer thread
    Thread producer = new Thread(() -> {
      String[] items = {"Offer-A", "Offer-B", "Offer-C", "Offer-D", "Offer-E"};
      for (String item : items) {
        try {
          System.out.println("  Producer: putting " + item);
          queue.put(item);
          Thread.sleep(100);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }, "Producer");

    // Consumer thread
    Thread consumer = new Thread(() -> {
      for (int i = 0; i < 5; i++) {
        try {
          Thread.sleep(200);
          String item = queue.take();
          System.out.println("  Consumer: took " + item);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }, "Consumer");

    producer.start();
    consumer.start();
    producer.join();
    consumer.join();

    System.out.println("  BlockingQueue demo complete.");
  }

  private void demoCustomBuffer() throws InterruptedException {
    BoundedBuffer buffer = new BoundedBuffer(2);

    Thread producer = new Thread(() -> {
      for (int i = 1; i <= 4; i++) {
        try {
          String item = "Transaction-" + i;
          buffer.put(item);
          System.out.println("  Producer: added " + item);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    });

    Thread consumer = new Thread(() -> {
      for (int i = 1; i <= 4; i++) {
        try {
          Thread.sleep(150);
          String item = buffer.take();
          System.out.println("  Consumer: processed " + item);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    });

    producer.start();
    consumer.start();
    producer.join();
    consumer.join();

    System.out.println("  Custom buffer demo complete.");
  }

  private void demoPrintInOrder() throws InterruptedException {
    Foo foo = new Foo();

    Thread t1 = new Thread(() -> {
      try {
        foo.first(() -> System.out.print("first"));
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });

    Thread t2 = new Thread(() -> {
      try {
        foo.second(() -> System.out.print("second"));
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });

    Thread t3 = new Thread(() -> {
      try {
        foo.third(() -> System.out.print("third"));
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });

    // Start in reverse order to demonstrate synchronization.
    t3.start();
    t2.start();
    t1.start();

    t1.join();
    t2.join();
    t3.join();

    System.out.println("\n  Print in Order demo complete.");
  }

  /**
   * BoundedBuffer implementing Producer-Consumer pattern.
   *
   * <p><b>LOGIC (wait/notify Synchronization):</b>
   * <ol>
   *   <li>Producers wait when buffer is full</li>
   *   <li>Consumers wait when buffer is empty</li>
   *   <li>notify() wakes up waiting threads when state changes</li>
   * </ol>
   *
   * <p><b>Time Complexity: O(1)</b> for put and take (excluding wait time).
   * <p><b>Space Complexity: O(capacity)</b>
   */
  public static class BoundedBuffer {
    private final Queue<String> buffer;
    private final int capacity;

    public BoundedBuffer(int capacity) {
      this.capacity = capacity;
      this.buffer = new LinkedList<>();
    }

    public synchronized void put(String item) throws InterruptedException {
      while (buffer.size() == capacity) {
        wait();  // Buffer full, wait.
      }
      buffer.add(item);
      notifyAll();  // Wake up consumers.
    }

    public synchronized String take() throws InterruptedException {
      while (buffer.isEmpty()) {
        wait();  // Buffer empty, wait.
      }
      String item = buffer.poll();
      notifyAll();  // Wake up producers.
      return item;
    }
  }

  /**
   * Foo class for LeetCode #1114 - Print in Order.
   *
   * <p><b>LOGIC (Semaphore-like Flags):</b>
   * <ol>
   *   <li>Use flags to track which method has completed</li>
   *   <li>second() waits until first() sets firstDone</li>
   *   <li>third() waits until second() sets secondDone</li>
   * </ol>
   */
  public static class Foo {
    private volatile boolean firstDone = false;
    private volatile boolean secondDone = false;

    public synchronized void first(Runnable printFirst) throws InterruptedException {
      printFirst.run();
      firstDone = true;
      notifyAll();
    }

    public synchronized void second(Runnable printSecond) throws InterruptedException {
      while (!firstDone) {
        wait();
      }
      printSecond.run();
      secondDone = true;
      notifyAll();
    }

    public synchronized void third(Runnable printThird) throws InterruptedException {
      while (!secondDone) {
        wait();
      }
      printThird.run();
    }
  }
}
