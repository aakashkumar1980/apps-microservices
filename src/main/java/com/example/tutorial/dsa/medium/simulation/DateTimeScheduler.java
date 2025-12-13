package com.example.tutorial.dsa.medium.simulation;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * DateTimeScheduler
 * ----------------------------------
 * <p>This program implements a meeting room scheduler with conflict detection.
 * The core problem solved here is Meeting Rooms II (LeetCode #253).
 *
 * <p><b>Problem Statement:</b>
 * Given an array of meeting time intervals, find the minimum number of
 * conference rooms required to hold all meetings.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Schedule promotional offer campaigns without overlap</li>
 *   <li>Allocate resources for batch transaction processing windows</li>
 *   <li>Plan customer service availability slots</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Google, Facebook, Amazon, Bloomberg
 *
 * @see <a href="https://leetcode.com/problems/meeting-rooms-ii/">LeetCode 253 - Meeting Rooms II</a>
 */
@Component
public class DateTimeScheduler implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DateTimeScheduler.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== DateTimeScheduler: Meeting Room Scheduler Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Example 1: Basic meeting scheduling
    System.out.println("--- Example 1: Basic Meeting Schedule ---\n");
    int[][] intervals1 = {{0, 30}, {5, 10}, {15, 20}};
    printIntervals(intervals1);
    int rooms1 = minMeetingRooms(intervals1);
    System.out.println("Minimum rooms needed: " + rooms1 + "\n");

    // Example 2: No overlapping meetings
    System.out.println("--- Example 2: Non-overlapping Meetings ---\n");
    int[][] intervals2 = {{7, 10}, {2, 4}};
    printIntervals(intervals2);
    int rooms2 = minMeetingRooms(intervals2);
    System.out.println("Minimum rooms needed: " + rooms2 + "\n");

    // Example 3: All meetings overlap
    System.out.println("--- Example 3: All Meetings Overlap ---\n");
    int[][] intervals3 = {{1, 5}, {2, 6}, {3, 7}, {4, 8}};
    printIntervals(intervals3);
    int rooms3 = minMeetingRooms(intervals3);
    System.out.println("Minimum rooms needed: " + rooms3 + "\n");

    // Alternative approach demonstration
    System.out.println("--- Sweep Line Algorithm Approach ---\n");
    int rooms4 = minMeetingRoomsSweepLine(intervals1);
    System.out.println("Intervals: " + Arrays.deepToString(intervals1));
    System.out.println("Minimum rooms (sweep line): " + rooms4 + "\n");
  }

  private static void printIntervals(int[][] intervals) {
    System.out.println("Meeting intervals:");
    for (int[] interval : intervals) {
      System.out.println("  [" + interval[0] + ", " + interval[1] + "]");
    }
  }

  /**
   * Finds minimum meeting rooms using min-heap (Priority Queue).
   *
   * <p><b>LOGIC (Min-Heap for End Times):</b>
   * <ol>
   *   <li>Sort meetings by start time</li>
   *   <li>Use min-heap to track end times of ongoing meetings</li>
   *   <li>For each meeting: if earliest ending meeting ends before current starts,
   *       reuse that room (poll from heap)</li>
   *   <li>Add current meeting's end time to heap</li>
   *   <li>Heap size = rooms in use at peak</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * Intervals: [[0,30], [5,10], [15,20]]
   * Sorted by start: [[0,30], [5,10], [15,20]]
   *
   * Process [0,30]: heap empty → add 30, heap = [30], rooms = 1
   * Process [5,10]: peek=30 > 5 → can't reuse, add 10, heap = [10,30], rooms = 2
   * Process [15,20]: peek=10 < 15 → reuse! poll 10, add 20, heap = [20,30], rooms = 2
   *
   * Max rooms needed: 2
   * </pre>
   *
   * <p><b>Time Complexity: O(n log n)</b>
   * <br>Sorting takes O(n log n), each heap operation is O(log n).
   * <br><i>Like managing hotel checkouts - track when rooms become available
   * and assign guests to free rooms efficiently.</i>
   *
   * <p><b>Space Complexity: O(n)</b>
   * <br>Heap can hold up to n end times.
   *
   * @param intervals array of [start, end] meeting times
   * @return minimum number of conference rooms required
   */
  public static int minMeetingRooms(int[][] intervals) {
    if (intervals == null || intervals.length == 0) {
      return 0;
    }

    // Sort by start time.
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

    // Min-heap to track end times.
    PriorityQueue<Integer> endTimes = new PriorityQueue<>();

    for (int[] interval : intervals) {
      // If earliest ending meeting ends before current starts, reuse that room.
      if (!endTimes.isEmpty() && endTimes.peek() <= interval[0]) {
        endTimes.poll();
      }
      // Add current meeting's end time.
      endTimes.offer(interval[1]);
    }

    return endTimes.size();
  }

  /**
   * Alternative approach using sweep line algorithm.
   *
   * <p><b>LOGIC (Event-based Sweep Line):</b>
   * <ol>
   *   <li>Create events: +1 for start, -1 for end</li>
   *   <li>Sort events by time (end before start at same time)</li>
   *   <li>Sweep through events, tracking concurrent meetings</li>
   *   <li>Maximum concurrent = rooms needed</li>
   * </ol>
   *
   * <p><b>Time Complexity: O(n log n)</b> for sorting events.
   * <p><b>Space Complexity: O(n)</b> for storing events.
   *
   * @param intervals array of [start, end] meeting times
   * @return minimum number of conference rooms required
   */
  public static int minMeetingRoomsSweepLine(int[][] intervals) {
    if (intervals == null || intervals.length == 0) {
      return 0;
    }

    // Create events: [time, type] where type: 1=start, -1=end.
    List<int[]> events = new ArrayList<>();
    for (int[] interval : intervals) {
      events.add(new int[] {interval[0], 1});   // Meeting starts.
      events.add(new int[] {interval[1], -1});  // Meeting ends.
    }

    // Sort by time; if same time, process ends (-1) before starts (1).
    events.sort((a, b) -> {
      if (a[0] != b[0]) {
        return a[0] - b[0];
      }
      return a[1] - b[1];  // End (-1) before start (1).
    });

    int currentRooms = 0;
    int maxRooms = 0;

    for (int[] event : events) {
      currentRooms += event[1];
      maxRooms = Math.max(maxRooms, currentRooms);
    }

    return maxRooms;
  }
}
