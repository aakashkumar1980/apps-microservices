package com.example.tutorial.process.aggregations;

import com.example.tutorial.common.datamodel.redemption.Redemption;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.DoubleSummaryStatistics;

import static com.example.tutorial.common.utils.SampleDataSupplier.REDEMPTIONS;

@Component
public class SimpleAggregations implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(SimpleAggregations.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println(String.format("Redemptions size: %d", REDEMPTIONS.get().size()));

    /** Sum of a field in the object
     * .mapToDouble.sum() is preferred as it avoids boxing/unboxing overhead.
     * **/
    double totalRedemptionAmount = REDEMPTIONS.get().stream()
        .mapToDouble(r -> r.getAmount().doubleValue())
        .sum();
    System.out.println(String.format("Total Redemption Amount: %.2f", totalRedemptionAmount));

    totalRedemptionAmount = REDEMPTIONS.get().stream()
        .map(r -> r.getAmount().doubleValue())
        .reduce(0.0, Double::sum);
    System.out.println(String.format("Total Redemption Amount: %.2f", totalRedemptionAmount));


    /** Summary statistics of a field in the object **/
    DoubleSummaryStatistics stats = REDEMPTIONS.get().stream()
        .mapToDouble(r -> r.getAmount().doubleValue())
        .summaryStatistics();
    System.out.println(String.format("Redemption Amount - Count: %d, Sum: %.2f, Min: %.2f, Average: %.2f, Max: %.2f",
        stats.getCount(), stats.getSum(), stats.getMin(), stats.getAverage(), stats.getMax()));
  }
}
