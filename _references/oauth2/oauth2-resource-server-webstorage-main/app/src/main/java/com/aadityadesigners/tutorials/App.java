package com.aadityadesigners.tutorials;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.aadityadesigners.tutorials.utils.AppUtility;

@SpringBootApplication
@ComponentScan(basePackages= "com.aadityadesigners.tutorials")
public class App {
    private static final Logger LOGGER= LogManager.getLogger(App.class);

    public static void main( String[] args ) throws Exception {
        SpringApplication.run(App.class, args);
        LOGGER.info("App is running in {}", AppUtility.getHost());
    }
    
}
