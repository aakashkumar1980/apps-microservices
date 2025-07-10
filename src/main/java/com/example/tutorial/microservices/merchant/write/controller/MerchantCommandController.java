package com.example.tutorial.microservices.merchant.write.controller;

import com.example.tutorial.microservices.merchant.write.service.MerchantCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/merchant")
public class MerchantCommandController {

    @Autowired
    private MerchantCommandService merchantCommandService;

}
