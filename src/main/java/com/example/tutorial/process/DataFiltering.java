package com.example.tutorial.process;

import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.datamodel.campaign.CampaignStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;
import static com.example.tutorial.common.utils.SampleDataSupplier.*;

@Component
public class DataFiltering implements CommandLineRunner {
  public static void main(String[] args) {SpringApplication.run(DataFiltering.class, args);}

  /**
   * Data Filtering Example
   * <p>
   *  In this example, we will filter a list of {@link Campaign} objects to find those that are active.
   *  We will use Java Streams to perform the filtering operation.
   * <p>
   * <b>Syntax:</b>
   * <pre>
   * list.stream()
   *    .filter(p), where p is a predicate i.e. a function that returns a boolean. e.g.
   *        c -> {
   *          return c.getStatus().equals(CampaignStatus.ACTIVE);
   *        }, OR
   *        c -> c.getStatus() == CampaignStatus.ACTIVE, OR
   *        Campaign::isActive (if isActive() method is defined in Campaign class)
   *    .toList();
   * </pre>
   */
  @Override
  public void run(String... args) {
    System.out.println(String.format("Campaigns size: %d", CAMPAIGNS.get().size()));
    List<Campaign> filteredCampaigns =
        CAMPAIGNS.get().stream()
            .filter(c -> c.getStatus() == CampaignStatus.ACTIVE)
            .toList();

    System.out.println(String.format("Filtered Campaigns size: %d", filteredCampaigns.size()));
    filteredCampaigns.forEach(System.out::println);

  }

}
