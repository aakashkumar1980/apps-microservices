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
public class DataGroupingAggregations implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataGroupingAggregations.class, args);
  }

  /**
   * Data Grouping using Java Streams
   * <p>
   * In this example, we will demonstrate how to group data using Java Streams.
   * We will use a list of Campaign objects and group them by their status and
   * then count the number of campaigns in each status.
   * </p>
   * <b>Syntax:</b>
   * <pre>
   *   list.stream()
   *     .collect(
   *       Collectors.groupingBy(
   *         keyMapper, // Function to extract the key for grouping e.g.
   *         // r -> {return r.getCustomerId();} | r -> r.getCustomerId() | Redemption::getCustomerId
   *
   *         aggregationFunction // collector to count the number of elements in each group e.g.
   *         // Collectors.summingDouble(r -> r.getAmount().doubleValue()) -> to sum the amount of each group
   *         // Collectors.averagingDouble(r -> r.getAmount().doubleValue()) -> to average the amount of each group
   *         // Collectors.maxBy(Comparator.comparing(r -> r.getAmount().doubleValue())) -> to get the max amount of each group
   *         // Collectors.minBy(Comparator.comparing(r -> r.getAmount().doubleValue())) -> to get the min amount of each group
   *       )
   *     );
   * </pre>
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
