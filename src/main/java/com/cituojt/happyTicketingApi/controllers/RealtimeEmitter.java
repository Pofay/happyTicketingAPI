package com.cituojt.happyTicketingApi.controllers;

public interface RealtimeEmitter {

    public void emit(String channelName, String event, Object data);
}
