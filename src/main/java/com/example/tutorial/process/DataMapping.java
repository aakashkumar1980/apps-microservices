package com.example.tutorial.process;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.tutorial.common.utils.SampleDataSupplier.CAMPAIGNS;

@Component
public class DataMapping implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataMapping.class, args);
  }

  /**
   * Data Mapping (conversion) Example
   * <p>
   *   In this example, we will map a list of {@code Campaign} objects to a list of their names.
   *   We will use Java Streams to perform the mapping operation.
   * </p>
   * <b>Syntax:</b>
   * <pre>
   *   list.stream()
   *     .map(f), where f is a function that transforms an element of the stream, e.g.
   *        c -> {
   *            return c.getName();
   *        }, OR
   *        c -> c.getName(), OR
   *        Campaign::getName
   *     .toList();
   * </pre>
   */
  @Override
  public void run(String... args) {
    System.out.println(String.format("Campaigns size: %d", CAMPAIGNS.get().size()));

    List<String> campaignNames =
        CAMPAIGNS.get().stream()
            .map(c -> {
                  return c.getName();
                }
            )
            .toList();
    System.out.println(String.format("Campaigns size: %d", campaignNames.size()));
    campaignNames.forEach(System.out::println);
  }

}
