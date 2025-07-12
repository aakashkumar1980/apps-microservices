package com.example.tutorial.microservices.offer.read.service;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.offer.Offer;
import com.example.tutorial.microservices.offer.read.repository.OfferQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferQueryService {

  private static final Logger log = LoggerFactory.getLogger(OfferQueryService.class);

  @Autowired
  private OfferQueryRepository offerQueryRepository;

  /**
   * Retrieves all offers from the repository.
   *
   * @return a list of BaseDto containing Offer objects.
   */
  public List<BaseDto<Offer>> getAllOffers() {
    log.info("Fetching all offers from the repository");
    return offerQueryRepository.findAll();
  }
}
