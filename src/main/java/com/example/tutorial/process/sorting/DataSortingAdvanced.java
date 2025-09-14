package com.example.tutorial.process.sorting;

import com.example.tutorial.common.datamodel.offer.Offer;
import com.example.tutorial.common.datamodel.redemption.Redemption;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

import static com.example.tutorial.common.utils.SampleDataSupplier.OFFERS;
import static com.example.tutorial.common.utils.SampleDataSupplier.REDEMPTIONS;

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
   *     .sorted(comparator)
   *     .limit(n)
   *     .toList();
   * }</pre>
   *
   * <p><b>Explanation:</b></p>
   * <ul>
   *   <li><code>list.stream()</code>: Creates a stream from the list.</li>
   *   <li><code>[sorted(comparator)]</sorted(comparator)></code> <br/>
   *     <code>Comparator.comparing(keyExtractorFunction, keyComparator)</code><br/>
   *     <code>Comparator.comparing(keyExtractorFunction).reversed()</code> (optional)<br/>
   *     <code>Comparator.thenComparing(keyExtractorFunction, keyComparator)</code> (optional)<br/>
   *     Sorts the elements of the stream based on the provided comparator.
   *     <ul>
   *       <li><code>keyExtractorFunction</code>: A function that extracts the field to be compared, e.g., <br/>
   *          <code>T::getField1</code>.
   *       </li>
   *       <li><code>keyComparator</code>: A comparator that defines the order of sorting, e.g., <br/>
   *          <code>Comparator.reverseOrder()</code>
   *       </li>
   *   </li>
   * </ul>
   * <li><code>limit(n)</code>: Limits the result to the top <code>n</code> elements after sorting.</li>
   * <li><code>toList()</code>: Collects the sorted elements into a new list.</li>
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

    List<Redemption> latest5Redemptions =
        REDEMPTIONS.get().stream()
            .sorted(
                Comparator.comparing(
                    Redemption::getRedemptionTime
                ).reversed()
            )
            .limit(5)
            .toList();
    System.out.println("Latest 5 Redemptions sorted by redemptionTime in descending order:");
    latest5Redemptions.forEach(System.out::println);


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
