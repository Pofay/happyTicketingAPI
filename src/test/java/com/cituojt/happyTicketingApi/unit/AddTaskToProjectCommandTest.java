package com.cituojt.happyTicketingApi.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.UUID;

import com.cituojt.happyTicketingApi.modules.projectManagement.domain.aggregate.Project;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.dtos.AddTicketToProjectDTO;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.usecases.AddTicketToProjectCommand;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects.ProjectId;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects.ProjectName;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects.TicketId;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects.TicketName;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects.TicketStatus;
import com.cituojt.happyTicketingApi.modules.sharedKernel.valueObjects.Email;
import com.cituojt.happyTicketingApi.modules.sharedKernel.valueObjects.UserId;
import com.cituojt.happyTicketingApi.modules.userManagement.domain.aggregate.User;

import org.junit.jupiter.api.Test;

public class AddTaskToProjectCommandTest {

    @Test
    public void owner_can_add_a_ticket_to_project_via_command() {
        var taskId = TicketId.fromUUID(UUID.randomUUID());
        var projectId = ProjectId.fromUUID(UUID.randomUUID());
        var userId = UserId.fromUUID(UUID.randomUUID());

        var project = Project.create(projectId, ProjectName.of("Scrabble Trainer"));
        var user = new User(userId, Email.of("gian@example.com"));
        var userRepo = new InMemoryUserRepo();
        userRepo.save(user);
        var projectRepo = new InMemoryProjectRepo();
        project.setAsOwner(user);
        projectRepo.save(project);

        var command = new AddTicketToProjectCommand(userRepo, projectRepo);
        var dto = new AddTicketToProjectDTO(projectId, userId, taskId, TicketName.of("Test"), TicketStatus.TO_IMPLEMENT,
                Duration.ofHours(3));

        command.execute(dto);
        var actual = projectRepo.getById(projectId).getTickets().get(0);

        assertEquals(TicketName.of("Test"), actual.getName());
        assertEquals(TicketStatus.TO_IMPLEMENT, actual.getStatus());
        assertEquals(Duration.ofHours(3), actual.getEstimatedTime());
        assertEquals(Email.of("gian@example.com"), actual.getAssignedTo());
    }

    @Test
    public void cannot_add_a_ticket_if_user_is_not_a_member_of_the_project() {
        var taskId = TicketId.fromUUID(UUID.randomUUID());
        var projectId = ProjectId.fromUUID(UUID.randomUUID());
        var ownerId = UserId.fromUUID(UUID.randomUUID());
        var nonMemberId = UserId.fromUUID(UUID.randomUUID());

        var project = Project.create(projectId, ProjectName.of("Scrabble Trainer"));
        var owner = new User(ownerId, Email.of("pofay@example.com"));
        var nonMember = new User(nonMemberId, Email.of("some@example.com"));
        var userRepo = new InMemoryUserRepo();
        userRepo.save(owner);
        userRepo.save(nonMember);
        var projectRepo = new InMemoryProjectRepo();
        project.setAsOwner(owner);
        projectRepo.save(project);
        var expected = String.format("User with email of %s is not a registered member of selected project",
                nonMember.getEmail());

        var command = new AddTicketToProjectCommand(userRepo, projectRepo);
        var dto = new AddTicketToProjectDTO(projectId, nonMemberId, taskId, TicketName.of("Bogus"),
                TicketStatus.PARTIAL, Duration.ofHours(1));

        var result = command.execute(dto).getLeft();

        assertEquals(expected, result);
    }

    @Test
    public void member_can_add_a_ticket() {
        var taskId = TicketId.fromUUID(UUID.randomUUID());
        var projectId = ProjectId.fromUUID(UUID.randomUUID());
        var memberId = UserId.fromUUID(UUID.randomUUID());

        var project = Project.create(projectId, ProjectName.of("Scrabble Trainer"));
        var member = new User(memberId, Email.of("pofay@example.com"));
        var userRepo = new InMemoryUserRepo();
        userRepo.save(member);
        var projectRepo = new InMemoryProjectRepo();
        project.addMember(member);
        projectRepo.save(project);

        var command = new AddTicketToProjectCommand(userRepo, projectRepo);
        var dto = new AddTicketToProjectDTO(projectId, memberId, taskId, TicketName.of("Clean Code"),
                TicketStatus.COMPLETE, Duration.ofHours(5));

        command.execute(dto);
        var actual = projectRepo.getById(projectId).getTickets().get(0);

        assertEquals(TicketName.of("Clean Code"), actual.getName());
        assertEquals(TicketStatus.COMPLETE, actual.getStatus());
        assertEquals(Duration.ofHours(5), actual.getEstimatedTime());
        assertEquals(Email.of("pofay@example.com"), actual.getAssignedTo());
    }

}
