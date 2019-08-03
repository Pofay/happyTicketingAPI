package com.cituojt.happyTicketingApi.responses;

import java.util.List;

public class ProjectsResponse {

    private List<ProjectDTO> data;

    public ProjectsResponse() {
    }

    public ProjectsResponse(List<ProjectDTO> projects) {
        this.data = projects;
    }

    public List<ProjectDTO> getProjects() {
        return data;
    }

    public void setProjects(List<ProjectDTO> projects) {
        this.data = projects;
    }

}
