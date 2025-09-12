package com.example.tutorial.process._interview_algorithms;

import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.datamodel.campaign.CampaignStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

import static com.example.tutorial.common.utils.SampleDataSupplier.CAMPAIGNS;

@Component
public class StringCharactersCount implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(StringCharactersCount.class, args);
  }

  @Override
  public void run(String... args) {
    System.out.println(String.format("Campaigns size: %d", CAMPAIGNS.get().size()));

    String alphabets = "abacdac";
    Map<Character, Long> charactersCount =
        // first convert string to IntStream
        alphabets.chars()
            // then convert IntStream to Stream<Character>
            .mapToObj(
                c -> (char) c
            )

            // next, group by character and count occurrences
            .collect(
                Collectors.groupingBy(
                    c -> c,
                    Collectors.counting()
                )
            );
    System.out.println(String.format("charactersCount size: %d", charactersCount.size()));
    charactersCount.forEach((character, count) -> {
      System.out.println(String.format("Character: %s, Count: %d", character, count));
    });
  }

}
