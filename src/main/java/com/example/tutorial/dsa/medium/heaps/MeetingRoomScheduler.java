package com.example.tutorial.dsa.medium.heaps;

import com.example.tutorial.common.datamodel.Meeting;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * MeetingRoomScheduler
 * ----------------------------------
 * <p>This program finds minimum meeting rooms required using Min Heap.
 * The core problem solved here is Meeting Rooms II (LeetCode #253).
 *
 * <p><b>Problem Statement:</b>
 * Given an array of meeting time intervals, return the minimum number of
 * conference rooms required.
 *
 * <p><b>Real UseCase:</b>
 * <ul>
 *   <li>Conference room booking systems</li>
 *   <li>Calendar scheduling applications</li>
 *   <li>Resource allocation for overlapping tasks</li>
 *   <li>Server capacity planning for batch jobs</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: [[0,30],[5,10],[15,20]] → Output: 2</li>
 *   <li>Input: [[7,10],[2,4]] → Output: 1 (no overlap)</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Facebook, Google, Bloomberg
 *
 * @see <a href="https://leetcode.com/problems/meeting-rooms-ii/">LeetCode 253 - Meeting Rooms II</a>
 */
@Component
public class MeetingRoomScheduler implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(MeetingRoomScheduler.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== MeetingRoomScheduler: Minimum Rooms Required Demo ===\n");

    // Load sample meetings - appropriate for scheduling demos
    List<Meeting> meetings = SampleDataLoader.MEETINGS_DTO.get();
    System.out.println("Loaded " + meetings.size() + " meetings from sample data.\n");

    // Display meetings
    System.out.println("--- Today's Meeting Schedule ---\n");
    for (Meeting meeting : meetings) {
      System.out.println("  " + meeting);
    }

    // Convert meetings to intervals and calculate rooms needed
    int[][] intervals = meetings.stream()
        .map(Meeting::toInterval)
        .toArray(int[][]::new);

    int roomsNeeded = minMeetingRooms(intervals);
    System.out.println("\nMinimum conference rooms needed: " + roomsNeeded + "\n");

    // Additional test cases
    System.out.println("--- Additional Examples ---\n");
    int[][][] testCases = {
        {{0, 30}, {5, 10}, {15, 20}},
        {{7, 10}, {2, 4}},
        {{1, 5}, {2, 3}, {3, 6}, {5, 7}}
    };

    for (int[][] testIntervals : testCases) {
      int result = minMeetingRooms(testIntervals);
      System.out.println("Intervals: " + formatIntervals(testIntervals));
      System.out.println("Minimum rooms: " + result + "\n");
    }
  }

  /**
   * Finds minimum meeting rooms required using Min Heap.
   *
   * <p><b>LOGIC (Min Heap for End Times):</b>
   * <ol>
   *   <li>Sort meetings by start time</li>
   *   <li>Use min heap to track end times of ongoing meetings</li>
   *   <li>For each meeting:
   *     <ul>
   *       <li>Remove all meetings that ended before current starts (pop from heap)</li>
   *       <li>Add current meeting's end time to heap</li>
   *     </ul>
   *   </li>
   *   <li>Heap size at any point = rooms in use</li>
   *   <li>Track maximum heap size seen</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * intervals = [[0,30], [5,10], [15,20]]
   * After sorting by start: [[0,30], [5,10], [15,20]]
   * min-heap tracks end times
   *
   * Process [0,30]: heap empty, add 30 → heap = [30], rooms = 1
   * Process [5,10]: heap.peek()=30 > 5, meeting ongoing
   *                 add 10 → heap = [10, 30], rooms = 2
   * Process [15,20]: heap.peek()=10 < 15, that room is free!
   *                  pop 10, add 20 → heap = [20, 30], rooms = 2
   *
   * Max rooms needed: 2
   * </pre>
   *
   * <p><b>Time Complexity: O(n log n)</b>
   * <br>Sorting takes O(n log n), each heap operation O(log n).
   * <br><i>Like a hotel receptionist sorting check-in times first, then tracking
   * which rooms are occupied using a quick-lookup system.</i>
   *
   * <p><b>Space Complexity: O(n)</b>
   * <br>Heap may contain all n meetings if all overlap.
   * <br><i>Like the hotel's "occupied rooms" board - worst case all rooms are full.</i>
   *
   * @param intervals array of [start, end] meeting times
   * @return minimum number of meeting rooms required
   */
  public static int minMeetingRooms(int[][] intervals) {
    if (intervals == null || intervals.length == 0) {
      return 0;
    }

    // Sort by start time.
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

    // Min heap to track end times of ongoing meetings.
    PriorityQueue<Integer> endTimes = new PriorityQueue<>();

    // Process each meeting.
    for (int[] interval : intervals) {
      int start = interval[0];
      int end = interval[1];

      // If earliest ending meeting has ended, that room is free.
      if (!endTimes.isEmpty() && endTimes.peek() <= start) {
        endTimes.poll();  // Free up the room.
      }

      // Allocate room for current meeting.
      endTimes.offer(end);
    }

    // Heap size = rooms currently in use = max rooms needed.
    return endTimes.size();
  }

  /**
   * Overloaded method that accepts Meeting objects directly.
   *
   * @param meetings list of Meeting objects
   * @return minimum number of meeting rooms required
   */
  public static int minMeetingRooms(List<Meeting> meetings) {
    int[][] intervals = meetings.stream()
        .map(Meeting::toInterval)
        .toArray(int[][]::new);
    return minMeetingRooms(intervals);
  }

  private static String formatIntervals(int[][] intervals) {
    StringBuilder sb = new StringBuilder("[");
    for (int i = 0; i < intervals.length; i++) {
      sb.append("[").append(intervals[i][0]).append(",").append(intervals[i][1]).append("]");
      if (i < intervals.length - 1) sb.append(", ");
    }
    return sb.append("]").toString();
  }
}
