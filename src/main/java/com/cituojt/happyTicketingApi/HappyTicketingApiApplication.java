package com.cituojt.happyTicketingApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({ @PropertySource("classpath:application.properties"), @PropertySource("classpath:auth0.properties") })
public class HappyTicketingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HappyTicketingApiApplication.class, args);
    }

}
