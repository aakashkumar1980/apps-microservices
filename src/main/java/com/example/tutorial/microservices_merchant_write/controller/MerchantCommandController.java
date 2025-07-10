package com.example.tutorial.microservices_merchant_write.controller;

import com.example.tutorial.microservices_merchant_write.service.MerchantCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/merchant")
public class MerchantCommandController {

    @Autowired
    private MerchantCommandService merchantCommandService;

}
