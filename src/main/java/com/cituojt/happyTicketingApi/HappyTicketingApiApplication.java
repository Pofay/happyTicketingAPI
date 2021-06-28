package com.cituojt.happyTicketingApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import jdk.jfr.Percentage;

@SpringBootApplication
@PropertySources({ @PropertySource("classpath:application.properties"), @PropertySource("classpath:auth0.properties"),
        @PropertySource("classpath:pusher.properties") })
@EnableAutoConfiguration
public class HappyTicketingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HappyTicketingApiApplication.class, args);
    }
}
