package com.example.tutorial.process.filters;

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
   * Checks if elements in a list satisfy certain conditions using Java Streams.
   *
   * <p><b>Syntax:</b></p>
   * <pre>{@code
   * boolean any = list<T>.stream().anyMatch(predicate);
   * boolean all = list<T>.stream().allMatch(predicate);
   * boolean none = list<T>.stream().noneMatch(predicate);
   * }</pre>
   *
   * <p><b>Explanation:</b></p>
   * <ul>
   *   <li><code>list.stream()</code>: Creates a stream from the list.</li>
   *   <li><code>.anyMatch(predicate)</code>: Returns <code>true</code> if any element matches the predicate <code>p</code>.</li>
   *   <li><code>.allMatch(predicate)</code>: Returns <code>true</code> if all elements match the predicate <code>p</code>.</li>
   *   <li><code>.noneMatch(predicate)</code>: Returns <code>true</code> if no elements match the predicate <code>p</code>.</li>
   *   <li>Examples of predicates:
   *     <ul>
   *       <li><code>t -&gt; t.getStatus() == CampaignStatus.DRAFT</code></li>
   *     </ul>
   *   </li>
   * </ul>
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
