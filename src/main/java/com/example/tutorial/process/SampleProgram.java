package com.example.tutorial.process;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class SampleProgram implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(SampleProgram.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

  }
}
