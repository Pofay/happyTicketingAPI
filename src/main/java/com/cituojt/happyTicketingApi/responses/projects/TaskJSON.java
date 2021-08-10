package com.cituojt.happyTicketingApi.responses.projects;

import java.util.UUID;

public class TaskJSON {

    private String id;
    private String name;
    private String assignedTo;
    private String status;
    private UUID projectId;
    private Integer estimatedTime;

    public TaskJSON(String id, UUID projectId, String name, String assignedTo, String status, Integer estimatedTime) {
        this.setId(id);
        this.setProjectId(projectId);
        this.setName(name);
        this.setAssignedTo(assignedTo);
        this.setStatus(status);
        this.setEstimatedTime(estimatedTime);
    }

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public UUID getProjectId() {
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
