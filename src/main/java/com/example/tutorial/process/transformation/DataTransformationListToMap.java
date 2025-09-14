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
   * Map<K, V> mapByKeyValue = list<T>.stream()
   *    .collect(collector);
   * }</pre>
   *
   * <p>
   * <b>Explanation:</b>
   * <ul>
   *   <li> {@code list.stream()}: Creates a stream from the list.</li>
   *   <li> {@code [collect(collector)]}<br/>
   *        {@code Collectors.toMap(keyMapperFunction, valueMapperFunction)}: Collects the results into a new map using the specified collector.
   *        Transforms the stream to a Map from T -> Map(K, V)
   *        <ul>
   *          <li>{@code keyMapperFunction}: A function that extracts the key for the map e.g., <br/>
   *            {@code T::getField1} => K
   *          </li>
   *          <li>{@code valueMapperFunction}: A function that extracts the value for the map e.g., <br/>
   *            {@code T::getField2} => V
   *          </li>
   *        </ul>
   *   </li>
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
