package com.cituojt.happyTicketingApi.modules.projectManagement.domain.usecases;

import com.cituojt.happyTicketingApi.modules.projectManagement.domain.aggregate.Project;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.dtos.AddTicketToProjectDTO;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.repository.ProjectRepository;
import com.cituojt.happyTicketingApi.modules.userManagement.domain.repository.UserRepository;

import io.vavr.control.Either;

public class AddTicketToProjectCommand {

    private UserRepository userRepo;
    private ProjectRepository projectRepo;

    public AddTicketToProjectCommand(UserRepository userRepo, ProjectRepository projectRepo) {
        this.userRepo = userRepo;
        this.projectRepo = projectRepo;
    }

    public Either<String, Project> execute(AddTicketToProjectDTO dto) {

        var user = userRepo.getById(dto.getUserId());
        var project = projectRepo.getById(dto.getProjectId());
        return project.addNewTicket(user, dto.getTicketId(), dto.getTicketName(), dto.getTicketStatus(),
                dto.getEstimatedTime()).flatMap(projectRepo::save);

        // var result = project.addNewTask(user, taskData...)
        // project.findMemberAssociatedWithUser(user)
        // if result is sucess => projectsRepo.save(project), return Either.ok()
        // else => return Either.error(errorMessage)
    }

}
