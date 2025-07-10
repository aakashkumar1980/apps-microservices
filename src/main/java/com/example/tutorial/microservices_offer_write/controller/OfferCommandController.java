package com.example.tutorial.microservices_offer_write.controller;

import com.example.tutorial.microservices_offer_write.service.OfferCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/offers")
public class OfferCommandController {

    @Autowired
    private OfferCommandService offerCommandService;

}
