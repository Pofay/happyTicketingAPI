package com.cituojt.happyTicketingApi.modules.projectManagement.domain.repository;

import com.cituojt.happyTicketingApi.modules.projectManagement.domain.aggregate.Project;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects.ProjectId;

import io.vavr.control.Either;

public interface ProjectRepository {
    public Either<String, Project> save(Project p);

    public Project getById(ProjectId id);
}
