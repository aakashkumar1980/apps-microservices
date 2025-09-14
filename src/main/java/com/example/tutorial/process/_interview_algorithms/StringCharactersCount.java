package com.example.tutorial.process._interview_algorithms;

import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.datamodel.campaign.CampaignStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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

    /** Count occurrences of each character in a string */
    String alphabets = "abacdac";

    /** Using traditional approach **/
    // convert to char arrays first
    char[] alphabetsArr = alphabets.toCharArray();
    // next create a map with character as the key and count as the occurances
    Map<Character, Integer> characterCountMap = new HashMap<>();
    // loop the alphabetsArr
    for(int i=0; i<alphabetsArr.length; i++) {
      Character currentCharacter = (Character) alphabetsArr[i];
      Integer existingCharCount = characterCountMap.get(currentCharacter);
      if(existingCharCount!=null) {
        // character already exists and current character is same, so update the count
        characterCountMap.put(currentCharacter, (existingCharCount+1));
      } else {
        // first entry of the character
        characterCountMap.put(currentCharacter, 1);
      }
    }
    // print the Map
    System.out.println(String.format("charactersCount size: %d", characterCountMap.size()));
    characterCountMap.forEach((k,v) ->
        System.out.println(String.format("character:%s | count:%d", k,v)));


    /** Using Java Streams **/
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
