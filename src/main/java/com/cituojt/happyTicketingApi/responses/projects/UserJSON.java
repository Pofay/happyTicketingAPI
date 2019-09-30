package com.cituojt.happyTicketingApi.responses.projects;

public class UserJSON {

    private Long id;
    private String email;
    private Long projectId;

    public UserJSON(Long id, String email, Long projectId) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
