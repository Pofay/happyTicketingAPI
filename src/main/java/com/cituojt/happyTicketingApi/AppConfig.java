package com.cituojt.happyTicketingApi;

import com.cituojt.happyTicketingApi.controllers.RealtimeEmitter;
import com.cituojt.happyTicketingApi.thirdParty.PusherRealtimeEmitter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.pusher.rest.*;

@Configuration
@ComponentScan("com.cituojt.happyTicketingApi")
public class AppConfig {


    @Value("${pusher.appId}")
    String appId;

    @Value("${pusher.key}")
    String appKey;

    @Value("${pusher.secret}")
    String appSecret;

    @Value("${pusher.cluster}")
    String appCluster;

    @Bean
    public Pusher pusher() {
        Pusher p = new Pusher(appId, appKey, appSecret);
        p.setCluster(appCluster);
        p.setEncrypted(true);
        return p;
    }

}

