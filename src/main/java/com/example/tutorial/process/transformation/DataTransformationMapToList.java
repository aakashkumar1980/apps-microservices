package com.example.tutorial.process.transformation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.tutorial.common.utils.SampleDataSupplier.CAMPAIGNS_DTO;

@Component
public class DataTransformationMapToList implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataTransformationMapToList.class, args);
  }

  @Override
  public void run(String... args) {
    System.out.println(String.format("Campaigns size: %d", CAMPAIGNS_DTO.get().size()));

    Map<String, BigDecimal> campaignBudgets = Map.of(
        CAMPAIGNS_DTO.get().get(0).getId(), CAMPAIGNS_DTO.get().get(0).getData().getBudget(),
        CAMPAIGNS_DTO.get().get(1).getId(), CAMPAIGNS_DTO.get().get(1).getData().getBudget(),
        CAMPAIGNS_DTO.get().get(2).getId(), CAMPAIGNS_DTO.get().get(2).getData().getBudget()
    );

    List<String> campaignBudgetSummaries =
        campaignBudgets.entrySet().stream()
            .map(
                entry -> String.format("CampaignId: %s, Budget: %s", entry.getKey(), entry.getValue())
            )
            .collect(Collectors.toList());

    System.out.println(String.format("campaignBudgetSummaries size: %d", campaignBudgetSummaries.size()));
    campaignBudgetSummaries.forEach(System.out::println);
  }

}
