package com.example.tutorial.common.datamodel;

import lombok.*;

/**
 * Meeting data model for scheduling-related DSA problems.
 * Used in: MeetingRoomScheduler, DateTimeScheduler
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meeting {
  private String meetingId;
  private String title;
  private int startTime;  // Minutes from midnight (e.g., 540 = 9:00 AM)
  private int endTime;    // Minutes from midnight
  private String organizer;
  private String room;

  /**
   * Creates a Meeting with start and end times as hours.
   */
  public static Meeting ofHours(String title, int startHour, int endHour, String organizer) {
    return Meeting.builder()
        .meetingId("MTG-" + System.nanoTime())
        .title(title)
        .startTime(startHour * 60)
        .endTime(endHour * 60)
        .organizer(organizer)
        .build();
  }

  /**
   * Returns time in HH:MM format.
   */
  public String getStartTimeFormatted() {
    return String.format("%02d:%02d", startTime / 60, startTime % 60);
  }

  public String getEndTimeFormatted() {
    return String.format("%02d:%02d", endTime / 60, endTime % 60);
  }

  /**
   * Returns interval as [start, end] array (for algorithm compatibility).
   */
  public int[] toInterval() {
    return new int[] {startTime, endTime};
  }

  @Override
  public String toString() {
    return String.format("%s (%s - %s) by %s",
        title, getStartTimeFormatted(), getEndTimeFormatted(), organizer);
  }
}
