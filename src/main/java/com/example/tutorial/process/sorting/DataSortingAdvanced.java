package com.example.tutorial.process.sorting;

import com.example.tutorial.common.datamodel.offer.Offer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

import static com.example.tutorial.common.utils.SampleDataSupplier.OFFERS;

@Component
public class DataSortingAdvanced implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataSortingAdvanced.class, args);
  }

  /**
   * Demonstrates advanced sorting techniques using Java Streams.
   *
   * <p><b>Syntax:</b></p>
   * <pre>{@code
   * List<T> sortedList = list<T>.stream()
   *     .sorted(
   *         Comparator
   *             .comparing(T::getField1, Comparator.reverseOrder())
   *             .thenComparing(T::getField2, Comparator.reverseOrder())
   *     )
   *     .limit(n)
   *     .toList();
   * }</pre>
   *
   * <p><b>Explanation:</b></p>
   * <ul>
   *   <li><code>list.stream()</code>: Creates a stream from the list.</li>
   *   <li><code>Comparator.comparing(T::getField1, Comparator.reverseOrder())</code>: Sorts the elements based on <code>field1</code> in descending order.</li>
   *   <li><code>.thenComparing(T::getField2, Comparator.reverseOrder())</code>: For elements with equal <code>field1</code>, sorts them based on <code>field2</code> in descending order.</li>
   *   <li><code>.limit(n)</code>: Limits the result to the top <code>n</code> elements after sorting.</li>
   *   <li><code>.toList()</code>: Collects the sorted elements into a new list.</li>
   * </ul>
   */
  @Override
  public void run(String... args) throws Exception {
    List<Offer> top5OffersByRedemption =
        OFFERS.get().stream()
            .sorted(
                Comparator
                    .comparing(
                        Offer::getMaxRedemptions, Comparator.reverseOrder()
                    )
            )
            .limit(5)
            .toList();
    System.out.println("Top 5 Offers sorted by maxRedemptions in descending order:");
    top5OffersByRedemption.forEach(System.out::println);

    List<Offer> top5OffersByRedemptionAndDiscountAmount =
        OFFERS.get().stream()
            .sorted(
                Comparator
                    .comparing(
                        Offer::getMaxRedemptions, Comparator.reverseOrder()
                    )
                    .thenComparing(
                        Offer::getDiscountAmount, Comparator.reverseOrder()
                    )
            )
            .limit(5)
            .toList();
    System.out.println("Top 5 Offers sorted by maxRedemptions and discountAmount in descending order:");
    top5OffersByRedemptionAndDiscountAmount.forEach(System.out::println);

  }
}
