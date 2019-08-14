package com.cituojt.happyTicketingApi.requests;

public class CreateProjectRequest {

    private String name;

    public CreateProjectRequest() {

    }

    public CreateProjectRequest(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
