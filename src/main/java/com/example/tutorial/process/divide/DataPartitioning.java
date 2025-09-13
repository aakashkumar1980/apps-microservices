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
   *     .collect(Collectors.partitioningBy(
   *        predicate,
   *        Collectors.toList()
   *     ));
   * }</pre>
   *
   * <p><b>Explanation:</b></p>
   * <ul>
   *   <li><code>list.stream()</code>: Creates a stream from the list.</li>
   *   <li><code>Collectors.partitioningBy(predicate, Collectors.toList())</code>: Partitions the elements into two groups based on the predicate <code>p</code>.</li>
   *   <li>The result is a <code>Map&lt;Boolean, List&lt;T&gt;&gt;</code> where:
   *     <ul>
   *       <li>The key <code>true</code> corresponds to elements that match the predicate.</li>
   *       <li>The key <code>false</code> corresponds to elements that do not match the predicate.</li>
   *     </ul>
   *   </li>
   *   <li>Examples of predicates:
   *     <ul>
   *       <li><code>t -&gt; t.getEnrolledOffers().isEmpty()</code></li>
   *     </ul>
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
