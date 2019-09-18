package com.cituojt.happyTicketingApi.requests;

import org.springframework.lang.Nullable;

public class UpdateTaskRequest {

    private String id;
    private String name;
    private String assignedTo;
    private String status;

    public UpdateTaskRequest() {
    }

    public UpdateTaskRequest(String id, String name, String assignedTo, String status) {
        this.setId(id);
        this.setName(name);
        this.setAssignedTo(assignedTo);
        this.setStatus(status);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
