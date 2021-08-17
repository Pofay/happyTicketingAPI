package com.cituojt.happyTicketingApi.modules.projectManagement.domain.aggregate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cituojt.happyTicketingApi.modules.projectManagement.domain.entities.ProjectMember;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.entities.Ticket;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects.ProjectId;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects.ProjectName;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects.TicketId;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects.TicketName;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects.TicketStatus;
import com.cituojt.happyTicketingApi.modules.userManagement.domain.aggregate.User;

import io.vavr.control.Either;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Project {
    @NonNull
    @Getter
    @EqualsAndHashCode.Include
    private final ProjectId id;

    @NonNull
    @Getter
    private final ProjectName name;

    @NonNull
    @Getter
    private final List<Ticket> tickets;

    @NonNull
    @Getter
    private final List<ProjectMember> members;

    public static Project create(ProjectId id, ProjectName name) {
        return new Project(id, name, new ArrayList<Ticket>(), new ArrayList<ProjectMember>());
    }

    private Project(ProjectId id, ProjectName name, List<Ticket> tickets, List<ProjectMember> members) {
        this.id = id;
        this.name = name;
        this.tickets = tickets;
        this.members = members;
    }

    public Either<String, Project> addNewTicket(User user, TicketId ticketId, TicketName ticketName,
            TicketStatus ticketStatus, Duration estimatedTime) {

        var userOrNone = verifyMembership(user);
        if (userOrNone.isEmpty()) {
            return Either.left(String.format("User with email of %s is not a registered member of selected project",
                    user.getEmail()));
        } else {
            var ticket = new Ticket(ticketId, ticketName, ticketStatus, estimatedTime, user.getEmail());
            this.getTickets().add(ticket);
            return Either.right(new Project(this.id, this.name, this.getTickets(), this.getMembers()));
        }
    }

    private Optional<ProjectMember> verifyMembership(User user) {
        return members.stream().filter(m -> m.getUserId() == user.getId()).findFirst();
    }

    public void setAsOwner(User user) {
        members.add(ProjectMember.asOwner(user));
    }

    public void addMember(User member) {
        members.add(ProjectMember.asMember(member));
    }

}
