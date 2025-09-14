package com.example.tutorial.process.transformation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.tutorial.common.utils.SampleDataSupplier.CUSTOMERS;

/**
 * <p>
 * Demonstrates flattening nested collections using Java Streams' {@code flatMap} operation.
 * This is useful when you have a collection of objects, each containing a collection, and you want to process all inner elements as a single stream.
 * </p>
 *
 * <b>Syntax:</b>
 * <pre>
 * {@code
 * List<R> listOfListElements = list<T>.stream()
 *    .flatMap(streamFunction)
 *    .collect(Collectors.toList());
 * }
 * </pre>
 *
 * <p>
 * <b>Explanation:</b>
 * <ul>
 *   <li>{@code list.stream()}: Creates a stream from the outer list.</li>
 *   <li>{@code streamFunction}: Transforms the inner stream to a List. Transforming from T -> R. <br/>
 *       {@code t -> t.getField().stream()} => stream<R>
 *   </li>
 *   <li>{@code collect(Collectors.toSet())}: Collects the flattened elements into a {@code List}.</li>
 * </ul>
 * </p>
 */
@Component
public class DataTransformationFlattening implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataTransformationFlattening.class, args);
  }


  @Override
  public void run(String... args) {
    System.out.println(String.format("Customers size: %d", CUSTOMERS.get().size()));

    Set<String> enrolledOffers =
        CUSTOMERS.get().stream()
            .flatMap(
                c -> c.getEnrolledOffers().stream()
            )
            .collect(Collectors.toSet());
    System.out.println(String.format("Enrolled offers size: %d", enrolledOffers.size()));
    enrolledOffers.forEach(System.out::println);
  }

}
