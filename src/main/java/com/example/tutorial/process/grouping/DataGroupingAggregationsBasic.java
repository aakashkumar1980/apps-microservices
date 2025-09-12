package com.example.tutorial.process.grouping;

import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.datamodel.campaign.CampaignStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

import static com.example.tutorial.common.utils.SampleDataSupplier.CAMPAIGNS;

@Component
public class DataGroupingAggregationsBasic implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataGroupingAggregationsBasic.class, args);
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
   * Map<K, Long> aggregateByKey = list<T>.stream()
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
   *        {@code t -> t.getStatus()} => K
   *      </li>
   *      <li>{@code aggregateFunction}: A downstream collector that performs a reduction operation on the values associated with a given key e.g., <br/>
   *        {@code Collectors.counting()}
   *      </li>
   *   </ul>
   * </ul>
   * </p>
   */
  @Override
  public void run(String... args) {
    System.out.println(String.format("Campaigns size: %d", CAMPAIGNS.get().size()));

    /** Group Campaigns by Status and count */
    Map<CampaignStatus, Long> countByCampaignStatus =
        CAMPAIGNS.get().stream()
            .collect(
                Collectors.groupingBy(
                    Campaign::getStatus,
                    Collectors.counting()
                )
            );
    System.out.println(String.format("countByCampaignStatus size: %d", countByCampaignStatus.size()));
    countByCampaignStatus.forEach((status, count) -> {
      System.out.println(String.format("Status: %s, Count: %d", status, count));
    });
  }

}
