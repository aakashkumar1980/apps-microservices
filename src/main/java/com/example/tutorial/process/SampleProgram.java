package com.example.tutorial.process;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SampleProgram implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(SampleProgram.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    offers.forEach(offer -> {
      System.out.println(offer.getTitle());
    });
  }
}
