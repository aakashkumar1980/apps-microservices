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

/**
 * <p>
 * This is basically a simple grouping the dataset with a key (an attribute of the object)
 * and then applying a mapping function on the grouped data.
 * </p>
 *
 * <b>Syntax:</b>
 * <pre>
 * {@code
 * Map<K, List/Set/String/Long/Integer/Double<V>> groupedByKey = list<T>.stream()
 *    .collect(Collectors.groupingBy(
 *        keyExtractorFunction,
 *
 *        Collectors.mapping(
 *          mapperFunction,
 *          Collectors.toList/Set/joining/counting/summingInt/averagingDouble()
 *        )
 *    ));
 * }
 * </pre>
 *
 * <p>
 * <b>Explanation:</b>
 * <ul>
 *   <li>{@code list.stream()}: Creates a stream from the list.</li>
 *   <li>{@code Collectors.groupingBy()}: Groups elements by the key and use mapperFunction to run in each group.</li>
 *   <ul>
 *      <li>{@code keyExtractorFunction}: Function to extract the key for grouping, e.g., <br/>
 *        {@code t -> t.getCampaignId()} => K
 *      </li>
 *      <li>{@code mapperFunction}: For transforming the grouped elements before collecting them. e.g., <br/>
 *        {@code t -> t.getId()} => V
 *      </li>
 *   </ul>
 * </ul>
 * </p>
 */
@Component
public class DataGroupingDataTransformation implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataGroupingDataTransformation.class, args);
  }

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
