package com.example.tutorial.process.grouping;

import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.datamodel.offer.Offer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.tutorial.common.utils.SampleDataSupplier.*;

@Component
public class DataGroupingDataTransformation implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataGroupingDataTransformation.class, args);
  }

  /**
   * Data grouping and transformation using Java Streams
   * <p>
   * In this example, we will demonstrate how to group data using Java Streams and
   * then transform the grouped data using a mapping function.
   * </p>
   * <b>Syntax:</b>
   * <pre>
   *   list.stream()
   *     .collect(
   *       Collectors.groupingBy(
   *         keyMapper, // function to extract the key for grouping e.g.
   *         // o -> {return o.getData().getCampaignId();} | o -> o.getData().getCampaignId()
   *
   *         mapperFunction // function to extract the value for mapping e.g.
   *         // Collectors.mapping(
   *         //   valueMapper, // function to extract the value for mapping e.g.  o -> o.getId()
   *         //
   *         //   Collectors.toList() // collector to accumulate the mapped values
   *         // )
   *       )
   *     );
   * </pre>
   */
  @Override
  public void run(String... args) {
    System.out.println(String.format("Offers size: %d", OFFERS_DTO.get().size()));

    /** Grouping Offers by CampaignId and mapping to Offer Ids */
    Map<String, List<String>> offerIdListByCampaignId =
        OFFERS_DTO.get().stream()
            .collect(
                Collectors.groupingBy(
                    o -> o.getData().getCampaignId(),

                    Collectors.mapping(
                        o -> o.getId(),
                        Collectors.toList()
                    )
                )
            );

    System.out.println(String.format("offerListByCampaign size: %d", offerIdListByCampaignId.size()));
    offerIdListByCampaignId.forEach((k, v) ->
        System.out.println(String.format("CampaignId: %s, OfferIds: %s", k, v)));

    /** Grouping Offers by Campaign and mapping to Offer objects */
    Map<Campaign, List<Offer>> offerListByCampaign =
        OFFERS_DTO.get().stream()
            .collect(
                Collectors.groupingBy(
                    o -> {
                      String campaignId = o.getData().getCampaignId();
                      return CAMPAIGNS_DTO.get().stream()
                          .filter(c -> c.getId().equals(campaignId))
                          .findFirst()
                          .get().getData();
                    },

                    Collectors.mapping(
                        o -> o.getData(),
                        Collectors.toList()
                    )
                )
            );
    System.out.println(String.format("offerListByCampaign size: %d", offerListByCampaign.size()));
    offerListByCampaign.forEach((k, v) ->
        System.out.println(String.format("Campaign: %s, Offers: %s", k, v)));
  }

}
