package com.cituojt.happyTicketingApi.responses.projects;

public class TaskJSON {

    // private Long id;
    private Long id;
    private String name;
    private String assignedTo;
    private String status;

    public TaskJSON(Long id, String name, String assignedTo, String status) {
        this.id = id;
        this.setName(name);
        this.setAssignedTo(assignedTo);
        this.setStatus(status);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignedTo() {
        return this.assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
