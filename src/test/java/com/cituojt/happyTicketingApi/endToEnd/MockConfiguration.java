package com.cituojt.happyTicketingApi.endToEnd;

import com.cituojt.happyTicketingApi.controllers.RealtimeEmitter;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockConfiguration {

    @Bean
    public RealtimeEmitter emitter() {
        return Mockito.mock(RealtimeEmitter.class);
    }

}

