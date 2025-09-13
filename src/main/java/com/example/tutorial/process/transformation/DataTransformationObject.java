package com.example.tutorial.process.transformation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.tutorial.common.utils.SampleDataSupplier.CAMPAIGNS;

@Component
public class DataTransformationObject implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataTransformationObject.class, args);
  }

  /**
   * Data Mapping Example
   * <p>
   *  In this example, we will map a list of {@code Campaign} objects to a list of their names.
   *  We will use Java Streams to perform the mapping operation.
   * </p>
   * <b>Syntax:</b>
   * <pre>
   * {@code
   * List<R> transformedList = list<T>.stream()
   *    .map(function)
   *    .toList();
   * }
   * </pre>
   * <p>
   * <b>Explanation:</b>
   * <ul>
   *   <li>{@code list.stream()}: Creates a stream from the list.</li>
   *   <li>{@code .map(function)}: Applies the function {@code function} to each element, transforming it from T -> R.</li>
   *   <li>{@code .toList()}: Collects the results into a new list.</li>
   *   <li>Examples of mapping functions:
   *     <ul>
   *       <li>{@code t -> t.getName()}</li>
   *       <li>{@code Campaign::getName}</li>
   *     </ul>
   *   </li>
   * </ul>
   * </p>
   */
  @Override
  public void run(String... args) {
    System.out.println(String.format("Campaigns size: %d", CAMPAIGNS.get().size()));

    /** Map Campaign to Campaign Name */
    List<String> campaignNames =
        CAMPAIGNS.get().stream()
            .map(c -> {
                  return c.getName();
                }
            )
            .toList();
    System.out.println(String.format("campaignNames size: %d", campaignNames.size()));
    campaignNames.forEach(System.out::println);
  }

}
