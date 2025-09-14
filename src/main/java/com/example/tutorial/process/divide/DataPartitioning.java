package com.example.tutorial.process.divide;

import com.example.tutorial.common.datamodel.customer.Customer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.tutorial.common.utils.SampleDataSupplier.CUSTOMERS;

@Component
public class DataPartitioning implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataPartitioning.class, args);
  }


  /**
   * Partitions elements in a list into two groups based on a predicate using Java Streams.
   *
   * <p><b>Syntax:</b></p>
   * <pre>{@code
   * Map<Boolean, List<T>> groupByConditionMap = list<T>.stream()
   *     .collect(collector);
   * }</pre>
   *
   * <p><b>Explanation:</b></p>
   * <ul>
   *   <li><code>list.stream()</code>: Creates a stream from the list.</li>
   *   <li><code>[collect(collector)]</code> <br/>
   *       <code>Collectors.partitioningBy(predicateFunction, collector)</code>: Partitions the elements into two groups (true/false) based on the predicate.
   *       <ul>
   *         <li><code>predicateFunction</code>: A function that evaluates each element and returns a boolean value (true or false) based on a condition. e.g., <br/>
   *            <code>t -> t.getField().equals(valueToCompare)</code> <br/>
   *            <code>T::isBooleanField</code>
   *         </li>
   *         <li><code>collector</code>: A downstream collector that defines how to collect the elements in each partition. e.g., <br/>
   *         <code>Collectors.toList()</code></li>
   *       </ul>
   *   </li>
   * </ul>
   */
  @Override
  public void run(String... args) throws Exception {
    System.out.println(String.format("Customers size: %d", CUSTOMERS.get().size()));

    Map<Boolean, List<Customer>> customersEnrolledToOffers =
        CUSTOMERS.get().stream()
            .collect(
                Collectors.partitioningBy(
                    c -> c.getEnrolledOffers().isEmpty(),
                    Collectors.toList()
                )
            );

    System.out.println(String.format("Customers not enrolled to offers: %d", customersEnrolledToOffers.get(true).size()));
    customersEnrolledToOffers.get(true).forEach(c -> System.out.println(c));
    System.out.println(String.format("Customers enrolled to offers: %d", customersEnrolledToOffers.get(false).size()));
    customersEnrolledToOffers.get(false).forEach(c -> System.out.println(c));
  }
}
