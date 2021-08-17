package com.cituojt.happyTicketingApi.unit;

import java.util.HashMap;
import java.util.Map;

import com.cituojt.happyTicketingApi.modules.projectManagement.domain.aggregate.Project;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.repository.ProjectRepository;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects.ProjectId;

import io.vavr.control.Either;

public class InMemoryProjectRepo implements ProjectRepository {

    private Map<ProjectId, Project> projects;

    public InMemoryProjectRepo() {
        this.projects = new HashMap<>();
    }

    public Either<String, Project> save(Project project) {
        this.projects.put(project.getId(), project);
        return Either.right(project);
    }

    public Project getById(ProjectId projectId) {
        return projects.get(projectId);
    }

}
