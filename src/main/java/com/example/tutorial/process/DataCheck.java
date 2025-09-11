package com.example.tutorial.process;

import com.example.tutorial.common.datamodel.campaign.CampaignStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import static com.example.tutorial.common.utils.SampleDataSupplier.CAMPAIGNS;

@Component
public class DataCheck implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataCheck.class, args);
  }

  /**
   * Data Check Example
   * <p>
   *  In this example, we will demonstrate how to use Java Streams to perform data checks on a list of campaigns.
   *  - We will check if at least one campaign is in DRAFT status.
   *  - We will check if all campaigns are in ACTIVE status.
   *  - We will check if no campaigns are in EXPIRED status.
   * </p>
   * <b>Syntax:</b>
   * <pre>
   *   list.stream()
   *        .anyMatch(c -> c.getStatus() == CampaignStatus.DRAFT); // at least one match
   *
   *     list.stream()
   *        .allMatch(c -> c.getStatus() == CampaignStatus.ACTIVE); // all match
   *
   *     list.stream()
   *        .noneMatch(c -> c.getStatus() == CampaignStatus.EXPIRED); // none match
   * </pre>
   */
  @Override
  public void run(String... args) {
    System.out.println(String.format("Campaigns size: %d", CAMPAIGNS.get().size()));

    Boolean isAtleastOneCampaignInDraftStatus =
        CAMPAIGNS.get().stream()
            .anyMatch(c -> {
                  return c.getStatus() == CampaignStatus.DRAFT;
                }
            );
    System.out.println(String.format("isAtleastOneCampaignInDraftStatus : %b", isAtleastOneCampaignInDraftStatus));

    Boolean areAllCampaignsInActiveStatus =
        CAMPAIGNS.get().stream()
            .allMatch(c -> {
                  return c.getStatus() == CampaignStatus.ACTIVE;
                }

            );
    System.out.println(String.format("areAllCampaignsInActiveStatus : %b", areAllCampaignsInActiveStatus));

    Boolean areNoCampaignsInExpiredStatus =
        CAMPAIGNS.get().stream()
            .noneMatch(c -> {
                  return c.getStatus() == CampaignStatus.EXPIRED;
                }

            );
    System.out.println(String.format("areNoCampaignsInExpiredStatus : %b", areNoCampaignsInExpiredStatus));
  }

}
