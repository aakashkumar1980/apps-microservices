package com.example.tutorial.dsa.medium.simulation.elevator;

import java.util.Collections;
import java.util.TreeSet;

/**
 * Individual Elevator class implementing SCAN algorithm.
 *
 * <p><b>SCAN Algorithm (Elevator Algorithm):</b>
 * <ol>
 *   <li>Elevator moves in one direction servicing all requests</li>
 *   <li>When reaching the end, reverses direction</li>
 *   <li>Similar to how a disk arm moves across platters</li>
 * </ol>
 *
 * <p><b>State Tracking:</b>
 * <ul>
 *   <li>currentFloor: where elevator currently is</li>
 *   <li>direction: 1=up, -1=down, 0=idle</li>
 *   <li>upStops: floors to visit while going up (TreeSet for ordering)</li>
 *   <li>downStops: floors to visit while going down (reverse TreeSet)</li>
 * </ul>
 */
public class Elevator {
  private final int id;
  private final int numFloors;
  private int currentFloor;
  private int direction;  // 1=up, -1=down, 0=idle
  private final TreeSet<Integer> upStops;
  private final TreeSet<Integer> downStops;

  public Elevator(int id, int numFloors) {
    this.id = id;
    this.numFloors = numFloors;
    this.currentFloor = 1;
    this.direction = 0;
    this.upStops = new TreeSet<>();
    this.downStops = new TreeSet<>(Collections.reverseOrder());
  }

  /**
   * Calculates score for this elevator to handle a request.
   * Lower score = better choice for assignment.
   *
   * <p><b>Scoring Logic:</b>
   * <ul>
   *   <li>Idle elevator: just distance to floor</li>
   *   <li>Moving towards request in same direction: just distance</li>
   *   <li>Moving away or opposite direction: distance + penalty</li>
   * </ul>
   *
   * @param floor the requested floor
   * @param requestDir requested direction (1=up, -1=down)
   * @return score (lower is better)
   */
  public int getScore(int floor, int requestDir) {
    int distance = Math.abs(currentFloor - floor);

    // Idle elevator - just use distance.
    if (direction == 0) {
      return distance;
    }

    // Moving towards request floor and same direction - best case.
    if (direction == requestDir) {
      if ((direction == 1 && floor >= currentFloor)
          || (direction == -1 && floor <= currentFloor)) {
        return distance;
      }
    }

    // Will need to change direction - add penalty.
    return distance + numFloors;
  }

  /**
   * Adds a stop to this elevator's queue.
   *
   * @param floor the floor to stop at
   */
  public void addStop(int floor) {
    if (floor > currentFloor || (direction == 0 && floor != currentFloor)) {
      upStops.add(floor);
    }
    if (floor < currentFloor || (direction == 0 && floor != currentFloor)) {
      downStops.add(floor);
    }

    // Set direction if idle.
    if (direction == 0) {
      if (!upStops.isEmpty()) {
        direction = 1;
      } else if (!downStops.isEmpty()) {
        direction = -1;
      }
    }
  }

  /**
   * Advances elevator by one floor.
   */
  public void step() {
    if (direction == 0) {
      return;  // Idle, nothing to do.
    }

    // Move one floor in current direction.
    currentFloor += direction;

    // Check if we've arrived at a stop.
    if (direction == 1 && upStops.contains(currentFloor)) {
      upStops.remove(currentFloor);
    } else if (direction == -1 && downStops.contains(currentFloor)) {
      downStops.remove(currentFloor);
    }

    // Check if we need to change direction.
    if (direction == 1 && upStops.isEmpty()) {
      direction = downStops.isEmpty() ? 0 : -1;
    } else if (direction == -1 && downStops.isEmpty()) {
      direction = upStops.isEmpty() ? 0 : 1;
    }
  }

  public int getId() {
    return id;
  }

  public int getCurrentFloor() {
    return currentFloor;
  }

  public int getDirection() {
    return direction;
  }

  public String getDirectionString() {
    return direction == 1 ? "UP" : (direction == -1 ? "DOWN" : "IDLE");
  }

  @Override
  public String toString() {
    return "Elevator " + id + ": Floor " + currentFloor + ", " + getDirectionString()
        + ", upStops=" + upStops + ", downStops=" + downStops;
  }
}
