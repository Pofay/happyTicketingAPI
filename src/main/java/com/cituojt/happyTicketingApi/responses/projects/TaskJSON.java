package com.cituojt.happyTicketingApi.responses.projects;

public class TaskJSON {

    private String id;
    private String name;
    private String assignedTo;
    private String status;
    private Long projectId;

    public TaskJSON(String id, Long projectId, String name, String assignedTo, String status) {
        this.setId(id);
        this.setProjectId(projectId);
        this.setName(name);
        this.setAssignedTo(assignedTo);
        this.setStatus(status);
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getProjectId() {
        return projectId;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
