package com.example.tutorial.common.datamodel;

import lombok.*;

/**
 * Vehicle data model for parking-related DSA problems.
 * Used in: ParkingLotManager
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

  public enum VehicleType {
    BIG(1),      // Trucks, buses
    MEDIUM(2),   // SUVs, vans
    SMALL(3);    // Cars, motorcycles

    private final int code;

    VehicleType(int code) {
      this.code = code;
    }

    public int getCode() {
      return code;
    }
  }

  private String vehicleId;
  private String licensePlate;
  private VehicleType type;
  private String owner;

  /**
   * Creates a vehicle with basic info.
   */
  public static Vehicle of(String licensePlate, VehicleType type) {
    return Vehicle.builder()
        .vehicleId("VEH-" + System.nanoTime())
        .licensePlate(licensePlate)
        .type(type)
        .build();
  }

  @Override
  public String toString() {
    return String.format("%s (%s) - %s", licensePlate, type, vehicleId);
  }
}
