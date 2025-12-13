package com.example.tutorial.dsa.medium.concurrency;

import com.example.tutorial.common.datamodel.Task;
import com.example.tutorial.common.utils.SampleDataLoader;
import com.example.tutorial.dsa.medium.concurrency.producerconsumer.BoundedBuffer;
import com.example.tutorial.dsa.medium.concurrency.producerconsumer.PrintInOrder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
 * <p><b>Real UseCase:</b>
 * <ul>
 *   <li>Transaction processing pipeline</li>
 *   <li>Message queue processing (Kafka, RabbitMQ consumers)</li>
 *   <li>Batch job scheduling</li>
 *   <li>Event-driven architectures</li>
 * </ul>
 *
 * <p><b>Pattern Overview:</b>
 * <pre>
 * ┌──────────┐    ┌─────────────┐    ┌──────────┐
 * │ Producer │───▶│   Buffer    │───▶│ Consumer │
 * └──────────┘    │ (Bounded)   │    └──────────┘
 *                 └─────────────┘
 *
 * Producer: Creates items, blocks when buffer full
 * Consumer: Processes items, blocks when buffer empty
 * Buffer: Thread-safe queue with capacity limit
 * </pre>
 *
 * <p><b>Company Tags:</b> Amazon, Google, Microsoft, Apple
 *
 * @see com.example.tutorial.dsa.medium.concurrency.producerconsumer.BoundedBuffer
 * @see com.example.tutorial.dsa.medium.concurrency.producerconsumer.PrintInOrder
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

    // Load sample tasks - appropriate for producer-consumer demos
    List<Task> tasks = SampleDataLoader.TASKS_DTO.get();
    System.out.println("Loaded " + tasks.size() + " sample tasks for processing.\n");

    // Demo 1: Using BlockingQueue with Task objects
    System.out.println("--- Demo 1: BlockingQueue Producer-Consumer ---\n");
    demoBlockingQueue(tasks);

    // Demo 2: Custom BoundedBuffer implementation
    System.out.println("\n--- Demo 2: Custom BoundedBuffer with wait/notify ---\n");
    demoCustomBuffer();

    // Demo 3: Print in Order (LeetCode #1114)
    System.out.println("\n--- Demo 3: Print In Order (LeetCode #1114) ---\n");
    demoPrintInOrder();
  }

  private void demoBlockingQueue(List<Task> tasks) throws InterruptedException {
    BlockingQueue<Task> queue = new LinkedBlockingQueue<>(3);

    // Producer thread - adds tasks to queue
    Thread producer = new Thread(() -> {
      for (int i = 0; i < Math.min(5, tasks.size()); i++) {
        try {
          Task task = tasks.get(i);
          System.out.println("  Producer: adding [" + task.getPriority() + "] " + task.getName());
          queue.put(task);
          Thread.sleep(100);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }, "Producer");

    // Consumer thread - processes tasks from queue
    Thread consumer = new Thread(() -> {
      for (int i = 0; i < 5; i++) {
        try {
          Thread.sleep(200);  // Simulate slower processing
          Task task = queue.take();
          task.complete();
          System.out.println("  Consumer: processed " + task.getName() + " [" + task.getStatus() + "]");
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
    BoundedBuffer<String> buffer = new BoundedBuffer<>(2);

    Thread producer = new Thread(() -> {
      String[] items = {"Payment-001", "Payment-002", "Payment-003", "Payment-004"};
      for (String item : items) {
        try {
          buffer.put(item);
          System.out.println("  Producer: added " + item);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    });

    Thread consumer = new Thread(() -> {
      for (int i = 0; i < 4; i++) {
        try {
          Thread.sleep(150);  // Simulate processing time
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
    PrintInOrder printInOrder = new PrintInOrder();

    Thread t1 = new Thread(() -> {
      try {
        printInOrder.first(() -> System.out.print("first"));
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });

    Thread t2 = new Thread(() -> {
      try {
        printInOrder.second(() -> System.out.print("second"));
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });

    Thread t3 = new Thread(() -> {
      try {
        printInOrder.third(() -> System.out.print("third"));
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });

    // Start in reverse order to demonstrate synchronization works
    t3.start();
    t2.start();
    t1.start();

    t1.join();
    t2.join();
    t3.join();

    System.out.println("\n  Print in Order demo complete.");
  }
}
