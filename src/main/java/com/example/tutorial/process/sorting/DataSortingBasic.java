package com.example.tutorial.process.sorting;

import com.example.tutorial.common.datamodel.offer.Offer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.tutorial.common.utils.SampleDataSupplier.OFFERS;

@Component
public class DataSortingBasic implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataSortingBasic.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    List<Offer> offersSortedByRedemptionAsc =
        OFFERS.get().stream()
            .sorted(
                (o1, o2) -> o1.getMaxRedemptions().compareTo(o2.getMaxRedemptions())
            )
            .toList();

    System.out.println("Offers sorted by maxRedemptions in ascending order:");
    offersSortedByRedemptionAsc.forEach(System.out::println);

    List<Offer> offersSortedByRedemptionDesc =
        OFFERS.get().stream()
            .sorted(
                (o1, o2) -> o2.getMaxRedemptions().compareTo(o1.getMaxRedemptions())
            )
            .toList();
    System.out.println("Offers sorted by maxRedemptions in descending order:");
    offersSortedByRedemptionDesc.forEach(System.out::println);

    List<Offer> top5OffersSortedByRedemptionDesc =
        OFFERS.get().stream()
            .sorted(
                (o1, o2) -> o2.getMaxRedemptions().compareTo(o1.getMaxRedemptions())
            )
            .limit(5)
            .toList();
    System.out.println("Offers sorted by maxRedemptions in descending order (top 5):");
    top5OffersSortedByRedemptionDesc.forEach(System.out::println);
  }
}
