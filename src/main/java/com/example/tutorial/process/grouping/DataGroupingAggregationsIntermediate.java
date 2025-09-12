package com.example.tutorial.process.grouping;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.customer.Customer;
import com.example.tutorial.common.datamodel.redemption.Redemption;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

import static com.example.tutorial.common.utils.SampleDataSupplier.*;

@Component
public class DataGroupingAggregationsIntermediate implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataGroupingAggregationsIntermediate.class, args);
  }
  /**
   * <p>
   * This is basically a simple grouping the dataset with a key (an attribute of the object)
   * and then applying an aggregation function on the grouped data.
   * </p>
   *
   * <b>Syntax:</b>
   * <pre>
   * {@code
   * list.stream()
   *    .collect(Collectors.groupingBy(
   *        keyExtractorFunction,
   *        aggregateFunction
   *    ));
   *
   * }</pre>
   *
   * <p>
   * <b>Explanation:</b>
   * <ul>
   *   <li>{@code list.stream()}: Creates a stream from the list.</li>
   *   <li>{@code Collectors.groupingBy()}: Groups elements by the key and use aggregateFunction to run in each group.</li>
   *   <ul>
   *      <li>{@code keyExtractorFunction}: A function that extracts the key for grouping e.g., <br/>
   *        {@code Redemption::getCustomerId}
   *      </li>
   *      <li>{@code aggregateFunction}: A downstream collector that performs a reduction operation on the values associated with a given key e.g., <br/>
   *          {@code Collectors.summingDouble/Int/Long(r -> r.getAmount().doubleValue())} <br/>
   *          {@code Collectors.averagingDouble/Int/Long(r -> r.getAmount().doubleValue())} <br/>
   *          {@code Collectors.maxBy(Comparator.comparingDouble/Int/Long(r -> r.getAmount().doubleValue()))} <br/>
   *          {@code Collectors.minBy(Comparator.comparingDouble/Int/Long(r -> r.getAmount().doubleValue()))}
   *      </li>
   *   </ul>
   * </ul>
   * </p>
   */
  @Override
  public void run(String... args) {
    System.out.println(String.format("Redemptions size: %d", REDEMPTIONS.get().size()));

    /** Group by customerId and sum the amount spent */
    Map<String, Double> totalDiscountAmountByCustomerId =
        REDEMPTIONS.get().stream()
            .collect(
                Collectors.groupingBy(
                    /** keyMapper **/
                    Redemption::getCustomerId,

                    /** aggregationFunction **/
                    Collectors.summingDouble(r -> r.getAmount().doubleValue())
                )
            );
    System.out.println(String.format("totalDiscountAmountByCustomerId size: %d", totalDiscountAmountByCustomerId.size()));
    totalDiscountAmountByCustomerId.forEach((customerId, totalAmount) ->
        System.out.println(String.format("Customer ID: %s, Total Discount Amount: %.2f", customerId, totalAmount))
    );

    /** Group by Customer object and sum the amount spent */
    Map<Customer, Double> totalDiscountAmountByCustomer =
        REDEMPTIONS.get().stream()
            .collect(
                Collectors.groupingBy(
                    /** keyMapper **/
                    r -> {
                      // convert customerId to Customer object
                      String customerId = r.getCustomerId();
                      return CUSTOMERS_DTO.get().stream()
                          // filter the customer with the given customerId
                          .filter(c -> c.getId().equals(customerId))
                          .findFirst()
                          // get the Customer object from BaseDto<Customer>
                          .map(BaseDto<Customer>::getData)
                          .get();
                    },

                    /** aggregationFunction **/
                    Collectors.summingDouble(r -> r.getAmount().doubleValue())
                )
            );
    System.out.println(String.format("totalDiscountAmountByCustomer size: %d", totalDiscountAmountByCustomer.size()));
    totalDiscountAmountByCustomer.forEach((customer, totalAmount) ->
        System.out.println(String.format("Customer: %s, Total Discount Amount: %.2f", customer.getName(), totalAmount))
    );

  }

}
