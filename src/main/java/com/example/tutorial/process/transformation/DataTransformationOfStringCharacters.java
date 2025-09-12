package com.example.tutorial.process.transformation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataTransformationOfStringCharacters implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataTransformationOfStringCharacters.class, args);
  }

  public void run(String... args) {
    String alphabets = "abacdac";
    List<Character> characters =
        // convert string to int stream of characters
        alphabets.chars()
            // map each int to Character object
            .mapToObj(
                c -> (char) c
            ).toList();
    System.out.println(String.format("characters size: %d", characters.size()));
    characters.forEach(System.out::println);
  }
}
