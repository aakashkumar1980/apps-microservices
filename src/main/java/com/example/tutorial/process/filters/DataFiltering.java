package com.example.tutorial.process.filters;

import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.datamodel.campaign.CampaignStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.tutorial.common.utils.SampleDataSupplier.*;

@Component
public class DataFiltering implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataFiltering.class, args);
  }

  /**
   * Filters a list of objects based on a specified condition using Java Streams.
   *
   * <p><b>Syntax:</b></p>
   * <pre>{@code
   * List<T> filteredList = list<T>.stream()
   *     .filter(predicate)
   *     .toList();
   * }</pre>
   *
   * <p><b>Explanation:</b></p>
   * <ul>
   *   <li><code>list.stream()</code>: Creates a stream from the list.</li>
   *   <li><code>.filter(predicate)</code>: Filters elements using a predicate <code>p</code> (a function returning boolean). e.g.,
   *     <ul>
   *       <li><code>t -> t.getField().equals(valueToCompare)</code></li>
   *       <li><code>T::isBooleanField</code> (if <code>isBooleanField()</code> e.g. is defined in object)</li>
   *     </ul>
   *   </li>
   *   <li><code>.toList()</code>: Collects the filtered elements into a new list.</li>
   * </ul>
   */
  @Override
  public void run(String... args) {
    System.out.println(String.format("Campaigns size: %d", CAMPAIGNS.get().size()));

    List<Campaign> filteredCampaigns =
        CAMPAIGNS.get().stream()
            .filter(c -> c.getStatus() == CampaignStatus.ACTIVE)
            .toList();
    System.out.println(String.format("filteredCampaigns size: %d", filteredCampaigns.size()));
    filteredCampaigns.forEach(System.out::println);
  }

}
