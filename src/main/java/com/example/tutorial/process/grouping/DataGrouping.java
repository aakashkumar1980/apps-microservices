package com.example.tutorial.process.grouping;

import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.datamodel.campaign.CampaignStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.tutorial.common.utils.SampleDataSupplier.CAMPAIGNS;

@Component
public class DataGrouping implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataGrouping.class, args);
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
   *         // c -> {return c.getStatus();} | c -> c.getStatus() | Campaign::getStatus
   *         Collectors.counting() // collector to count the number of elements in each group
   *       )
   *     );
   * </pre>
   */
  @Override
  public void run(String... args) {
    System.out.println(String.format("Campaigns size: %d", CAMPAIGNS.get().size()));

    /** Group Campaigns by Status and count */
    Map<CampaignStatus, Long> campaignByStatus =
        CAMPAIGNS.get().stream()
            .collect(
                Collectors.groupingBy(
                    Campaign::getStatus,
                    Collectors.counting()
                )
            );
    System.out.println(String.format("Campaigns by Status size: %d", campaignByStatus.size()));
    campaignByStatus.forEach((status, count) -> {
      System.out.println(String.format("Status: %s, Count: %d", status, count));
    });

    /** Count characters in a string */
    String alphabets = "abacdac";
    Map<Character, Long> charactersCount =
        // first convert string to IntStream, then map each char to Character object
        alphabets.chars()
            .mapToObj(
                c -> (char) c
            )
            // next, group by character and count occurrences
            .collect(
                Collectors.groupingBy(
                    c -> c,
                    Collectors.counting()
                )
            );
    System.out.println(String.format("charactersCount size: %d", charactersCount.size()));
    charactersCount.forEach((character, count) -> {
      System.out.println(String.format("Character: %s, Count: %d", character, count));
    });
  }

}
