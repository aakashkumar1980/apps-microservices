package com.example.tutorial.process._interview_algorithms;

import java.util.*;
import java.util.stream.*;
import java.time.*;

public class TODO {
  static record Campaign(String id, String name, boolean isActive, double budget,
                         LocalDateTime startDate, LocalDateTime endDate,
                         List<String> offerIds) {};

  static List<Campaign> campaignList;
  static {
    campaignList =
        List.of(
            new Campaign("campaign::1", "New Year Bonaza", true, 1000.0,
                LocalDateTime.parse("2025-09-14T00:00:00"), LocalDateTime.parse("2025-11-14T00:00:00"),
                List.of("offer::1", "offer::2")),
            new Campaign("campaign::2", "Good Friday", false, 1200.0,
                LocalDateTime.parse("2025-09-14T00:00:00"), LocalDateTime.parse("2025-11-14T00:00:00"),
                List.of("offer::1", "offer::3")),
            new Campaign("campaign::3", "Independence Day", true, 1200.0,
                LocalDateTime.parse("2025-09-13T00:00:00"), LocalDateTime.parse("2025-09-14T00:00:00"),
                List.of("offer::1", "offer::4", "offer::5"))
        );

  }

  /** MAIN **/
  public static void main(String[] args) {
    // Return a List<String> of campaign names where isActive==true, sorted A to Z.
    List<String> activeCampaignNames =
        campaignList.stream()
            .filter(Campaign::isActive)
            .sorted(Comparator.comparing(Campaign::name))
            .map(Campaign::name)
            .toList();
    activeCampaignNames.forEach(v ->
        System.out.println(String.format("name: %s", v)));

    // Compute the total double budget of active campaigns
    System.out.println();
    Double totalBudgetOfActiveCampaigns =
        campaignList.stream()
            .filter(Campaign::isActive)
            .mapToDouble(Campaign::budget)
            .sum();
    System.out.println(
        String.format("totalBudgetOfActiveCampaigns: %f", totalBudgetOfActiveCampaigns));

    // Return boolean if there exists any campaign with isActive==false
    System.out.println();
    boolean isAnyCampaignInactive =
        campaignList.stream()
            .anyMatch(c -> c.isActive()==false);
    System.out.println("isAnyCampaignInactive: "+isAnyCampaignInactive);

    // Return a Set<String> of all unique offerIds across active campaigns.
    System.out.println();
    Set<String> offersOfActiveCampaigns =
        campaignList.stream()
            .filter(Campaign::isActive)
            .flatMap(c -> c.offerIds().stream())
            .collect(Collectors.toSet());
    offersOfActiveCampaigns.forEach(v ->
        System.out.println(String.format("offer: %s", v)));

    // Return long count of active campaigns.
    System.out.println();
    Long activeCampaignCount =
        campaignList.stream()
            .filter(Campaign::isActive)
            .count();
    System.out.println("activeCampaignCount: "+activeCampaignCount);

    // Return the campaign with the highest budget (any tie-break by name ascending).
    System.out.println();
    Optional<Campaign> highestBudgetCampaign =
        campaignList.stream()
            .sorted(
                Comparator.comparing(Campaign::budget, Comparator.reverseOrder())
                    .thenComparing(Campaign::name)
            )
            .findFirst();
    System.out.println("highestBudgetCampaign: "+highestBudgetCampaign.get());

    // Group Campaigns by active/inactive
    System.out.println();
    Map<Boolean, List<Campaign>> activeInactiveCampaigns =
        campaignList.stream()
            .collect(Collectors.partitioningBy(Campaign::isActive));
    activeInactiveCampaigns.forEach((k, v) ->
        System.out.println("k: "+k+" | v: "+v));

    // Offer count per campaign
    System.out.println();
    Map<String, Integer> offerCountPerCampaignMap =
        campaignList.stream()
            .collect(
                Collectors.toMap(
                    Campaign::name,
                    v -> v.offerIds().size()
                )
            );
    offerCountPerCampaignMap.forEach((k, v) ->
        System.out.println("k:"+k+" |v:"+v));

    // Date window filter: Given LocalDateTime now, return campaigns where startDate ≤ now ≤ endDate
    List<Campaign> activeNowCampaigns =
        campaignList.stream()
            .filter(c->
                c.startDate().isBefore(LocalDateTime.now())
                && c.endDate().isAfter(LocalDateTime.now())
            ).toList();
    activeNowCampaigns.forEach((v) ->
        System.out.println("Campaign: "+v)
    );

  }
}
