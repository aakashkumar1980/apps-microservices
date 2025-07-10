package com.example.tutorial.microservices.offer.write.controller;

import com.example.tutorial.microservices.offer.write.service.OfferCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/offers")
public class OfferCommandController {

    @Autowired
    private OfferCommandService offerCommandService;

}
