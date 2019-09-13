package com.cituojt.happyTicketingApi.endToEnd;

import com.cituojt.happyTicketingApi.controllers.RealtimeEmitter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public RealtimeEmitter emitter() {
        return new NoOpRealtimeEmitter();
    }

    public class NoOpRealtimeEmitter implements RealtimeEmitter {

        @Override
        public void emit(String channelName, String event, Object data) {
            // Do nothing
        }

    }
}

