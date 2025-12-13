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
 * MinStackImplementation
 * ----------------------------------
 * <p>This program implements a stack that supports retrieving minimum element in O(1).
 * The core problem solved here is Min Stack (LeetCode #155).
 *
 * <p><b>Problem Statement:</b>
 * Design a stack that supports push, pop, top, and retrieving the minimum element
 * in constant time.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Track minimum transaction amount in a processing queue</li>
 *   <li>Monitor lowest offer price in a dynamic list</li>
 *   <li>Keep track of minimum wait time in customer service queue</li>
 * </ul>
 *
 * <p><b>Operations:</b>
 * <ul>
 *   <li>push(val) - Push element onto stack</li>
 *   <li>pop() - Remove top element</li>
 *   <li>top() - Get top element</li>
 *   <li>getMin() - Get minimum element in O(1)</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Microsoft, Bloomberg, Apple
 *
 * @see <a href="https://leetcode.com/problems/min-stack/">LeetCode 155 - Min Stack</a>
 */
@Component
public class MinStackImplementation implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(MinStackImplementation.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== MinStackImplementation: Min Stack Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate MinStack with transaction amounts
    System.out.println("--- Tracking Minimum Transaction Amount ---\n");
    MinStack minStack = new MinStack();

    int[] transactions = {500, 200, 300, 100, 400};
    System.out.println("Processing transactions:");

    for (int amount : transactions) {
      minStack.push(amount);
      System.out.println("  Push " + amount + " → Min so far: " + minStack.getMin());
    }

    System.out.println("\nPopping elements:");
    while (!minStack.isEmpty()) {
      System.out.println("  Top: " + minStack.top() + ", Min: " + minStack.getMin());
      minStack.pop();
    }

    // Standard test case from LeetCode
    System.out.println("\n--- LeetCode Example ---\n");
    MinStack stack = new MinStack();
    stack.push(-2);
    System.out.println("push(-2)");
    stack.push(0);
    System.out.println("push(0)");
    stack.push(-3);
    System.out.println("push(-3)");
    System.out.println("getMin() → " + stack.getMin());  // -3
    stack.pop();
    System.out.println("pop()");
    System.out.println("top() → " + stack.top());        // 0
    System.out.println("getMin() → " + stack.getMin());  // -2
  }

  /**
   * MinStack - Stack with O(1) minimum retrieval.
   *
   * <p><b>DESIGN (Two-Stack Approach):</b>
   * <ol>
   *   <li>Main stack: stores all elements normally</li>
   *   <li>Min stack: stores minimum at each level</li>
   *   <li>When pushing: push to main, push min(val, currentMin) to minStack</li>
   *   <li>When popping: pop from both stacks</li>
   *   <li>getMin(): simply peek minStack</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * Operations: push(5), push(3), push(7), push(2), pop(), pop()
   *
   * push(5): mainStack=[5], minStack=[5]
   * push(3): mainStack=[5,3], minStack=[5,3] (3 < 5)
   * push(7): mainStack=[5,3,7], minStack=[5,3,3] (3 < 7)
   * push(2): mainStack=[5,3,7,2], minStack=[5,3,3,2] (2 < 3)
   *
   * getMin() → peek minStack → 2
   *
   * pop(): mainStack=[5,3,7], minStack=[5,3,3]
   * getMin() → 3
   *
   * pop(): mainStack=[5,3], minStack=[5,3]
   * getMin() → 3
   * </pre>
   *
   * <p><b>Time Complexity: O(1) for all operations</b>
   * <br>Push, pop, top, getMin are all constant time.
   * <br><i>Like having two stacks of plates - one regular, one tracking "smallest so far".
   * Every operation just looks at the top plate - instant!</i>
   *
   * <p><b>Space Complexity: O(n)</b>
   * <br>We use two stacks, each potentially holding n elements.
   * <br><i>Like keeping two identical-height stacks - double the storage,
   * but still proportional to number of elements.</i>
   */
  public static class MinStack {
    // Main stack for all elements.
    private final Deque<Integer> stack;

    // Parallel stack tracking minimum at each level.
    private final Deque<Integer> minStack;

    public MinStack() {
      stack = new ArrayDeque<>();
      minStack = new ArrayDeque<>();
    }

    /**
     * Pushes element onto the stack.
     *
     * @param val the value to push
     */
    public void push(int val) {
      stack.push(val);

      // Push current minimum to minStack.
      if (minStack.isEmpty()) {
        minStack.push(val);
      } else {
        minStack.push(Math.min(val, minStack.peek()));
      }
    }

    /**
     * Removes the top element from the stack.
     */
    public void pop() {
      if (!stack.isEmpty()) {
        stack.pop();
        minStack.pop();
      }
    }

    /**
     * Gets the top element.
     *
     * @return the top element
     */
    public int top() {
      return stack.peek();
    }

    /**
     * Retrieves the minimum element in the stack in O(1) time.
     *
     * @return the minimum element
     */
    public int getMin() {
      return minStack.peek();
    }

    /**
     * Checks if stack is empty.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
      return stack.isEmpty();
    }
  }
}
