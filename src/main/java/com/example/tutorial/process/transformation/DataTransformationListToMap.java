package com.example.tutorial.process.transformation;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.campaign.Campaign;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.tutorial.common.utils.SampleDataSupplier.CAMPAIGNS_DTO;

@Component
public class DataTransformationListToMap implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataTransformationListToMap.class, args);
  }

  /**
   * <p>
   * This is basically a simple transformation of a List to a Map.
   * </p>
   *
   * <b>Syntax:</b>
   * <pre>
   * {@code
   * Map<K, V> mapByKey = list<T>.stream()
   *    .collect(Collectors.toMap(
   *        keyExtractorFunction,
   *        valueExtractorFunction
   *    ));
   *
   * }</pre>
   *
   * <p>
   * <b>Explanation:</b>
   * <ul>
   *   <li>{@code list.stream()}: Creates a stream from the list.</li>
   *   <li>{@code Collectors.toMap()}: Transforms the stream to a Map.</li>
   *   <ul>
   *      <li>{@code keyExtractorFunction}: A function that extracts the key for the map e.g., <br/>
   *        {@code t -> t.getId()} => K
   *      </li>
   *      <li>{@code valueExtractorFunction}: A function that extracts the value for the map e.g., <br/>
   *          {@code t -> t.getData().getBudget()} => V
   *      </li>
   *   </ul>
   * </ul>
   * </p>
   */
  @Override
  public void run(String... args) {
    System.out.println(String.format("Campaigns size: %d", CAMPAIGNS_DTO.get().size()));

    Map<String, BigDecimal> campaignBudgets =
        CAMPAIGNS_DTO.get().stream()
            .collect(
                Collectors.toMap(
                    BaseDto<Campaign>::getId,
                    c -> c.getData().getBudget()
                )
            );
    System.out.println(String.format("campaignBudgets size: %d", campaignBudgets.size()));
    campaignBudgets.forEach((k, v) -> System.out.println(String.format("CampaignId: %s, Budget: %s", k, v)));
  }

}
