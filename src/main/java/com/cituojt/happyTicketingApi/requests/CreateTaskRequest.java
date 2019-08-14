package com.cituojt.happyTicketingApi.requests;

public class CreateTaskRequest {

    private String name;

    public CreateTaskRequest() {

    }

    public CreateTaskRequest(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
