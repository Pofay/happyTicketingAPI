package com.cituojt.happyTicketingApi.responses.projects;

public class DeletedTaskJSON {

    private String taskId;
    private Long projectId;

    public DeletedTaskJSON() {

    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String id) {
        this.taskId = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public DeletedTaskJSON(String taskId, Long projectId) {
        this.setTaskId(taskId);
        this.setProjectId(projectId);
    }

}
