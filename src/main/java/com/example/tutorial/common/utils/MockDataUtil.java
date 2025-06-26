package com.example.tutorial.common.utils;

import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.common.dto.campaign.CampaignStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Component
public class MockDataUtil {

  /**
   * Generates a list of mock Campaign objects for testing purposes.
   * * @return A Supplier that provides a list of Campaign objects.
   *
   * Here instead of usual function
   * List<Campaign> campaigns() {
   *   return new List.of(
   *     new Campaign(1L, "Summer Sale 2024", "Discounts on summer clothing and accessories.", "ACTIVE", "2024-06-01", "2024-06-30", 50000.0),
   *     new Campaign(2L, "Back to School", "Promotions for school supplies and backpacks.", "PLANNED", "2024-07-15", "2024-08-15", 30000.0),
   *     ...
   *   );
   * }
   *
   * Java 8 Supplier function is used to generate the list of Campaign objects.
   * The advantage is that it allows for lazy evaluation, meaning the list is only created when needed.
   * syntax:
   * Supplier<Object> supplier = () -> {
   *   // logic to create and return an object
   *   return new Object();
   * };
   */
  public Supplier<List<Campaign>> campaignSupplier = () -> {
    List<Campaign> campaigns = new ArrayList<>();

    Campaign c1 = new Campaign();
    c1.setId(1L);
    c1.setName("Summer Sale 2024");
    c1.setDescription("Discounts on summer clothing and accessories.");
    c1.setStatus(CampaignStatus.valueOf("ACTIVE"));
    c1.setStartDate(LocalDateTime.parse("2024-06-01T00:00:00"));
    c1.setEndDate(LocalDateTime.parse("2024-06-30T00:00:00"));
    c1.setBudget(50000.0);
    campaigns.add(c1);

    Campaign c2 = new Campaign();
    c2.setId(2L);
    c2.setName("Back to School");
    c2.setDescription("Promotions for school supplies and backpacks.");
    c2.setStatus(CampaignStatus.valueOf("PLANNED"));
    c2.setStartDate(LocalDateTime.parse("2024-07-15T00:00:00"));
    c2.setEndDate(LocalDateTime.parse("2024-08-15T00:00:00"));
    c2.setBudget(30000.0);
    campaigns.add(c2);

    Campaign c3 = new Campaign();
    c3.setId(3L);
    c3.setName("Black Friday Blast");
    c3.setDescription("Biggest deals of the year on electronics.");
    c3.setStatus(CampaignStatus.valueOf("PLANNED"));
    c3.setStartDate(LocalDateTime.parse("2024-11-25T00:00:00"));
    c3.setEndDate(LocalDateTime.parse("2024-11-29T00:00:00"));
    c3.setBudget(120000.0);
    campaigns.add(c3);

    Campaign c4 = new Campaign();
    c4.setId(4L);
    c4.setName("Holiday Cheer");
    c4.setDescription("Seasonal offers for the holidays.");
    c4.setStatus(CampaignStatus.valueOf("ACTIVE"));
    c4.setStartDate(LocalDateTime.parse("2024-12-01T00:00:00"));
    c4.setEndDate(LocalDateTime.parse("2024-12-31T00:00:00"));
    c4.setBudget(80000.0);
    campaigns.add(c4);

    Campaign c5 = new Campaign();
    c5.setId(5L);
    c5.setName("New Year Kickoff");
    c5.setDescription("Start the year with exclusive deals.");
    c5.setStatus(CampaignStatus.valueOf("DRAFT"));
    c5.setStartDate(LocalDateTime.parse("2025-01-01T00:00:00"));
    c5.setEndDate(LocalDateTime.parse("2025-01-10T00:00:00"));
    c5.setBudget(25000.0);
    campaigns.add(c5);

    Campaign c6 = new Campaign();
    c6.setId(6L);
    c6.setName("Spring Collection Launch");
    c6.setDescription("Introducing the new spring collection.");
    c6.setStatus(CampaignStatus.valueOf("ACTIVE"));
    c6.setStartDate(LocalDateTime.parse("2024-03-10T00:00:00"));
    c6.setEndDate(LocalDateTime.parse("2024-04-10T00:00:00"));
    c6.setBudget(40000.0);
    campaigns.add(c6);

    Campaign c7 = new Campaign();
    c7.setId(7L);
    c7.setName("Tech Expo 2024");
    c7.setDescription("Showcasing the latest in tech gadgets.");
    c7.setStatus(CampaignStatus.valueOf("COMPLETED"));
    c7.setStartDate(LocalDateTime.parse("2024-05-01T00:00:00"));
    c7.setEndDate(LocalDateTime.parse("2024-05-07T00:00:00"));
    c7.setBudget(60000.0);
    campaigns.add(c7);

    Campaign c8 = new Campaign();
    c8.setId(8L);
    c8.setName("Fitness Frenzy");
    c8.setDescription("Special offers on fitness equipment.");
    c8.setStatus(CampaignStatus.valueOf("ACTIVE"));
    c8.setStartDate(LocalDateTime.parse("2024-09-01T00:00:00"));
    c8.setEndDate(LocalDateTime.parse("2024-09-30T00:00:00"));
    c8.setBudget(35000.0);
    campaigns.add(c8);

    Campaign c9 = new Campaign();
    c9.setId(9L);
    c9.setName("Travel Bonanza");
    c9.setDescription("Discounts on travel packages and accessories.");
    c9.setStatus(CampaignStatus.valueOf("PLANNED"));
    c9.setStartDate(LocalDateTime.parse("2024-10-01T00:00:00"));
    c9.setEndDate(LocalDateTime.parse("2024-10-31T00:00:00"));
    c9.setBudget(70000.0);
    campaigns.add(c9);

    Campaign c10 = new Campaign();
    c10.setId(10L);
    c10.setName("Clearance Event");
    c10.setDescription("End of season clearance on all items.");
    c10.setStatus(CampaignStatus.valueOf("INACTIVE"));
    c10.setStartDate(LocalDateTime.parse("2024-08-01T00:00:00"));
    c10.setEndDate(LocalDateTime.parse("2024-08-15T00:00:00"));
    c10.setBudget(20000.0);
    campaigns.add(c10);

    return campaigns;
  };
}
