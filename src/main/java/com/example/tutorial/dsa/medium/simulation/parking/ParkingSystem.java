package com.example.tutorial.dsa.medium.simulation.parking;

/**
 * Simple ParkingSystem class (LeetCode #1603).
 *
 * <p><b>LOGIC (Counter-based):</b>
 * <ol>
 *   <li>Track available spots for each vehicle size</li>
 *   <li>On add, check if spot available for car type</li>
 *   <li>If available, decrement counter and return true</li>
 * </ol>
 *
 * <p><b>Example:</b>
 * <pre>
 * ParkingSystem(1, 1, 0)  // 1 big, 1 medium, 0 small
 *
 * addCar(1) → true   // Park big car
 * addCar(1) → false  // No more big spots
 * addCar(2) → true   // Park medium car
 * addCar(3) → false  // No small spots
 * </pre>
 *
 * <p><b>Time Complexity: O(1)</b> for all operations.
 * <p><b>Space Complexity: O(1)</b> - just 3 counters.
 *
 * @see <a href="https://leetcode.com/problems/design-parking-system/">LeetCode 1603</a>
 */
public class ParkingSystem {
  private final int[] spots;  // [0]=unused, [1]=big, [2]=medium, [3]=small

  /**
   * Creates a parking system with specified capacity.
   *
   * @param big number of big parking spots
   * @param medium number of medium parking spots
   * @param small number of small parking spots
   */
  public ParkingSystem(int big, int medium, int small) {
    spots = new int[] {0, big, medium, small};
  }

  /**
   * Parks a car if a spot is available.
   *
   * @param carType 1=big, 2=medium, 3=small
   * @return true if parked successfully, false if no spot available
   */
  public boolean addCar(int carType) {
    if (carType < 1 || carType > 3) {
      return false;
    }
    if (spots[carType] > 0) {
      spots[carType]--;
      return true;
    }
    return false;
  }

  /**
   * Gets remaining spots for a car type.
   *
   * @param carType 1=big, 2=medium, 3=small
   * @return number of remaining spots
   */
  public int getAvailableSpots(int carType) {
    if (carType < 1 || carType > 3) {
      return 0;
    }
    return spots[carType];
  }
}
