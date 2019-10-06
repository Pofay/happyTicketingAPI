package com.cituojt.happyTicketingApi.requests;

public class DeleteTaskRequest {

    private String id;

    public DeleteTaskRequest() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DeleteTaskRequest(String id) {
        this.setId(id);
    }
}
