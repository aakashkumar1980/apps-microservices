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
 *    .collect(collector);
 * }
 * </pre>
 *
 * <p>
 * <b>Explanation:</b>
 * <ul>
 *   <li>{@code list.stream()}: Creates a stream from the list.</li>
 *   <li>{@code [collect(collector)]} <br/>
 *       {@code Collectors.groupingBy(keyClassifierFunction, mapperFunctionCollector)}: Groups elements by the key and use mapperFunction to run in each group.
 *       <ul>
 *          <li>{@code keyClassifierFunction}: A function that identifies the key for grouping the data e.g., <br/>
 *              {@code T::getField()} => K
 *          </li>
 *          <li>{@code mapperFunctionCollector}: A downstream collector that performs a data transformation operation on the
 *              values associated with a given key, Transforms the stream to a Map from T -> Map(K, V)
 *              <pre>{@code
 *                Collectors.mapping(
 *                  mapperFunction,
 *                  Collectors.toList/Set/joining/counting/summingInt/averagingDouble()
 *                )}</pre>
 *                {@code mapperFunction} It is a function that transforms an element of type T to another type V e.g., <br/>
 *                {@code T::getField()} => V
 *          </li>
 *       </ul>
 *   </li>
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
