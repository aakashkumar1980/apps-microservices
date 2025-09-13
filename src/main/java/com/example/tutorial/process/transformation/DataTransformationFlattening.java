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
 * Set<R> result = list<T>.stream()
 *    .flatMap(streamFunction<R>)
 *    .collect(Collectors.toSet());
 * }
 * </pre>
 *
 * <p>
 * <b>Explanation:</b>
 * <ul>
 *   <li>{@code list.stream()}: Creates a stream from the outer list.</li>
 *   <li>{@code streamFunction<R>}: A function that takes an element of type T and returns a stream of elements of type R.
 *    <pre>{@code
 *      t -> t.getCollectionAttribute().stream() => stream<R>
 *    }</pre>
 *   </li>
 *   <li>{@code collect(Collectors.toSet())}: Collects the flattened elements into a {@code Set}.</li>
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
