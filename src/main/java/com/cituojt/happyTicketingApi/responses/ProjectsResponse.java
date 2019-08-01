package com.cituojt.happyTicketingApi.responses;

import java.util.Collection;

public class ProjectsResponse {

    private Collection<ProjectDTO> data;

    public ProjectsResponse() {
    }

    public ProjectsResponse(Collection<ProjectDTO> projects) {
        this.data = projects;
    }

    public Collection<ProjectDTO> getProjects() {
        return data;
    }

}
