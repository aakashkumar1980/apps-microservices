package com.example.tutorial.process.transformation;

import com.example.tutorial.common.datamodel.campaign.Campaign;
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
   *   <li>{@code map(mapperFunction)}: A function that transforms each element of the stream from T -> R. <br/>
   *       {@code T::getField} => R
   *   </li>
   *   <li>{@code toList()}: Collects the results into a new list.</li>
   * </ul>
   * </p>
   */
  @Override
  public void run(String... args) {
    System.out.println(String.format("Campaigns size: %d", CAMPAIGNS.get().size()));

    /** Map Campaign to Campaign Name */
    List<String> campaignNames =
        CAMPAIGNS.get().stream()
            .map(Campaign::getName)
            .toList();
    System.out.println(String.format("campaignNames size: %d", campaignNames.size()));
    campaignNames.forEach(System.out::println);
  }

}
