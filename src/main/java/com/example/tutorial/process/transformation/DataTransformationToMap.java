package com.example.tutorial.process.transformation;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.campaign.Campaign;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.tutorial.common.utils.SampleDataSupplier.CAMPAIGNS;
import static com.example.tutorial.common.utils.SampleDataSupplier.CAMPAIGNS_DTO;

@Component
public class DataTransformationToMap implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataTransformationToMap.class, args);
  }

  @Override
  public void run(String... args) {
    System.out.println(String.format("Campaigns size: %d", CAMPAIGNS_DTO.get().size()));

    /** Map Campaign to Campaign Name */
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
