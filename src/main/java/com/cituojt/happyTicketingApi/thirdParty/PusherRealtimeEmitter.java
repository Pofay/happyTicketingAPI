package com.cituojt.happyTicketingApi.thirdParty;

import com.cituojt.happyTicketingApi.controllers.RealtimeEmitter;
import com.pusher.rest.Pusher;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan("com.cituojt.happyTicketingApi.thirdParty")
public class PusherRealtimeEmitter implements RealtimeEmitter {

    private Pusher pusherImplementation;

    public PusherRealtimeEmitter(Pusher impl) {
        this.pusherImplementation = impl;
    }

    @Override
    public void emit(String channelName, String event, Object data) {
        this.pusherImplementation.trigger(channelName, event, data);
    }
}
