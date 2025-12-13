package com.example.tutorial.dsa.medium.heaps;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * TaskSchedulerOptimizer
 * ----------------------------------
 * <p>This program finds minimum time to complete all tasks with cooldown using Greedy + Heap.
 * The core problem solved here is Task Scheduler (LeetCode #621).
 *
 * <p><b>Problem Statement:</b>
 * Given a list of tasks and a cooldown period n, find the minimum time units to
 * complete all tasks. Same task must have at least n intervals between them.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Schedule API calls with rate limiting cooldowns</li>
 *   <li>Process batch jobs with resource cooldown constraints</li>
 *   <li>Send promotional emails with minimum gap between same customer</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: tasks = ["A","A","A","B","B","B"], n = 2 → Output: 8</li>
 *   <li>Input: tasks = ["A","A","A","B","B","B"], n = 0 → Output: 6</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Facebook, Amazon, Microsoft (⭐ Very Popular!)
 *
 * @see <a href="https://leetcode.com/problems/task-scheduler/">LeetCode 621 - Task Scheduler</a>
 */
@Component
public class TaskSchedulerOptimizer implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(TaskSchedulerOptimizer.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== TaskSchedulerOptimizer: Task Scheduling with Cooldown Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate with API call scheduling
    System.out.println("--- Scheduling API Calls with Rate Limiting ---\n");
    char[] apiCalls = {'P', 'P', 'P', 'Q', 'Q', 'R', 'R', 'R', 'R'};
    int cooldown = 2;
    System.out.println("API endpoints to call: " + Arrays.toString(apiCalls));
    System.out.println("Rate limit cooldown: " + cooldown + " time units");
    int totalTime = leastInterval(apiCalls, cooldown);
    System.out.println("Minimum time to complete: " + totalTime + " units\n");

    // Test cases
    System.out.println("--- Additional Examples ---\n");
    char[][] testTasks = {
        {'A', 'A', 'A', 'B', 'B', 'B'},
        {'A', 'A', 'A', 'B', 'B', 'B'},
        {'A', 'A', 'A', 'A', 'A', 'A', 'B', 'C', 'D', 'E', 'F', 'G'}
    };
    int[] cooldowns = {2, 0, 2};

    for (int i = 0; i < testTasks.length; i++) {
      char[] tasks = testTasks[i];
      int n = cooldowns[i];
      int result = leastInterval(tasks, n);
      System.out.println("Tasks: " + Arrays.toString(tasks));
      System.out.println("Cooldown n = " + n);
      System.out.println("Minimum intervals: " + result + "\n");
    }
  }

  /**
   * Finds minimum intervals to complete all tasks with cooldown using Math/Greedy approach.
   *
   * <p><b>LOGIC (Math + Greedy):</b>
   * <ol>
   *   <li>Count frequency of each task</li>
   *   <li>Find the maximum frequency (most common task)</li>
   *   <li>Count how many tasks have this max frequency</li>
   *   <li>Calculate: (maxFreq - 1) * (n + 1) + countMaxFreq</li>
   *   <li>Result = max(calculated value, total tasks)</li>
   * </ol>
   *
   * <p><b>Why this formula works:</b>
   * <br>Think of scheduling in "cycles" of size (n+1):
   * <pre>
   * tasks = [A,A,A,B,B,B], n = 2
   *
   * Most frequent: A and B, both with freq=3
   * We need (3-1) = 2 full cycles, then 1 final partial
   *
   * Cycle 1: A B _ (3 slots)
   * Cycle 2: A B _ (3 slots)
   * Final:   A B   (just the max-freq tasks)
   *
   * Total = (3-1) * 3 + 2 = 8
   *
   * If we had more tasks, they'd fill the '_' idle slots.
   * If tasks > calculated, we don't need any idle time.
   * </pre>
   *
   * <p><b>Time Complexity: O(n)</b>
   * <br>Count frequencies in O(n), rest is O(1) or O(26) for 26 letters.
   * <br><i>Like counting colored balls once - after counting, you immediately
   * know how to optimally arrange them.</i>
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>Fixed array of size 26 (or constant for character set).
   * <br><i>Like using 26 labeled buckets - doesn't grow with input.</i>
   *
   * @param tasks array of task characters
   * @param n cooldown period (must wait n intervals between same task)
   * @return minimum intervals needed
   */
  public static int leastInterval(char[] tasks, int n) {
    // Count frequency of each task.
    int[] freq = new int[26];
    for (char task : tasks) {
      freq[task - 'A']++;
    }

    // Find maximum frequency.
    int maxFreq = 0;
    for (int f : freq) {
      maxFreq = Math.max(maxFreq, f);
    }

    // Count how many tasks have maximum frequency.
    int countMaxFreq = 0;
    for (int f : freq) {
      if (f == maxFreq) {
        countMaxFreq++;
      }
    }

    // Calculate minimum intervals.
    // (maxFreq - 1) complete cycles of size (n + 1)
    // + final cycle with just the max-frequency tasks.
    int calculated = (maxFreq - 1) * (n + 1) + countMaxFreq;

    // If we have more tasks than idle slots, no idle time needed.
    return Math.max(calculated, tasks.length);
  }

  /**
   * Alternative approach using Max Heap + Queue (simulation).
   * More intuitive but O(n * m) time where m = total intervals.
   */
  public static int leastIntervalWithHeap(char[] tasks, int n) {
    // Count frequencies.
    int[] freq = new int[26];
    for (char task : tasks) {
      freq[task - 'A']++;
    }

    // Max heap of remaining counts.
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    for (int f : freq) {
      if (f > 0) {
        maxHeap.offer(f);
      }
    }

    // Queue to track tasks in cooldown: [remaining count, available time].
    Queue<int[]> cooldownQueue = new LinkedList<>();

    int time = 0;

    while (!maxHeap.isEmpty() || !cooldownQueue.isEmpty()) {
      time++;

      // If a task is available, process it.
      if (!maxHeap.isEmpty()) {
        int count = maxHeap.poll() - 1;
        if (count > 0) {
          // Put in cooldown queue.
          cooldownQueue.offer(new int[] {count, time + n});
        }
      }

      // Check if any task finished cooldown.
      if (!cooldownQueue.isEmpty() && cooldownQueue.peek()[1] == time) {
        maxHeap.offer(cooldownQueue.poll()[0]);
      }
    }

    return time;
  }
}
