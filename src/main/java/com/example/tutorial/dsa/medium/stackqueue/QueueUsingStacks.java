package com.example.tutorial.dsa.medium.stackqueue;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * QueueUsingStacks
 * ----------------------------------
 * <p>This program implements a FIFO queue using two stacks.
 * The core problem solved here is Implement Queue using Stacks (LeetCode #232).
 *
 * <p><b>Problem Statement:</b>
 * Implement a first in first out (FIFO) queue using only two stacks. The queue
 * should support all functions: push, pop, peek, and empty.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Process offer redemption requests in order received</li>
 *   <li>Handle customer service tickets FIFO when only stack-based storage available</li>
 *   <li>Manage batch processing jobs in order</li>
 * </ul>
 *
 * <p><b>Operations:</b>
 * <ul>
 *   <li>push(x) - Push element to back of queue</li>
 *   <li>pop() - Remove element from front of queue</li>
 *   <li>peek() - Get front element</li>
 *   <li>empty() - Check if queue is empty</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Microsoft, Apple, Bloomberg
 *
 * @see <a href="https://leetcode.com/problems/implement-queue-using-stacks/">LeetCode 232</a>
 */
@Component
public class QueueUsingStacks implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(QueueUsingStacks.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== QueueUsingStacks: Queue Implementation Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate with customer service tickets
    System.out.println("--- Processing Service Tickets (FIFO) ---\n");
    MyQueue ticketQueue = new MyQueue();

    String[] tickets = {"Ticket-101", "Ticket-102", "Ticket-103", "Ticket-104"};
    System.out.println("Submitting tickets:");
    for (String ticket : tickets) {
      ticketQueue.push(ticket.hashCode());
      System.out.println("  Submitted: " + ticket);
    }

    System.out.println("\nProcessing tickets (FIFO order):");
    while (!ticketQueue.empty()) {
      System.out.println("  Processing ticket with hash: " + ticketQueue.pop());
    }

    // Standard LeetCode example
    System.out.println("\n--- LeetCode Example ---\n");
    MyQueue queue = new MyQueue();
    queue.push(1);
    System.out.println("push(1)");
    queue.push(2);
    System.out.println("push(2)");
    System.out.println("peek() → " + queue.peek());   // 1
    System.out.println("pop() → " + queue.pop());     // 1
    System.out.println("empty() → " + queue.empty()); // false
  }

  /**
   * MyQueue - Queue implemented using two stacks.
   *
   * <p><b>DESIGN (Two-Stack Reversal):</b>
   * <ol>
   *   <li>inputStack: receives all push operations</li>
   *   <li>outputStack: used for pop/peek operations</li>
   *   <li>When outputStack is empty and we need pop/peek:
   *       transfer all elements from inputStack to outputStack</li>
   *   <li>Transfer reverses order, giving us FIFO behavior!</li>
   * </ol>
   *
   * <p><b>Why does this work?</b>
   * <br>Stack is LIFO (Last In First Out). By moving elements from one stack to another,
   * we reverse the order. So the first element pushed into inputStack becomes the top
   * of outputStack - exactly what we need for a queue!
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * push(1): inputStack=[1], outputStack=[]
   * push(2): inputStack=[1,2], outputStack=[]
   * push(3): inputStack=[1,2,3], outputStack=[]
   *
   * pop(): outputStack empty, transfer!
   *        inputStack=[], outputStack=[3,2,1]
   *        pop from outputStack → returns 1
   *        outputStack=[3,2]
   *
   * push(4): inputStack=[4], outputStack=[3,2]
   *
   * pop(): outputStack not empty, pop directly → returns 2
   *        outputStack=[3]
   * </pre>
   *
   * <p><b>Time Complexity:</b>
   * <br>- Push: O(1) always
   * <br>- Pop/Peek: Amortized O(1) - each element transferred at most once
   * <br><i>Like moving papers between two trays - each paper is moved at most twice
   * in its lifetime (once in, once to output tray). Average cost per operation = constant.</i>
   *
   * <p><b>Space Complexity: O(n)</b>
   * <br>Total elements stored across both stacks = n.
   * <br><i>Like having two in-trays - papers are in one or the other, never both.</i>
   */
  public static class MyQueue {
    // Stack for push operations.
    private final Deque<Integer> inputStack;

    // Stack for pop/peek operations (reversed order).
    private final Deque<Integer> outputStack;

    public MyQueue() {
      inputStack = new ArrayDeque<>();
      outputStack = new ArrayDeque<>();
    }

    /**
     * Pushes element to the back of queue.
     *
     * @param x the element to push
     */
    public void push(int x) {
      inputStack.push(x);
    }

    /**
     * Removes and returns the front element.
     *
     * @return the front element
     */
    public int pop() {
      // Ensure outputStack has elements.
      transferIfNeeded();
      return outputStack.pop();
    }

    /**
     * Gets the front element without removing it.
     *
     * @return the front element
     */
    public int peek() {
      transferIfNeeded();
      return outputStack.peek();
    }

    /**
     * Checks if queue is empty.
     *
     * @return true if empty
     */
    public boolean empty() {
      return inputStack.isEmpty() && outputStack.isEmpty();
    }

    /**
     * Transfers elements from inputStack to outputStack if outputStack is empty.
     * This reverses the order, converting LIFO to FIFO.
     */
    private void transferIfNeeded() {
      if (outputStack.isEmpty()) {
        while (!inputStack.isEmpty()) {
          outputStack.push(inputStack.pop());
        }
      }
    }
  }
}
