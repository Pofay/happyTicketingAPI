package com.cituojt.happyTicketingApi.responses.projects;

import java.util.UUID;

public class DeletedTaskJSON {

    private String taskId;
    private UUID projectId;

    public DeletedTaskJSON() {

    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String id) {
        this.taskId = id;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public DeletedTaskJSON(String taskId, UUID projectId) {
        this.setTaskId(taskId);
        this.setProjectId(projectId);
    }

}
