package com.aadityadesigners.tutorials.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/public/api")
public class WelcomeController {
    
    private static final Logger LOGGER = LogManager.getLogger(WelcomeController.class);

    @GetMapping("/iamlive")
    public ResponseEntity<Boolean> iamlive() {
        LOGGER.info("iamlive");
        return ResponseEntity.ok(Boolean.TRUE);
    }
    
}