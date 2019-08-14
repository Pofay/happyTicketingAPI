package com.cituojt.happyTicketingApi.responses.projects;

public class TaskJSON {

    private Long id;
    private String name;
    private String assignedTo;
    private String status;

    public TaskJSON(Long id, String name, String assignedTo, String status) {
        this.setId(id);
        this.setName(name);
        this.setAssignedTo(assignedTo);
        this.setStatus(status);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
