package com.cituojt.happyTicketingApi.responses.projects;

import java.util.UUID;

public class UserJSON {

    private UUID id;
    private String email;
    private Long projectId;

    public UserJSON(UUID id, String email, Long projectId) {
        this.setId(id);
        this.setEmail(email);
        this.setProjectId(projectId);
    }

    private void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
