package com.example.tutorial.common.datamodel;

import lombok.*;

/**
 * Task data model for producer-consumer and concurrent processing patterns.
 * Used in: ProducerConsumerPattern, ThreadPoolExecutorDemo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

  public enum Priority {
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    CRITICAL(4);

    private final int level;

    Priority(int level) {
      this.level = level;
    }

    public int getLevel() {
      return level;
    }
  }

  public enum Status {
    PENDING, IN_PROGRESS, COMPLETED, FAILED
  }

  private String taskId;
  private String name;
  private String description;
  private Priority priority;
  private Status status;
  private long createdAt;
  private long completedAt;

  /**
   * Creates a new pending task.
   */
  public static Task of(String name, Priority priority) {
    return Task.builder()
        .taskId("TASK-" + System.nanoTime())
        .name(name)
        .priority(priority)
        .status(Status.PENDING)
        .createdAt(System.currentTimeMillis())
        .build();
  }

  /**
   * Creates a simple task with medium priority.
   */
  public static Task of(String name) {
    return of(name, Priority.MEDIUM);
  }

  /**
   * Marks task as completed.
   */
  public void complete() {
    this.status = Status.COMPLETED;
    this.completedAt = System.currentTimeMillis();
  }

  @Override
  public String toString() {
    return String.format("[%s] %s (%s)", priority, name, status);
  }
}
