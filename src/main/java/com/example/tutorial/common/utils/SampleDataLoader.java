package com.example.tutorial.common.utils;

import com.example.tutorial.common.datamodel.Meeting;
import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.datamodel.Task;
import com.example.tutorial.common.datamodel.Vehicle;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public final class SampleDataLoader {

  private static final ObjectMapper MAPPER = new ObjectMapper()
      .registerModule(new JavaTimeModule())
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  /**
   * Sample credit card offers data.
   */
  public static final Supplier<List<Offer>> OFFERS_DTO =
      () -> {
        try {
          String json = Files.readString(Paths.get("src/main/resources/sample_data/offer.json"));
          return (List<Offer>) MAPPER.readValue(json, new TypeReference<List<Offer>>() {
          });
        } catch (Exception e) {
          e.printStackTrace();
        }
        return List.of();
      };

  /**
   * Sample meeting schedule data for scheduling algorithms.
   * Use case: Meeting room allocation, calendar scheduling.
   */
  public static final Supplier<List<Meeting>> MEETINGS_DTO = () -> Arrays.asList(
      Meeting.builder().meetingId("MTG-001").title("Sprint Planning").startTime(540).endTime(600)
          .organizer("John").build(),    // 9:00-10:00
      Meeting.builder().meetingId("MTG-002").title("Design Review").startTime(570).endTime(630)
          .organizer("Sarah").build(),   // 9:30-10:30 (overlaps with MTG-001)
      Meeting.builder().meetingId("MTG-003").title("Daily Standup").startTime(600).endTime(615)
          .organizer("Team").build(),    // 10:00-10:15
      Meeting.builder().meetingId("MTG-004").title("Client Call").startTime(660).endTime(720)
          .organizer("Mike").build(),    // 11:00-12:00
      Meeting.builder().meetingId("MTG-005").title("Lunch & Learn").startTime(720).endTime(780)
          .organizer("HR").build(),      // 12:00-13:00
      Meeting.builder().meetingId("MTG-006").title("Code Review").startTime(840).endTime(900)
          .organizer("Dev Team").build(),// 14:00-15:00
      Meeting.builder().meetingId("MTG-007").title("1:1 with Manager").startTime(900).endTime(930)
          .organizer("Jane").build(),    // 15:00-15:30
      Meeting.builder().meetingId("MTG-008").title("Architecture Discussion").startTime(870).endTime(960)
          .organizer("Tech Lead").build() // 14:30-16:00 (overlaps with MTG-006, MTG-007)
  );

  /**
   * Sample vehicle data for parking lot algorithms.
   * Use case: Parking lot management, resource allocation.
   */
  public static final Supplier<List<Vehicle>> VEHICLES_DTO = () -> Arrays.asList(
      Vehicle.builder().vehicleId("VEH-001").licensePlate("ABC-1234").type(Vehicle.VehicleType.SMALL)
          .owner("Alice").build(),
      Vehicle.builder().vehicleId("VEH-002").licensePlate("XYZ-5678").type(Vehicle.VehicleType.MEDIUM)
          .owner("Bob").build(),
      Vehicle.builder().vehicleId("VEH-003").licensePlate("DEF-9012").type(Vehicle.VehicleType.BIG)
          .owner("Charlie").build(),
      Vehicle.builder().vehicleId("VEH-004").licensePlate("GHI-3456").type(Vehicle.VehicleType.SMALL)
          .owner("Diana").build(),
      Vehicle.builder().vehicleId("VEH-005").licensePlate("JKL-7890").type(Vehicle.VehicleType.MEDIUM)
          .owner("Eve").build(),
      Vehicle.builder().vehicleId("VEH-006").licensePlate("MNO-2345").type(Vehicle.VehicleType.SMALL)
          .owner("Frank").build()
  );

  /**
   * Sample task data for producer-consumer and concurrent processing.
   * Use case: Task queues, thread pool demos, async processing.
   */
  public static final Supplier<List<Task>> TASKS_DTO = () -> Arrays.asList(
      Task.builder().taskId("TASK-001").name("Process Payment").priority(Task.Priority.HIGH)
          .status(Task.Status.PENDING).createdAt(System.currentTimeMillis()).build(),
      Task.builder().taskId("TASK-002").name("Send Notification").priority(Task.Priority.MEDIUM)
          .status(Task.Status.PENDING).createdAt(System.currentTimeMillis()).build(),
      Task.builder().taskId("TASK-003").name("Generate Report").priority(Task.Priority.LOW)
          .status(Task.Status.PENDING).createdAt(System.currentTimeMillis()).build(),
      Task.builder().taskId("TASK-004").name("Validate Transaction").priority(Task.Priority.CRITICAL)
          .status(Task.Status.PENDING).createdAt(System.currentTimeMillis()).build(),
      Task.builder().taskId("TASK-005").name("Update Cache").priority(Task.Priority.MEDIUM)
          .status(Task.Status.PENDING).createdAt(System.currentTimeMillis()).build(),
      Task.builder().taskId("TASK-006").name("Sync Database").priority(Task.Priority.HIGH)
          .status(Task.Status.PENDING).createdAt(System.currentTimeMillis()).build(),
      Task.builder().taskId("TASK-007").name("Archive Logs").priority(Task.Priority.LOW)
          .status(Task.Status.PENDING).createdAt(System.currentTimeMillis()).build(),
      Task.builder().taskId("TASK-008").name("Fraud Check").priority(Task.Priority.CRITICAL)
          .status(Task.Status.PENDING).createdAt(System.currentTimeMillis()).build()
  );
}
