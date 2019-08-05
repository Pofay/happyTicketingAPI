package com.cituojt.happyTicketingApi.responses;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ProjectsResponse that = (ProjectsResponse) o;
        return CollectionUtils.containsAll(data, that.data);
    }

     @Override
     public int hashCode() {
         return Objects.hash(data);
     }

}
